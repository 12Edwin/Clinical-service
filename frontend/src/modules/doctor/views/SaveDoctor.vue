<template>
    <div>
        <Header style="margin-bottom: 20px" title="Doctores" />

        <TransitionGroup name="fade">
            <loader v-if="isLoading" key="load" />
            <Card class="shadow shadow-lg rounded" v-else key="content">
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
                                    :class="{ 'invalid-field-custom': v$.surname.$error }">
                                    <i class="pi pi-user" />
                                    <InputText id="field-surname" type="text" v-model="v$.surname.$model" />
                                    <label for="field-surname" class="form-label-required">1er Apellido</label>
                                </span>
                                <div class="text-danger text-start pt-1">
                                    <p class="error-messages" v-if="v$.surname.$dirty && v$.surname.required.$invalid">
                                        {{ v$.surname.required.$message }}
                                    </p>
                                    <p class="error-messages"
                                        v-if="v$.surname.$dirty && v$.surname.onlyLettersAndAccents.$invalid">
                                        {{ v$.surname.onlyLettersAndAccents.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.surname.$dirty && v$.surname.minLength.$invalid">
                                        {{ v$.surname.minLength.$message }}
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
                                    <label for="field-lastname">2do Apellido</label>
                                </span>
                                <div class="text-danger text-start pt-1">
                                    <p class="error-messages"
                                        v-if="v$.lastname.$dirty && v$.lastname.onlyLettersAndAccents.$invalid">
                                        {{ v$.lastname.onlyLettersAndAccents.$message }}
                                    </p>
                                    <p class="error-messages"
                                        v-if="v$.lastname.$dirty && v$.lastname.minLength.$invalid">
                                        {{ v$.lastname.minLength.$message }}
                                    </p>
                                </div>
                            </div>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col class="mt-4" cols="12" md="6" lg="4">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right"
                                    :class="{ 'invalid-field-custom': v$.birthDate.$error }">
                                    <i class="pi pi-calendar" />
                                    <Calendar id="field-birthdate" :maxDate="getMaxDate()" :minDate="getMinDate()" v-model="v$.birthDate.$model"
                                        dateFormat="dd-mm-yy" style="cursor: pointer;" :manualInput="false" />
                                    <label for="field-birthdate" class="form-label-required">Fecha de nacimiento</label>
                                </span>
                                <div class="text-danger text-start pt-1">
                                    <p class="error-messages"
                                        v-if="v$.birthDate.$dirty && v$.birthDate.required.$invalid">
                                        {{ v$.birthDate.required.$message }}
                                    </p>
                                </div>
                            </div>
                        </b-col>
                        <b-col class="mt-4" cols="12" md="6" lg="4">
                            <div class="field">
                                <Dropdown :class="{ 'invalid-field-custom': v$.sex.$error }"
                                    class="form-label-required text-start" v-model="v$.sex.$model" :options="gneres"
                                    optionLabel="value" placeholder="Seleccione el sexo" />
                                <div class="text-danger text-start pt-1">
                                    <p class="error-messages" v-if="v$.sex.$dirty && v$.sex.required.$invalid">
                                        {{ v$.sex.required.$message }}
                                    </p>
                                </div>
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
                    <b-row>
                        <b-col class="d-flex align-items-lelft mt-4">
                            <h5 class="border-none pb-2">Contacto</h5>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col class="mt-3" cols="12" md="6" lg="4">
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
                    </b-row>

                    <b-row>
                        <b-col class="d-flex align-items-lelft mt-4">
                            <h5 class="border-none pb-2">Acceso al sistema</h5>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col class="mt-3" cols="12" md="6" lg="4">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right"
                                    :class="{ 'invalid-field-custom': v$.code.$error }">
                                    <i class="pi pi-phone" />
                                    <InputText type="text" v-model="v$.code.$model" id="field-phone"
                                        :useGrouping="false" />
                                    <label for="field-phone" class="form-label-required">Código de acceso</label>
                                </span>
                                <div class="text-danger text-start pt-1">
                                    <p class="error-messages" v-if="v$.code.$dirty && v$.code.required.$invalid">
                                        {{ v$.code.required.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.code.$dirty && v$.code.minLength.$invalid">
                                        {{ v$.code.minLength.$message }}
                                    </p>
                                    <p class="error-messages" v-if="v$.code.$dirty && v$.code.maxLength.$invalid">
                                        {{ v$.code.maxLength.$message }}
                                    </p>
                                </div>
                            </div>
                        </b-col>
                    </b-row>
                </div>
            </template>
            <template #footer>
                <b-row class="mt-2">
                    <b-col cols="12" class="d-flex justify-content-end">
                        <Button icon="pi pi-check" @click="verifyDoctors()" label="Guardar" class="p-button-rounded"
                            :loading="isLoading" :disabled="v$.$invalid" />
                    </b-col>
                </b-row>
                <Toast />
            </template>
        </Card>
        </TransitionGroup>
    </div>
</template>

<script>
import Calendar from 'primevue/calendar';
import InputNumber from 'primevue/inputnumber';
import Card from 'primevue/card';
import Dropdown from 'primevue/dropdown';
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, minLength, maxLength } from '@vuelidate/validators'
import { errorMessage, newregex } from "@/utils/regex"
import RadioButton from 'primevue/radiobutton';
import InputMask from 'primevue/inputmask';
import { decrypt, encrypt } from '@/config/security';
import service from '@/modules/doctor/services/doctor-service';
import InputText from 'primevue/inputtext/InputText';
import Toast from 'primevue/toast';
import specialityServices from '@/modules/speciality/services/speciality-services';
import FileUpload from 'primevue/fileupload';
import Header from '@/components/Header.vue';
import { onError, onQuestion, onSuccess } from '@/kernel/alerts';
import utils from "@/kernel/utils";
import Loader from '@/components/loader.vue';

export default {
    name: 'SaveDoctor',
    components: {
        Calendar,
        InputNumber,
        Card,
        RadioButton,
        InputMask,
        Dropdown,
        Toast,
        FileUpload,
        Header,
        Loader
    },
    setup() {
        const doctor = reactive({
            name: '',
            lastname: '',
            surname: '',
            sex: '',
            birthDate: '',
            speciality: '',
            phone: '',
            code: '',
        })

        const rules = {
            name: {
                required: helpers.withMessage("Debes agregar un nombre", required),
                onlyLettersAndAccents: helpers.withMessage(errorMessage, (value) => newregex.test(value)),
                minLength: helpers.withMessage("El nombre debe de contener al menos 3 caracteres", minLength(3))
            },
            lastname: {
                onlyLettersAndAccents: helpers.withMessage(errorMessage, (value) => newregex.test(value)),
                minLength: helpers.withMessage("El apellido debe de contener al menos 3 caracteres", minLength(3))
            },
            surname: {
                required: helpers.withMessage("Debes agregar un apellido", required),
                onlyLettersAndAccents: helpers.withMessage(errorMessage, (value) => newregex.test(value)),
                minLength: helpers.withMessage("El apellido debe de contener al menos 3 caracteres", minLength(3))
            },
            birthDate: {
                required: helpers.withMessage("Debes agregar una fecha de nacimiento", required),
            },
            sex: {
                required: helpers.withMessage("Debes seleccionar un género", required),

            },
            phone: {
                required: helpers.withMessage("Debes agregar un número", required),
                minLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", minLength(10)),
                maxLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", maxLength(10))
            },
            speciality: {
                required: helpers.withMessage("Debes seleccionar una especialidad", required)
            },
            code: {
                required: helpers.withMessage("Agrega un código de acceso", required),
                minLength: helpers.withMessage("El código debe de contar con mímino 10 digitos", minLength(10)),
                maxLength: helpers.withMessage("El código debe de contar con maximo 10 digitos", maxLength(10))
            }

        }
        const v$ = useVuelidate(rules, doctor)
        return { doctor, v$ }
    },
    data() {
        return {
            isLoading: false,
            selectedGender: null,
            isLoading: false,
            gneres: [
                {
                    id: 'Femenino',
                    name: 'Woman',
                    value: 'Femenino'
                },
                {
                    id: 'Masculino',
                    name: 'Man',
                    value: 'Masculino'
                }
            ],
            specialitys: [],
            pageable: {
                page: 0,
                size: 100
            },
        }
    },
    mounted() {
        this.getSpecialities();
    },
    methods: {
        getMaxDate() {
            const today = new Date();
            const year = today.getFullYear() - 18;
            const newDate = new Date(year, 11, 31);
            return newDate;
        },

        getMinDate(){
            const today = new Date();
            const year = today.getFullYear() - 100;
            const newDate = new Date(year, 11, 31);
            return newDate;
        },

        async getSpecialities() {
            try {
                this.isLoading = true;
                const { status, data } = await specialityServices.getSpecialities(this.pageable)
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(data.result)
                    const { content } = JSON.parse(decripted)
                    this.specialitys = content
                    this.isLoading = false;
                } else {
                    const message = utils.getErrorMessages(data.text)
                    await onError('Error', message).then(() => { })
                    this.isLoading = false;
                }
            } catch (error) { }

        },

        async verifyDoctors() {
            this.v$.$touch();
            const mandatoryFields = ['name', 'lastname', 'surname', 'birthDate', 'phone', 'code'];
            const anyEmpty = mandatoryFields.some(field => !this.doctor[field]);

            if (anyEmpty) {
                this.$toast.add({ severity: 'warn', summary: '¡Cuidado!', detail: 'Debes completar todos los campos requeridos', life: 3000 });
                return;
            }

            for (const fieldName in this.v$.$params) {
                if (!this.v$[fieldName].$dirty && !this.v$[fieldName].$error) {
                    continue;
                }

                if (!this.v$[fieldName].$dirty && this.v$[fieldName].$error) {
                    this.$toast.add({ severity: 'warn', summary: '¡Cuidado!', detail: `El campo ${fieldName} no cumple con las validaciones requeridas`, life: 3000 });
                    return;
                }
            }
            this.saveDoctor();
        },

        async saveDoctor() {
           if(await onQuestion('Guardar doctor', '¿Está seguro de guardar al doctor?')){
            let pass = "DOC" + this.doctor.name;
            let selectedGender = this.doctor.sex.value;
            let selectedSpeciality = this.doctor.speciality.id;
            let dateFormat = this.formatDate(this.doctor.birthDate);

            const newData = {
                name: this.doctor.name,
                surname: this.doctor.surname,
                lastname: this.doctor.lastname,
                birthday: dateFormat,
                phone: this.doctor.phone,
                sex: selectedGender,
                code: this.doctor.code,
                password: pass,
                speciality_id: selectedSpeciality,
            }

            try {
                const encoded = await encrypt(JSON.stringify(newData));
                const { status, data } = await service.save_doctor(encoded)
                if (status === 200 || status === 201) {
                    onSuccess("¡Éxito!", "¡Doctor guardado exitosamente")
                    setTimeout(() => {
                        this.$router.push('/doctors');
                    }, 500);
                } else {
                    const message = utils.getErrorMessages(data.text);
                    await onError('Error', message).then(() => { })
                }
            } catch (error) {
                onError("¡Error!")
            }
           }
        },

        formatDate(dateString) {
            const date = new Date(dateString);

            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');

            const formattedDate = `${year}-${month}-${day}`;

            return formattedDate;
        }
    }
}
</script>

<style scoped lang="scss">
@import "@/styles/colors.scss";

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
    color: $red-primary;
}

.form-label-required::after {
    content: " *";
    color: $red-primary;
}

.invalid-field-custom .p-inputtext {
    border-color: $red-primary !important;
    box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
}

.invalid-field-custom label {
    color: $red-primary !important;
}

.invalid-field-custom .pi {
    color: $red-primary !important;
}
</style>