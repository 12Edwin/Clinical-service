<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Modificar Patología" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
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
                                    <i class="pi pi-pencil" />
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
                    </b-row>
                </div>
                <template #footer>
                    <b-row>
                        <b-col cols="12">
                            <Button label="Cancelar" icon="pi pi-times" @click="closeModal"
                                class="p-button-rounded p-button-secondary" />
                            <Button label="Actualizar" icon="pi pi-pencil" @click="updatePathology()"
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
import { encrypt } from "@/config/security";
import Dialog from "primevue/dialog";
import Textarea from "primevue/textarea";
import pathologyService from '../pathology-service/Pathology'
import Dropdown from "primevue/dropdown/";
export default {
    name: "ModalUpdatePathology",
    props: {
        visible: {
            type: Boolean,
            required: true,
        },
        pathology: {
            required: true,
        },
    },
    components: {
        Dialog,
        Textarea,
        Toast,
        Dropdown,
    },
    setup() {
        const newPathology = reactive({
            name: "",
            description: "",
        });

        const rules = {
            name: {
                required: helpers.withMessage(
                    "Debes agregar un nombre para la patología",
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
                    "El nombre debe tener menos de 70 caracteres",
                    maxLength(60)
                ),
            },
            description: {
                required: helpers.withMessage(
                    "Debes agregar una descripción para la patología",
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
                    "La descripción debe tener menos de 150 caracteres",
                    maxLength(150)
                ),
            },
        };
        const v$ = useVuelidate(rules, newPathology);
        return { newPathology, v$ };
    },
    methods: {
        closeModal() {
            this.$emit("update:visible", false);
            const oldPathology = JSON.parse(this.pathology);
            this.newPathology.id;
            this.newPathology.name = oldPathology.name
            this.newPathology.description = oldPathology.description
            this.v$.$reset();
        },
        disableButton() {
            if (
                !this.v$.name.$dirty ||
                !this.v$.description.$dirty
            ) {
                return true;
            }
            return (
                !this.v$.name.$invalid &&
                !this.v$.description.$invalid
            );
        },
        async updatePathology() {
            this.speciality = +this.selectedSpeciality
            if (!this.v$.name.$invalid && !this.v$.description.$invalid) {
                try {
                    this.newPathology.id = JSON.parse(this.pathology).id;
                    const encodedPathology = await encrypt(JSON.stringify(this.newPathology));
                    const { status } = await pathologyService.update_pathology(encodedPathology);
                    if (status === 200 || status === 201) {
                        this.closeModal();
                        this.$toast.add({
                            severity: "success",
                            summary: "Éxito",
                            detail: "Patologia actualizada correctamente",
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
    },
    watch: {
        pathology: {
            handler() {
                const oldPathology = JSON.parse(this.pathology);
                this.newPathology.name = oldPathology.name;
                this.newPathology.description = oldPathology.description;
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
