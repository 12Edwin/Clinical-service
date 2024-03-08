<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Registrar especialidad" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <b-row>
                        <b-col class="mt-4 mb-2" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-shield" />
                                    <InputText id="field-name" type="text" v-model="v$.name.$model"
                                        :class="{'invalid-field-custom': v$.name.$error}" />
                                    <label for="field-name" class="form-label-required">Nombre</label>
                                </span>
                               <div class="text-danger text-start pt-2">
                                    <p class="error-messages" v-if="v$.name.$dirty && v$.name.required.$invalid">
                                        {{ v$.name.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.name.$dirty && v$.name.onlyLettersAndAccents.$invalid">
                                        {{ v$.name.onlyLettersAndAccents.$message }}
                                    </p>
                               </div>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="2" v-model="v$.description.$model"  :class="{'invalid-field-custom': v$.description.$error}" />
                                    <label for="field-description" class="form-label-required">Descripción</label>
                                </span>
                                <div class="text-danger text-start pt-2">
                                    <p class="error-messages" v-if="v$.description.$dirty && v$.description.required.$invalid">
                                        {{ v$.description.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.description.$dirty && v$.description.onlyLettersAndAccents.$invalid">
                                        {{ v$.description.onlyLettersAndAccents.$message }}
                                    </p>
                               </div>
                            </div>
                        </b-col>
                    </b-row>
                </div>
                <template #footer>
                    <b-row>
                        <b-col cols="12">
                            <Button label="Cancelar" icon="pi pi-times" @click="closeModal"
                                class="p-button-rounded p-button-secondary" />
                            <Button label="Registrar" icon="pi pi-plus" @click="closeModal"
                                class="p-button-rounded button-style" />
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
        </b-col>
    </b-row>
</template>

<script>
import Dialog from 'primevue/dialog';
import Textarea from "primevue/textarea"
import {onlyLettersAndAccents} from "@/utils/regex"
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers} from '@vuelidate/validators'
export default {
    name: 'ModalSaveSpeciality',
    props: ['visible'],
    components:{
        Dialog,
        Textarea
    }, 
    setup(){
        const speciality = reactive({
            name: '',
            description: ''
        })

        const rules = {
            name : { 
                required: helpers.withMessage("Debes agregar un nombre para la especialidad", required),
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => onlyLettersAndAccents.test(value))
            },
            description : { 
                required: helpers.withMessage("Debes agregar una descripción para la especialidad", required),
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => onlyLettersAndAccents.test(value))
            }
        }
        const v$ = useVuelidate(rules, speciality )
        return { speciality, v$ }
    },

    data(){
        return{
            speciality: {
                name: '',
                description: ''
            }
        }
    },
    methods:{
        closeModal() {
            this.$emit('update:visible', false);
            this.v$.name.$model = ''
            this.v$.description.$model = ''
            this.v$.$reset()
        },
    },
}   
</script>

<style scoped>
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

.invalid-field-custom{
    border-color:  rgba(255, 0, 0, 1) !important;
    box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
}

.error-messages{
    margin-bottom: 0; font-weight: 350; font-size: 15px;
}

.error-messages::before{
    content: "* ";
    color: red;
}

.form-label-required::after{
    content: " *";
    color: red;
}
</style>