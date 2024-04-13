<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Modificar doctor (a)" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
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
                                    <InputText id="field-lastname" type="text" v-model="v$.lastname.$model" :class="{'invalid-field-custom': v$.lastname.$error}" />
                                    <label for="field-lastname" class="form-label-required">1er Apellido</label>
                                    <div class="text-danger text-start pt-2">
                                    <p class="error-messages" v-if="v$.lastname.$dirty && v$.lastname.required.$invalid">
                                        {{ v$.lastname.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.lastname.$dirty && v$.lastname.minLength.$invalid">
                                        {{ v$.lastname.minLength.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.lastname.$dirty && v$.lastname.maxLength.$invalid">
                                        {{ v$.lastname.maxLength.$message }}
                                    </p>
                               </div>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <InputText id="field-surname" type="text" v-model="v$.surname.$model" :class="{'invalid-field-custom': v$.surname.$error}" />
                                    <label for="field-surname" class="form-label-required">2do Apellido</label>
                                    <div class="text-danger text-start pt-2">
                                    <p class="error-messages" v-if="v$.surname.$dirty && v$.surname.required.$invalid">
                                        {{ v$.surname.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.surname.$dirty && v$.surname.text.$invalid">
                                        {{ v$.surname.text.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.surname.$dirty && v$.surname.minLength.$invalid">
                                        {{ v$.surname.minLength.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.surname.$dirty && v$.surnamename.maxLength.$invalid">
                                        {{ v$.surname.maxLength.$message }}
                                    </p>
                               </div>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.phone.$error }">
                                <i class="pi pi-phone" />
                                <InputText type="number" v-model="v$.phone.$model" id="field-phone"
                                    :useGrouping="false" />
                                <label for="field-phone" class="form-label-required">Número de teléfono</label>
                            </span>
                            <div class="text-danger text-start pt-1">
                                <p class="error-messages" v-if="v$.phone.$dirty && v$.phone.required.$invalid">
                                    {{ v$.phone.required.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.phone.$dirty && v$.phone.minLength.$invalid">
                                    {{ v$.phone.minLength.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.phone.$dirty && v$.phone.maxLength.$invalid">
                                    {{ v$.phone.maxLength.$message }}
                                </p>
                            </div>
                        </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-star" />
                                <Dropdown :class="{ 'invalid-field-custom': v$.speciality.$error }"
                                    class="form-label-required text-start" v-model="v$.speciality.$model"
                                    :options="specialitys" optionLabel="name"
                                    placeholder="Seleccione una especialidad" />
                                <div class="text-danger text-start pt-1">
                                    <p class="error-messages"
                                        v-if="v$.speciality.$dirty && v$.speciality.required.$invalid">
                                        {{ v$.speciality.required.$message }}
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
                            <Button label="Actualizar" icon="pi pi-pencil" @click="updateDoctor()" :disabled="!disableButton()"
                                class="p-button-rounded button-style" />
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
import { newregex } from "@/utils/regex"
import Toast from 'primevue/toast';
import services from "../services/doctor-service"
import { encrypt, decrypt } from '@/config/security';
import Dropdown from 'primevue/dropdown';
import specialityServices from '@/modules/speciality/services/speciality-services';

export default {
    props: {
        visible: {
            type: Boolean,
            required: true
        },
        doctor: {
            required: true
        }
    },
    setup(){
        const newDoctor = reactive({
            name: '',
            lastname: '',
            surname: '',
            phone: '',
            sex: '',
            birthday: '',
            speciality_id: '',
            
        })

        const rules = {
            name : { 
                required: helpers.withMessage("Debes agregar el nombre correcto", required),
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => newregex.test(value)),
                minLength: helpers.withMessage("El nombre debe tener al menos 3 caracteres",minLength(3)),
                maxLength: helpers.withMessage("El nombre debe tener menos de 60 caracteres", maxLength(60))
            },
            lastname: {
                required: helpers.withMessage("Debes agregar un apellido", required),
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => newregex.test(value)),
                minLength: helpers.withMessage("El apellido debe de contener al menos 3 caracteres", minLength(3))
            },
            surname: {
                required: helpers.withMessage("Debes agregar un apellido", required),
                onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos",(value) => newregex.test(value)),
                minLength: helpers.withMessage("El apellido debe de contener al menos 3 caracteres", minLength(3))
            },
            phone: {
                required: helpers.withMessage("Debes seleccionar un género", required),
                minLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", minLength(10)),
                maxLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", maxLength(10))
            },
            speciality: {
                required: helpers.withMessage("Debes seleccionar una especialidad", required)
            },
        }
        const v$ = useVuelidate(rules, newDoctor )
        return { newDoctor, v$ }
    },
    components:{
        Dialog,
        Textarea,
        Toast,
        Dropdown
    },
    data(){
        return{
            specialitys: []
        }
    },
    name: 'ModalUpdateDoctor',
    mounted() {
        this.getSpecialities();
    },
    methods:{
        closeModal() {
            this.$emit('update:visible', false);
            const oldDoctor = JSON.parse(this.doctor)
            this.newDoctor.id
            this.newDoctor.name = oldDoctor.name
            this.newDoctor.lastname = oldDoctor.lastname
            this.newDoctor.surname = oldDoctor.surname
            this.newDoctor.phone = oldDoctor.phone,
            this.newDoctor.sex = oldDoctor.sex,
            this.newDoctor.birthday = oldDoctor.birthday,
            this.newDoctor.speciality_id = oldDoctor.speciality_id,
            this.v$.$reset()
        },
        disableButton(){
            if (!this.v$.name.$dirty && !this.v$.surname.$dirty && !this.v$.lastname.$dirty && !this.v$.phone.$dirty) {
                return true;
            }
            return !this.v$.name.$invalid && !this.v$.lastname.$invalid && !this.v$.surname.$invalid && !this.v$.phone.$invalid;
        },
        async getSpecialities() {
            try {
                const { status, data: { result } } = await specialityServices.getSpecialities(this.pageable)
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(result)
                    const { content } = JSON.parse(decripted)
                    this.specialitys = content
                }
            } catch (error) { }

        },
        async updateDoctor(){
            if(!this.v$.name.$invalid && !this.v$.lastname.$invalid && !this.v$.surname.$invalid && !this.v$.phone.$invalid){
                try {
                    this.newDoctor.id = JSON.parse(this.doctor).id
                    console.log(this.newDoctor);
                    const encodedDoctor = await encrypt(JSON.stringify(this.newDoctor))
                    const {status} = await services.updateDoctor(encodedDoctor)
                    if(status === 200 || status === 201){
                        this.closeModal()
                        this.$toast.add({severity:'success', summary: 'Éxito', detail: 'Doctor(a) actualizada correctamente', life: 3000});
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
        doctor:{
            handler(){
                const oldDoctor = JSON.parse(this.doctor);
                let timestamp = oldDoctor.birthday;
                let date = new Date(timestamp);
                let formattedDate = date.toISOString().split('T')[0];
                this.newDoctor.name = oldDoctor.name;
                this.newDoctor.lastname = oldDoctor.lastname;
                this.newDoctor.surname = oldDoctor.surname;
                this.newDoctor.phone = oldDoctor.phone;
                this.newDoctor.sex = oldDoctor.sex;
                this.newDoctor.birthday = formattedDate;
                this.newDoctor.speciality_id = oldDoctor.speciality_id;
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
    border-color: $red-primary !important;
    box-shadow: 0 0 3px $red-secondary !important;
}

.invalid-field-custom label {
    color: $red-primary !important;
}

.invalid-field-custom .pi {
    color: $red-primary !important;
}
</style>