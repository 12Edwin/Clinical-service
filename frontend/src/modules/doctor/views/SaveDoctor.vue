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
                                <label for="field-lastname" class="form-label-required">1er Apellido</label>
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
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.surname.$error }">
                                <i class="pi pi-user" />
                                <InputText id="field-surname" type="text" v-model="v$.surname.$model" />
                                <label for="field-surname">2do Apellido</label>
                            </span>
                            <div class="text-danger text-start pt-1">
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

                </b-row>
                <b-row>
                    <b-col class="mt-4" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right"
                                :class="{ 'invalid-field-custom': v$.birthDate.$error }">
                                <i class="pi pi-calendar" />
                                <Calendar id="field-birthdate" :maxDate="getNewDate()" v-model="v$.birthDate.$model"
                                    dateFormat="dd-mm-yy" style="cursor: pointer;" />
                                <label for="field-birthdate" class="form-label-required">Fecha de nacimiento</label>
                            </span>
                            <div class="text-danger text-start pt-1">
                                <p class="error-messages" v-if="v$.birthDate.$dirty && v$.birthDate.required.$invalid">
                                    {{ v$.birthDate.required.$message }}
                                </p>
                            </div>
                        </div>
                    </b-col>
                    <b-col class="mt-4" cols="12" md="6" lg="4">
                        <div class="field">
                            <Dropdown :class="{ 'invalid-field-custom': v$.sex.$error }"
                                class="form-label-required text-start" v-model="v$.sex.$model" :options="gneres"
                                optionLabel="value" placeholder="Seleccione un Género" />
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
                    <b-col class="mt-3" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right">
                                <i class="pi pi-at" />
                                <InputText id="field-email" type="email" :useGrouping="false" />
                                <label for="field-email">Correo electrónico</label>
                            </span>
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
                                <InputText type="text" v-model="v$.code.$model" id="field-phone" :useGrouping="false" />
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
                <!-- <b-row>
                    <b-col class="mt-3" cols="12" md="6" lg="4">
                        <div class="field">
                            <span class="p-float-label p-input-icon-right"
                            :class="{ 'invalid-field-custom': v$.photo.$error }"
                            >
                                <FileUpload mode="basic" name="perfil_photo[]" :fileLimit="1"
                                    accept="png, jpeg" v-model="v$.photo.$model" />
                            </span>
                            <div class="text-danger text-start pt-1">
                                <p class="error-messages" v-if="v$.photo.$dirty && v$.photo.file.$invalid">
                                    {{ v$.photo.file.$message }}
                                </p>
                            </div>
                        </div>
                    </b-col>
                </b-row> -->

            </div>
        </template>
        <template #footer>
            <b-row class="mt-2">
                <b-col cols="12" class="d-flex justify-content-end">
                    <Button icon="pi pi-check" @click="verifyDoctors()" label="Guardar" class="p-button-rounded"
                        :disabled="v$.$invalid" :loading="isLoading" />
                </b-col>
            </b-row>
            <Toast />
        </template>
    </Card>
</template>

<script>
import Calendar from 'primevue/calendar';
import InputNumber from 'primevue/inputnumber';
import Card from 'primevue/card';
import Dropdown from 'primevue/dropdown';
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, minLength, maxLength } from '@vuelidate/validators'
import { words, errorMessage, acceptedFormatsForImages } from "@/utils/regex"
import RadioButton from 'primevue/radiobutton';
import InputMask from 'primevue/inputmask';
import { decrypt, encrypt } from '@/config/security';
import service from '@/modules/doctor/services/doctor-service';
import InputText from 'primevue/inputtext/InputText';
import Toast from 'primevue/toast';
import specialityServices from '@/modules/speciality/services/speciality-services';
import FileUpload from 'primevue/fileupload';

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
        FileUpload
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
            photo: '',
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
            surname: {
                onlyLettersAndAccents: helpers.withMessage(errorMessage, (value) => words.test(value)),
                minLength: helpers.withMessage("El apellido debe de contener al menos 3 caracteres", minLength(3))
            },
            birthDate: {
                required: helpers.withMessage("Debes agregar una fecha de nacimiento", required),
                // dateFormat: helpers.withMessage("El formato ingresado no coincide con el requerido", (value) => dateFormatRegex.test(value))
            },
            sex: {
                required: helpers.withMessage("Debes seleccionar un género", required),

            },
            phone: {
                required: helpers.withMessage("Debes seleccionar un género", required),
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
            },
            password: {
                required: helpers.withMessage("Debes de agregar una constraseña", required),
                minLength: helpers.withMessage("La contraseña debe de contar con mínimo 6 digitos", minLength(6)),
                maxLength: helpers.withMessage("La contraseña debe de contar con maximo 20 digitos", maxLength(20))
            },
            photo: {
                file: helpers.withMessage("La foto debe ser un archivo válido", file => {
                    if (!file) return false;
                    const maxFileSize = 10 * 1024 * 1024; // 10MB

                    // Verificar el tipo de archivo
                    const fileTypeValid = acceptedFormats.includes(file.type);

                    // Verificar el tamaño del archivo
                    const fileSizeValid = file.size <= maxFileSize;
                    return fileTypeValid && fileSizeValid;
                })
            }

        }
        const v$ = useVuelidate(rules, doctor)
        return { doctor, v$ }
    },
    data() {
        return {
            isLoading: false,
            selectedGender: null,
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
            acceptedFormats: acceptedFormatsForImages,
            acceptTypes: acceptedFormatsForImages.map(format => `image/${format === '.jpg' ? 'jpeg' : format.substr(1)}`)
        }
    },
    mounted() {
        this.getSpecialities();
    },
    methods: {
        getNewDate() {
            return new Date()
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

        async verifyDoctors() {
            if (this.doctor.name || this.doctor.lastname
                || this.doctor.surname || this.doctor.birthDate
                || this.doctor.phone || this.doctor.curp != ""
            ) {
                this.saveDoctor();
            } else {
                // this.v$.$touch();
                this.$toast.add({ severity: 'warn', summary: '¡Cuidado!', detail: 'Debes completar todos los campos requeridos', life: 3000 });
            }
        },


        async saveDoctor() {
            var pass = "root";
            var selectedGender = this.doctor.sex.value;
            let selectedSpeciality = this.doctor.speciality.id;
            var dateFormat = this.formatDate(this.doctor.birthDate);

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
                const { status } = await service.save_doctor(encoded)
                if (status === 200 || status === 201) {
                    this.$toast.add({ severity: 'success', summary: '¡Éxito!', detail: 'Registro exitoso', life: 3000 });
                    setTimeout(() => {
                        this.$router.push('/doctors');
                    }, 500);
                } else {
                    console.log("error en la peticion");
                    this.$toast.add({ severity: 'error', summary: '¡Hups!', detail: 'Algo Salio mal!!', life: 3000 });
                }
            } catch (error) {
                this.$toast.add({ severity: 'error', summary: '¡Hups!', detail: 'Algo Salio mal', life: 3000 });
            }
        },

        formatDate(dateString) {
            const date = new Date(dateString);

            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // El mes es base 0, por lo que sumamos 1
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