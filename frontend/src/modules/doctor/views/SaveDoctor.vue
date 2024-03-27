<template>
    <Card class="shadow shadow-lg rounded">
        <template #header>
            <div class="header">
                <h4 class="d-flex w-100">Registro de doctores</h4>
            </div>
        </template>
        <template #content>
            <div class="p-fluid grid">
                <div class="row">
                    <div class="col d-flex align-items-left">
                        <h5 class="border-none pb-2 mb-1">Datos personales</h5>
                    </div>
                </div>
                <b-row>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.name.$error }">
                                <i class="pi pi-user"></i>
                                <InputText id="field-name" type="text" v-model="v$.name.$model" />
                                <label for="field-name" class="form-label-required">Nombre</label>
                            </span>
                            <div class="text-danger text-start pt-1">
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
                            </div>
                        </div>
                    </b-col>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.lastname.$error }">
                                <i class="pi pi-user" />
                                <InputText id="field-lastname" type="text" v-model="v$.lastname.$model" />
                                <label for="field-lastname" class="form-label-required">Apellido paterno</label>
                            </span>
                            <div class="text-danger text-start pt-1">
                                <p class="error-messages" v-if="v$.lastname.$dirty && v$.lastname.required.$invalid">
                                    {{ v$.lastname.required.$message }}
                                </p>
                                <p class="error-messages"
                                    v-if="v$.lastname.$dirty && v$.lastname.onlyLettersAndAccents.$invalid">
                                    {{ v$.lastname.onlyLettersAndAccents.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.lastname.$dirty && v$.lastname.minLength.$invalid">
                                    {{ v$.lastname.minLength.$message }}
                                </p>
                            </div>
                        </div>
                    </b-col>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-user" />
                                <InputText id="field-surname" type="text" />
                                <label for="field-surname">Apellido materno</label>
                            </span>
                        </div>
                    </b-col>

                </b-row>
                <b-row>
                    <b-col class="mt-4" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.curp.$error }">
                                <i class="pi pi-id-card" />
                                <InputText id="field-curp" type="text" v-model="v$.curp.$model"
                                    @input="() => v$.curp.$model = v$.curp.$model.toUpperCase()" />
                                <label for="field-curp" class="form-label-required">CURP</label>
                            </span>
                            <div class="text-danger text-start pt-1">
                                <p class="error-messages" v-if="v$.curp.$dirty && v$.curp.required.$invalid">
                                    {{ v$.curp.required.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.curp.$dirty && v$.curp.curpFormmat.$invalid">
                                    {{ v$.curp.curpFormmat.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.curp.$dirty && v$.curp.minLength.$invalid">
                                    {{ v$.curp.minLength.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.curp.$dirty && v$.curp.maxLength.$invalid">
                                    {{ v$.curp.maxLength.$message }}
                                </p>
                            </div>
                        </div>
                    </b-col>
                    <b-col class="mt-4" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.birthDate.$error }">
                                <i class="pi pi-calendar" />
                                <Calendar id="field-birthdate" :maxDate="getNewDate()" v-model="v$.birthDate.$model"
                                    dateFormat="yy-mm-dd" />
                                <label for="field-birthdate" class="form-label-required">Fecha de nacimiento</label>
                            </span>
                            <div class="text-danger text-start pt-1">
                                <p class="error-messages" v-if="v$.birthDate.$dirty && v$.birthDate.required.$invalid">
                                    {{ v$.birthDate.required.$message }}
                                </p>
                                <p class="error-messages"
                                    v-if="v$.birthDate.$dirty && v$.birthDate.dateFormat.$invalid">
                                    {{ v$.birthDate.dateFormat.$message }}
                                </p>
                            </div>
                        </div>
                    </b-col>
                    <b-col class="mt-4">
                        <span class="p-float-label p-input-icon-right"
                            :class="{ 'invalid-field-custom': v$.rfc.$error }">
                            <i class="pi pi-id-card" />
                            <InputText id="field-rfc" type="text" v-model="v$.rfc.$model"
                                @input="() => v$.rfc.$model = v$.rfc.$model.toUpperCase()" />
                            <label for="field-rfc" class="form-label-required">RFC</label>
                        </span>
                        <div class="text-danger text-start pt-1">
                            <p class="error-messages" v-if="v$.rfc.$dirty && v$.rfc.required.$invalid">
                                {{ v$.rfc.required.$message }}
                            </p>
                            <p class="error-messages" v-if="v$.rfc.$dirty && v$.rfc.rfcFormmat.$invalid">
                                {{ v$.rfc.rfcFormmat.$message }}
                            </p>
                            <p class="error-messages" v-if="v$.rfc.$dirty && v$.rfc.minLength.$invalid">
                                {{ v$.rfc.minLength.$message }}
                            </p>
                            <p class="error-messages" v-if="v$.rfc.$dirty && v$.rfc.maxLength.$invalid">
                                {{ v$.rfc.maxLength.$message }}
                            </p>
                        </div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col class="d-flex align-items-lelft mt-4">
                        <h5 class="border-none pb-2 mb-2">Dirección</h5>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-map" />
                                <InputText id="field-street" type="text" />
                                <label for="field-street" class="form-label-required">Calle</label>
                            </span>
                        </div>
                    </b-col>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-map-marker" />
                                <InputText id="field-town" type="text" />
                                <label for="field-town" class="form-label-required">Colonia</label>
                            </span>
                        </div>
                    </b-col>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-home" />
                                <InputNumber id="field-zip" :useGrouping="false" />
                                <label for="field-zip" class="form-label-required">Codigo postal</label>
                            </span>
                        </div>
                    </b-col>
                    <b-col class="mt-4" cols="12" md="6" lg="6" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-home" />
                                <InputNumber id="field-internal-number" :useGrouping="false" />
                                <label for="field-internal-number" class="form-label-required">Número interior</label>
                            </span>
                        </div>
                    </b-col>
                    <b-col class="mt-4" cols="12" md="6" lg="6" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-hashtag" />
                                <InputNumber id="field-external-number" :useGrouping="false" />
                                <label for="field-external-number">Número exterior</label>
                            </span>
                        </div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col class="d-flex align-items-lelft mt-4">
                        <h5 class="border-none pb-2">Información profesional</h5>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-star" />
                                <InputText id="field-speciality" type="text" />
                                <label for="field-speciality" class="form-label-required">Especialidad</label>
                            </span>
                        </div>
                    </b-col>
                    <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-id-card" />
                                <InputText id="field-professional-id" type="text" />
                                <label for="field-professional-id" class="form-label-required">Cédula
                                    profesional</label>
                            </span>
                        </div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col class="d-flex align-items-lelft mt-4">
                        <h5 class="border-none pb-2">Contacto</h5>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col class="mt-3" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-phone" />
                                <InputNumber id="field-phone" :useGrouping="false" />
                                <label for="field-phone" class="form-label-required">Número de teléfono</label>
                            </span>
                        </div>
                    </b-col>
                    <b-col class="mt-3" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-at" />
                                <InputText id="field-email" type="email" :useGrouping="false" />
                                <label for="field-email" class="form-label-required">Correo electrónico</label>
                            </span>
                        </div>
                    </b-col>
                </b-row>
            </div>
        </template>
        <template #footer>
            <b-row class="mt-2">
                <b-col cols="12" class="d-flex justify-content-end">
                    <Button icon="pi pi-check" @click="saveDoctors()" label="Guardar" class="p-button-rounded"
                        :loading="isLoading" />
                </b-col>
            </b-row>
        </template>
    </Card>
</template>

<script>
import Calendar from 'primevue/calendar';
import InputNumber from 'primevue/inputnumber';
import Card from 'primevue/card';
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, minLength, maxLength } from '@vuelidate/validators'
import { words, errorMessage, curp, rfc, text, dateFormat } from "@/utils/regex"
import RadioButton from 'primevue/radiobutton';
import InputMask from 'primevue/inputmask';
import { decrypt, encrypt } from '@/config/security';
import service from '@/modules/doctor/services/doctor-service';

export default {
    name: 'SaveDoctor',
    components: {
        Calendar,
        InputNumber,
        Card,
        RadioButton,
        InputMask
    },
    setup() {
        const doctor = reactive({
            name: '',
            lastname: '',
            secondLastName: '',
            curp: '',
            rfc: '',
            birthDate: '',
            street: '',
        })

        const rules = {
            name: {
                required: helpers.withMessage("Debes agregar un nombre", required),
                onlyLettersAndAccents: helpers.withMessage(errorMessage, (value) => words.test(value)),
                minLength: helpers.withMessage("El nombre debe de contener al menos 3 caracteres", minLength(3))
            },
            lastname: {
                required: helpers.withMessage("Debes agregar un apellido", required),
                onlyLettersAndAccents: helpers.withMessage(errorMessage, (value) => words.test(value)),
                minLength: helpers.withMessage("El apellido debe de contener al menos 3 caracteres", minLength(3))
            },
            curp: {
                required: helpers.withMessage("El campo CURP es obligatorio", required),
                curpFormmat: helpers.withMessage("El CURP no es valido", (value) => curp.test(value)),
                minLength: helpers.withMessage("El CURP debe de contener al menos 18 caracteres", minLength(18)),
                maxLength: helpers.withMessage("El CURP debe de contener máximo 18 caracteres", maxLength(18))
            },
            rfc: {
                required: helpers.withMessage("El campo RFC es obligatorio", required),
                rfcFormmat: helpers.withMessage("El RFC no es valido", (value) => rfc.test(value)),
                minLength: helpers.withMessage("El RFC debe de contener al menos 13 caracteres", minLength(13)),
                maxLength: helpers.withMessage("El RFC debe de contener máximo 13 caracteres", maxLength(13))
            },
            birthDate: {
                required: helpers.withMessage("Debes agregar una fecha de nacimiento", required),
                dateFormat: helpers.withMessage("El formato ingresado no coincide con el requerido", (value) => dateFormat.test(value))
            }
        }
        const v$ = useVuelidate(rules, doctor)
        return { doctor, v$ }
    },
    data() {
        return {
            doctor: {
                name: '',
                lastName: '',
                secondLastName: '',
                curp: '',
                rfc: '',
                birthDate: ''
            },
            isLoading: false,
            gneres: [
                {
                    id: 'womenId',
                    name: 'woman',
                    value: 'Mujer'
                },
                {
                    id: 'menId',
                    name: 'man',
                    value: 'Hombre'
                }
            ],
            newDoctor: {
                fullname: "Carlos Gonzalez",
                name: "Carlos",
                code: "949596",
                password: "753159",
                surname: "Gonzalez",
                lastname: "Gonzalez",
                birthday: "1990-01-01",
                phone: "773531419",
                sex: "Masculino",
                speciality: "Cardiologia",
                speciality_id: "19",
                description: "Estudio del corazon y de sus funciones y enfermedades."
            }
            // newDoctor: {
            //     name: "Yaret",
            //     surname: "apellido",
            //     lastname: "apellido",
            //     birthday: "2006-03-20",
            //     phone: "1234567895",
            //     sex: "Femenino",
            //     speciality_id: 18,
            //     code: "1234567127",
            //     password: "123"
            // }
        }
    },
    methods: {
        getNewDate() {
            return new Date()
        },

        async saveDoctors() {
            const objectToEncrypt = JSON.stringify(this.newDoctor)
            const chiperedObject = await encrypt(objectToEncrypt)
            // const unzipObject = await decrypt(chiperedObject);
            // console.log("=>", unzipObject)
            console.log("encryp =>", chiperedObject);
            const { data, status } = await service.save_doctor(chiperedObject)
            if (status === 200 || status === 201) {
                console.log(data);
            }
            console.log(data);
        },
    }
}
</script>
<!-- rgb(0 175 245) -->

<style scoped>
.header {
    padding-left: 15px;
    padding-top: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #e0e0e0;
}

.error-messages {
    margin-bottom: 0;
    font-weight: 350;
    font-size: 15px;
}

.error-messages::before {
    content: "* ";
    color: red;
}

.form-label-required::after {
    content: " *";
    color: red;
}

.invalid-field-custom .p-inputtext {
    border-color: rgba(255, 0, 0, 1) !important;
    box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
}

.invalid-field-custom label {
    color: rgba(255, 0, 0, 1) !important;
}

.invalid-field-custom .pi {
    color: red !important;
}
</style>