<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Modificar Servicio" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <b-row>
                        <b-col class="mt-4 mb-2" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-shield" />
                                    <InputText id="field-name" type="text" v-model="v$.name.$model"
                                        :class="{ 'invalid-field-custom': v$.name.$error }" />
                                    <label for="field-name" class="form-label-required">Nombre</label>
                                    <div class="text-danger text-start pt-2">
                                        <p class="error-messages" v-if="v$.name.$dirty && v$.name.required.$invalid">
                                            {{ v$.name.required.$message }}
                                        </p>
                                        <p class="error-messages"
                                            v-if="v$.name.$dirty && v$.name.onlyLettersAndAccents.$invalid">
                                            {{ v$.name.onlyLettersAndAccents.$message }}
                                        </p>
                                        <p class="error-messages" v-if="v$.name.$dirty && v$.name.minLength.$invalid">
                                            {{ v$.name.minLength.$message }}
                                        </p>
                                        <p class="error-messages" v-if="v$.name.$dirty && v$.name.maxLength.$invalid">
                                            {{ v$.name.maxLength.$message }}
                                        </p>
                                    </div>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="3"
                                        v-model="v$.description.$model"
                                        :class="{ 'invalid-field-custom': v$.description.$error }" />
                                    <label for="field-description" class="form-label-required">Descripción</label>
                                    <div class="text-danger text-start pt-2">
                                        <p class="error-messages"
                                            v-if="v$.description.$dirty && v$.description.required.$invalid">
                                            {{ v$.description.required.$message }}
                                        </p>
                                        <p class="error-messages"
                                            v-if="v$.description.$dirty && v$.description.text.$invalid">
                                            {{ v$.description.text.$message }}
                                        </p>
                                        <p class="error-messages"
                                            v-if="v$.description.$dirty && v$.description.minLength.$invalid">
                                            {{ v$.description.minLength.$message }}
                                        </p>
                                        <p class="error-messages"
                                            v-if="v$.description.$dirty && v$.description.maxLength.$invalid">
                                            {{ v$.description.maxLength.$message }}
                                        </p>
                                    </div>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-money-bill" />
                                    <InputText id="field-price" type="text" rows="3" v-model="v$.price.$model"
                                        :class="{ 'invalid-filed-custom': v$.price.$error }" />
                                    <label for="field-price">Precio</label>
                                    <div class="text-danger text-start pt-2">
                                        <p class="error-messages" v-if="v$.price.$dirty && v$.price.required.$invalid">
                                            {{ v$.price.required.$message }}
                                        </p>
                                        <p class="error-messages" v-if="v$.price.$dirty && v$.price.text.$invalid">
                                            {{ v$.price.text.$message }}
                                        </p>
                                        <p class="error-messages" v-if="v$.price.$dirty && v$.price.precio.$invalid">
                                            {{ v$.price.precio.$message }}
                                        </p>
                                        <p class="error-messages" v-if="v$.price.$dirty && v$.price.maxLength.$invalid">
                                            {{ v$.price.maxLength.$message }}
                                        </p>
                                    </div>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-bitcoin" />
                                    <Dropdown id="field-speciality" :options="specialities" optionLabel="name"
                                        optionValue="id" v-model="selectedSpeciality" />
                                    <label for="field-speciality" class="form-label-required">Especialidad</label>
                                </span>
                            </div>
                        </b-col>
                    </b-row>
                </div>
                <template #footer>
                    <b-row>
                        <b-col cols="12">
                            <Button label="Cancelar" icon="pi pi-times" @click="closeModal"
                                class="p-button-rounded p-button-secondary" />
                            <Button label="Actualizar" icon="pi pi-pencil" @click="updateService()"
                                :disabled="!disableButton()" class="p-button-rounded button-style" />
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
        </b-col>
    </b-row>
</template>

