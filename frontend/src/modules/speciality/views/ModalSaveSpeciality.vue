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
                                        :class="{'invalid-field-custom': v$.name.$error}"/>
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
                                    <Textarea id="field-description" type="text" rows="2" v-model="v$.description.$model"  :class="{'invalid-field-custom': v$.description.$error}" />
                                    <label for="field-description" class="form-label-required">Descripción</label>
                                </span>
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
                            </div>
                        </b-col>
                    </b-row>
                </div>
                <template #footer>
                    <b-row>
                        <b-col cols="12">
                            <Button label="Cancelar" icon="pi pi-times" @click="closeModal()"
                                class="p-button-rounded p-button-secondary" />
                            <Button label="Registrar" icon="pi pi-plus" @click="saveSpeciality()" :disabled="v$.$invalid"
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
import {words, text, newregex} from "@/utils/regex"
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, maxLength, minLength} from '@vuelidate/validators'
import { encrypt } from "@/config/security"
import specialityService from "@/modules/speciality/services/speciality-services"
export default {
    name: 'ModalSaveSpeciality',
    props: {
        visible: {
            type: Boolean,
            required: true
        }
    },
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
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => newregex.test(value)),
                minLength: helpers.withMessage("El nombre debe tener al menos 3 caracteres",minLength(3)),
                maxLength: helpers.withMessage("El nombre debe tener menos de 50 caracteres", maxLength(60))
            },
            description : { 
                required: helpers.withMessage("Debes agregar una descripción para la especialidad", required),
                text: helpers.withMessage("Caracteres no válidos",(value) => newregex.test(value)),
                minLength: helpers.withMessage("La descripción debe tener al menos 3 caracteres",minLength(3)),
                maxLength: helpers.withMessage("La descripción debe tener menos de 50 caracteres", maxLength(60))
            }
        }
        const v$ = useVuelidate(rules, speciality )
        return { speciality, v$ }
    },

    data(){
        return{
        }
    },
    methods:{
        closeModal() {
            this.$emit('update:visible', false);
            this.v$.name.$model = ''
            this.v$.description.$model = ''
            this.v$.$reset()
        },
        async saveSpeciality(){
            const encoded = await encrypt(JSON.stringify(this.speciality))
            console.log("encoded",encoded)
            try {
                const response = await specialityService.saveSpeciality(encoded)
                console.log("response",response)
            } catch (error) {
                console.log("error en la peticion",error)
            }
        }
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