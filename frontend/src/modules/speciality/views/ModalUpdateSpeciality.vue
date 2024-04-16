<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Modificar especialidad" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <b-row>
                        <b-col class="mt-4 mb-2" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-shield" />
                                    <InputText id="field-name" type="text" v-model="v$.name.$model" :class="{'invalid-field-custom': v$.name.$error}"/>
                                    <label for="field-name" class="form-label-required">Nombre</label>
                                </span>
                                <div class="text-danger text-start pt-2">
                                    <p class="error-messages" v-if="v$.name.$dirty && v$.name.required.$invalid">
                                        {{ v$.name.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.name.$dirty && v$.name.onlyLettersAndAccents.$invalid">
                                        {{ v$.name.onlyLettersAndAccents.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.name.$dirty && v$.name.minLength.$invalid">
                                        {{ v$.name.minLength.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.name.$dirty && v$.name.maxLength.$invalid">
                                        {{ v$.name.maxLength.$message }}
                                    </p>
                               </div>
                            </div>
                        </b-col> 
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="3" v-model="v$.description.$model" :class="{'invalid-field-custom': v$.description.$error}" />
                                    <label for="field-description" class="form-label-required">Descripción</label>
                                    <div class="text-danger text-start pt-2">
                                    <p class="error-messages" v-if="v$.description.$dirty && v$.description.required.$invalid">
                                        {{ v$.description.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.description.$dirty && v$.description.text.$invalid">
                                        {{ v$.description.text.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.description.$dirty && v$.description.minLength.$invalid">
                                        {{ v$.description.minLength.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.description.$dirty && v$.description.maxLength.$invalid">
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
                            <Button label="Actualizar" icon="pi pi-pencil" @click="updateSpeciality()" :disabled="!disableButton()"
                                class="p-button-rounded button-style" :loading="onUpdate"/>
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
            <Toast />
        </b-col>
    </b-row>
</template>

<script>
import Dialog from 'primevue/dialog';
import Textarea from "primevue/textarea"
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, maxLength, minLength} from '@vuelidate/validators'
import { text, words, backRegex } from "@/utils/regex"
import Toast from 'primevue/toast';
import specialitiesServices from "@/modules/speciality/services/speciality-services"
import { encrypt } from '@/config/security';
import { onSuccess, onError } from '@/kernel/alerts';
import utils from '@/kernel/utils';
export default {
    props: {
        visible: {
            type: Boolean,
            required: true
        },
        speciality: {
            required: true
        }
    },
    setup(){
        const newSpeciality = reactive({
            name: '',
            description: ''
        })

        const rules = {
            name : { 
                required: helpers.withMessage("Debes agregar un nombre para la especialidad", required),
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => backRegex.test(value)),
                minLength: helpers.withMessage("El nombre debe tener al menos 3 caracteres",minLength(3)),
                maxLength: helpers.withMessage("El nombre debe tener menos de 60 caracteres", maxLength(60))
            },
            description : { 
                required: helpers.withMessage("Debes agregar una descripción para la especialidad", required),
                text: helpers.withMessage("Caracteres no válidos",(value) => backRegex.test(value)),
                minLength: helpers.withMessage("La descripción debe tener al menos 3 caracteres",minLength(3)),
                maxLength: helpers.withMessage("La descripción debe tener menos de 60 caracteres", maxLength(60))
            }
        }
        const v$ = useVuelidate(rules, newSpeciality )
        return { newSpeciality, v$ }
    },
    data(){
        return {
            onUpdate: false
        }
    },
    components:{
        Dialog,
        Textarea,
        Toast
    },
    name: 'ModalUpdateSpeciality',
    methods:{
        closeModal() {
            this.$emit('update:visible', false);
            const oldSpeciality = JSON.parse(this.speciality)
            this.newSpeciality.id = oldSpeciality.id
            this.newSpeciality.name = oldSpeciality.name
            this.newSpeciality.description = oldSpeciality.description
            this.v$.$reset()
        },
        disableButton(){
            if (!this.v$.name.$dirty && !this.v$.description.$dirty) {
                return true;
            }
            return !this.v$.name.$invalid && !this.v$.description.$invalid;
        },
        async updateSpeciality(){
            if(!this.v$.name.$invalid && !this.v$.description.$invalid){
                try {
                    this.newSpeciality.id = JSON.parse(this.speciality).id
                    const encodedSpeciality = await encrypt(JSON.stringify(this.newSpeciality))
                    this.onUpdate = true
                    const {status, data: { text }} = await specialitiesServices.updateSpeciality(encodedSpeciality)
                    if(status === 200 || status === 201){
                        this.onUpdate = false
                        onSuccess("¡Éxito!", "Especialidad actualizada exitosamente").then(() => {
                            this.closeModal()
                        })
                        this.$emit('pagination', {page: 0, rows: 10})
                    }else{
                        this.onUpdate = false
                        const message = utils.getErrorMessages(text)
                        onError("Error al actualizar la especialidad", message).then(() => {
                            this.onSave = false
                            this.closeModal()
                        })
                    }
                } catch (error) {
                    console.log("error en la peticion",error)
                }
            }else{
                this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail: '¡Asegurate que todos los campos cumplan con el formato necesario!', life: 3000});
            }
        }
    },
    watch:{
        speciality:{
            handler(){
                const oldSpeciality = JSON.parse(this.speciality)
                this.newSpeciality.name = oldSpeciality.name
                this.newSpeciality.description = oldSpeciality.description
            },
            deep: true
        }
    
    }
}
</script>

<style scoped lang="scss">
@import "../../../styles/colors.scss";


.button-style{
    background: #2a715a;
    border: none;
}
.button-style:hover{
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

.error-messages {
    margin-bottom: 0;
    font-weight: 350;
    font-size: 15px;
}

.error-messages::before {
    content: "* ";
    color: $red-primary !important;
}

.form-label-required::after {
    content: " *";
    color: $red-primary;
}

.invalid-field-custom {
    border-color: #ff0000 !important;
    box-shadow: 0 0 3px $red-secondary !important;
}

.invalid-field-custom label {
    color: rgba(255, 0, 0, 1) !important;
}

.invalid-field-custom .pi {
    color: $red-primary !important;
}
</style>