<script>
import { reactive } from "@vue/composition-api";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, maxLength, minLength } from "@vuelidate/validators";
import { newregex } from "@/utils/regex";
import Toast from "primevue/toast";
import { encrypt, decrypt } from "@/config/security";
import Dialog from "primevue/dialog";
import Textarea from "primevue/textarea";
import servicios from "@/modules/service-private/service-services/Services";
import Dropdown from "primevue/dropdown/";
export default {
    props: {
        visible: {
            type: Boolean,
            required: true,
        },
        service: {
            required: true,
        },
    },
    data() {
        return {
            specialities: [],
            selectedSpeciality: null
        }
    },
    components: {
        Dialog,
        Textarea,
        Toast,
        Dropdown,
    },
    name: "ModalUpdateService",
    setup() {
        const newService = reactive({
            name: "",
            description: "",
            price: 0
        });

        const rules = {
            name: {
                required: helpers.withMessage(
                    "Debes agregar un nombre para la especialidad",
                    required
                ),
                onlyLettersAndAccents: helpers.withMessage(
                    "Caracteres no válidos",
                    (value) => newregex.test(value)
                ),
                minLength: helpers.withMessage(
                    "El nombre debe tener al menos 3 caracteres",
                    minLength(3)
                ),
                maxLength: helpers.withMessage(
                    "El nombre debe tener menos de 60 caracteres",
                    maxLength(60)
                ),
            },
            description: {
                required: helpers.withMessage(
                    "Debes agregar una descripción para la especialidad",
                    required
                ),
                text: helpers.withMessage("Caracteres no válidos", (value) =>
                    newregex.test(value)
                ),
                minLength: helpers.withMessage(
                    "La descripción debe tener al menos 3 caracteres",
                    minLength(3)
                ),
                maxLength: helpers.withMessage(
                    "La descripción debe tener menos de 60 caracteres",
                    maxLength(150)
                ),
            },
            price: {
                required: helpers.withMessage(
                    "Debes agregar una descripción para la servicio",
                    required
                ),
                text: helpers.withMessage("Caracteres no válidos", (value) =>
                    newregex.test(value)
                ),
                precio: helpers.withMessage("EL precio debe ser mayor a 0", (value) => +value > 0),
                maxLength: helpers.withMessage(
                    "La descripción debe tener menos de 60 caracteres",
                    maxLength(60)
                ),
            },
        };
        const v$ = useVuelidate(rules, newService);
        return { newService, v$ };
    },
    methods: {
        closeModal() {
            this.$emit("update:visible", false);
            const oldService = JSON.parse(this.service);
            this.newService.id;
            this.newService.name = oldService.name
            this.newService.description = oldService.description
            this.newService.price = oldService.price
            this.v$.$reset();
        },
        disableButton() {
            if (
                !this.v$.name.$dirty ||
                !this.v$.description.$dirty ||
                !this.v$.price.$dirty
            ) {
                return true;
            }
            return (
                !this.v$.name.$invalid &&
                !this.v$.description.$invalid &&
                !this.v$.price.$invalid
            );
        },
        async updateService() {
            this.speciality = +this.selectedSpeciality
            if (!this.v$.name.$invalid && !this.v$.description.$invalid && !this.v$.price.$invalid && this.selectedSpeciality != null) {
                try {
                    this.newService.id = JSON.parse(this.service).id;
                    this.newService.speciality = +this.selectedSpeciality
                    const encodedService = await encrypt(JSON.stringify(this.newService));
                    const { status } = await servicios.update_service(encodedService);
                    if (status === 200 || status === 201) {
                        this.closeModal();
                        this.$toast.add({
                            severity: "success",
                            summary: "Éxito",
                            detail: "Especialidad actualizada correctamente",
                            life: 3000,
                        });
                    }
                } catch (error) {
                    console.log("error en la peticion", error);
                }
            } else {
                this.$toast.add({
                    severity: "warn",
                    summary: "¡Cuidado!",
                    detail:
                        "¡Asegurate que todos los campos cumplan con el formato necesario!",
                    life: 3000,
                });
            }
        },
        async getSpecialities() {
            try {
                const { status, data: { result } } = await servicios.get_specialities();
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(result);
                    this.specialities = JSON.parse(decripted).content
                }
            } catch (error) {
                console.log("error en la peticion", error);
            }
        }
    },
    mounted() {
        this.getSpecialities()
    },
    watch: {
        service: {
            handler() {
                const oldService = JSON.parse(this.service);
                this.newService.name = oldService.name;
                this.newService.description = oldService.description;
                this.newService.price = oldService.price;
                this.newService.speciality = oldService.speciality;
            },
            deep: true,
        },
    },
};
</script>

<style scoped lang="scss">
@import "../../../styles/colors.scss";

.button-style {
    background: #2a715a;
    border: none;
}

.button-style:hover {
    background-color: #368368 !important;
}

.my-custom-dialog .p-dialog {
    max-width: 90%;
}

@media (max-width: 768px) {
    .my-custom-dialog .p-dialog {
        max-width: 95%;
    }
}


</style>
