<template>
  <div class="h-100 content-exp">
    <Header :title="'Modificar expediente ' + folio"/>
    <div class="form">
      <transition-group name="fade">
        <Loader v-if="isLoading" key="load"/>
        <div v-else key="content" class="vertical-align">
          <Unautorized message="No tienes permisos para modificar este expediente" color="white" v-if="unauthorized"/>
          <NotFound message="Expediente no encontrado" color="white" v-if="!expFound && !unauthorized"/>
          <form @submit.prevent="update(expedient)" v-if="expFound">
            <div>
              <BButton class="rounded-pill w-auto mb-4" variant="danger" @click="goBack()">
                <BIcon icon="arrow-left"/>
                <span class="me-3"> Regresar </span>
              </BButton>
              <div :class="[{'transition-up': noForm === 1}, {transition: noForm !== 1}]">
                <h4 class="mb-5">Datos personales</h4>
                <div class="row">
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <InputText id="field-name" v-model=" v$.name.$model "
                               :class="{ 'p-invalid': v$.name.$error }" class="w-100" type="text"/>
                    <label for="field-name">Nombre</label>
                  </span>
                    <div v-if="v$.name.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.name.required.$invalid" class="error-messages">
                        {{ v$.name.required.$message }}
                      </p>
                      <p v-if="v$.name.onlyLettersAndAccents.$invalid" class="error-messages">
                        {{ v$.name.onlyLettersAndAccents.$message }}
                      </p>
                      <p v-if="v$.name.length.$invalid" class="error-messages">
                        {{ v$.name.length.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <InputText id="surname" v-model=" v$.surname.$model " :class="{ 'p-invalid': v$.surname.$error }"
                               class="w-100" type="text"/>
                    <label for="surname">Apellido</label>
                  </span>
                    <div v-if="v$.surname.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.surname.required.$invalid" class="error-messages">
                        {{ v$.surname.required.$message }}
                      </p>
                      <p v-if="v$.surname.onlyLettersAndAccents.$invalid" class="error-messages">
                        {{ v$.surname.onlyLettersAndAccents.$message }}
                      </p>
                      <p v-if="v$.surname.length.$invalid" class="error-messages">
                        {{ v$.surname.length.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <InputText id="lastname" v-model=" v$.lastname.$model " :class="{ 'p-invalid': v$.lastname.$error }"
                               class="w-100" type="text"/>
                    <label for="lastname">Apellido Materno</label>
                  </span>
                    <div v-if="v$.lastname.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.lastname.onlyLettersAndAccents.$invalid" class="error-messages">
                        {{ v$.lastname.onlyLettersAndAccents.$message }}
                      </p>
                      <p v-if="v$.lastname.length.$invalid" class="error-messages">
                        {{ v$.lastname.length.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="always-active w-100 position-relative">
                    <InputText id="birthday" v-model.lazy="v$.birthday.$model" :class="{ 'p-invalid': v$.birthday.$error }"
                               class="w-100" type="date" placeholder="Fecha de nacimiento"/>
                    <label for="birthday">Fecha de Nacimiento</label>
                  </span>
                    <div v-if="v$.birthday.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.birthday.required.$invalid" class="error-messages">
                        {{ v$.birthday.required.$message }}
                      </p>
                      <p v-if="v$.birthday.isEighteenYearsOld.$invalid" class="error-messages">
                        {{ v$.birthday.isEighteenYearsOld.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label">
                      <Dropdown id="dropdown" v-model=" v$.sex.$model " :class="{ 'p-invalid': v$.sex.$error }"
                                :options="genders" class="w-100"
                                optionLabel="name"/>
                      <label for="dropdown">Sexo</label>
                  </span>
                    <div v-if="v$.sex.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.sex.required.$invalid" class="error-messages">
                        {{ v$.sex.required.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <InputText id="phone" v-model=" v$.phone.$model " :class="{ 'p-invalid': v$.phone.$error }"
                               class="w-100" type="text"/>
                    <label for="phone">Teléfono</label>
                  </span>
                    <div v-if="v$.phone.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.phone.required.$invalid" class="error-messages">
                        {{ v$.phone.required.$message }}
                      </p>
                      <p v-if="v$.phone.isPhoneNumber.$invalid" class="error-messages">
                        {{ v$.phone.isPhoneNumber.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <InputText id="place_of_birth" v-model=" v$.place_of_birth.$model "
                               :class="{ 'p-invalid': v$.place_of_birth.$error }" class="w-100" type="text"/>
                    <label for="place_of_birth">Lugar de Nacimiento</label>
                  </span>
                    <div v-if="v$.place_of_birth.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.place_of_birth.required.$invalid" class="error-messages">
                        {{ v$.place_of_birth.required.$message }}
                      </p>
                      <p v-if="v$.place_of_birth.onlyLettersAndAccents.$invalid" class="error-messages">
                        {{ v$.place_of_birth.onlyLettersAndAccents.$message }}
                      </p>
                      <p v-if="v$.place_of_birth.length.$invalid" class="error-messages">
                        {{ v$.place_of_birth.length.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <Dropdown id="marital_status" v-model=" v$.marital_status.$model "
                              :class="{ 'p-invalid': v$.marital_status.$error }"
                              :options="marital" class="w-100" optionLabel="name" type="text"/>
                    <label for="marital_status">Estado Civil</label>
                  </span>
                    <div v-if="v$.marital_status.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.marital_status.required.$invalid" class="error-messages">
                        {{ v$.marital_status.required.$message }}
                      </p>
                    </div>
                  </div>

                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <InputText id="email" v-model=" v$.email.$model " :class="{ 'p-invalid': v$.email.$error }"
                               class="w-100" type="email"/>
                    <label for="email">Correo Electrónico</label>
                  </span>
                    <div v-if="v$.email.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.email.required.$invalid" class="error-messages">
                        {{ v$.email.required.$message }}
                      </p>
                      <p v-if="v$.email.email.$invalid" class="error-messages">
                        {{ v$.email.email.$message }}
                      </p>
                      <p v-if="v$.email.length.$invalid" class="error-messages">
                        {{ v$.email.length.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label">
                      <Dropdown id="dropdown" v-model=" v$.gender.$model " :class="{ 'p-invalid': v$.gender.$error }"
                                :options="genders" class="w-100"
                                optionLabel="name"/>
                      <label for="dropdown">Género</label>
                  </span>
                    <div v-if="v$.gender.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.gender.required.$invalid" class="error-messages">
                        {{ v$.gender.required.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-4 col-md-6 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <Textarea id="occupation" v-model=" v$.occupation.$model "
                              :class="{ 'p-invalid': v$.occupation.$error }"
                              class="w-100" type="text"/>
                    <label for="occupation">Ocupación</label>
                  </span>
                    <div v-if="v$.occupation.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.occupation.required.$invalid" class="error-messages">
                        {{ v$.occupation.required.$message }}
                      </p>
                      <p v-if="v$.occupation.onlyLettersAndAccents.$invalid" class="error-messages">
                        {{ v$.occupation.onlyLettersAndAccents.$message }}
                      </p>
                      <p v-if="v$.occupation.length.$invalid" class="error-messages">
                        {{ v$.occupation.length.$message }}
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <div :class="[{'transition-up': noForm === 2}, {transition: noForm !== 2}]">
                <h4 class="mb-5">Datos médicos</h4>
                <div class="row">
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
                    <label for="weight">Peso</label>
                    <InputNumber id="weight" v-model=" v$.weight.$model " :class="{ 'p-invalid': v$.weight.$error }"
                                 :maxFractionDigits="2" :minFractionDigits="2" class="w-100" mode="decimal"/>
                    <div v-if="v$.weight.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.weight.required.$invalid" class="error-messages">
                        {{ v$.weight.required.$message }}
                      </p>
                      <p v-if="v$.weight.weight.$invalid" class="error-messages">
                        {{ v$.weight.weight.$message }}
                      </p>
                      <p v-if="v$.weight.min.$invalid" class="error-messages">
                        {{ v$.weight.min.$message }}
                      </p>
                      <p v-if="v$.weight.max.$invalid" class="error-messages">
                        {{ v$.weight.max.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
                    <label for="height">Altura</label>
                    <InputNumber id="height" v-model=" v$.height.$model " :class="{ 'p-invalid': v$.height.$error }"
                                 :maxFractionDigits="2" :minFractionDigits="2" aria-valuemax="2.6" aria-valuemin="1.2"
                                 aria-valuenow="" class="w-100" mode="decimal"/>
                    <div v-if="v$.height.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.height.required.$invalid" class="error-messages">
                        {{ v$.height.required.$message }}
                      </p>
                      <p v-if="v$.height.weight.$invalid" class="error-messages">
                        {{ v$.height.weight.$message }}
                      </p>
                      <p v-if="v$.height.min.$invalid" class="error-messages">
                        {{ v$.height.min.$message }}
                      </p>
                      <p v-if="v$.height.max.$invalid" class="error-messages">
                        {{ v$.height.max.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-12 col-md-12 col-12 mb-5">
                  <span class="p-float-label w-100">
                    <Textarea id="allergies" v-model="v$.allergies.$model "
                              :class="{ 'p-invalid': v$.allergies.$error }"
                              class="w-100" type="text-area"/>
                    <label for="allergies">Alergias</label>
                  </span>
                    <div v-if="v$.allergies.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.allergies.required.$invalid" class="error-messages">
                        {{ v$.allergies.required.$message }}
                      </p>
                      <p v-if="v$.allergies.onlyLettersAndAccents.$invalid" class="error-messages">
                        {{ v$.allergies.onlyLettersAndAccents.$message }}
                      </p>
                      <p v-if="v$.allergies.length.$invalid" class="error-messages">
                        {{ v$.allergies.length.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-6 col-12 mb-5">
                    <MultiSelect v-model="v$.pathologies.$model" :filter="true" :options="pathologies"
                                 class="w-100 multiselect-custom mb-2"
                                 display="chip" optionLabel="name"
                                 placeholder="Seleccione sus patologías">
                      <template #value="slotProps">
                        <div v-for="(option, ind) of slotProps.value" :key="ind"
                             v-tooltip="option.description" class="p-multiselect-token">
                          <span class="p-multiselect-token-label">{{ option.name }}</span>
                          <span class="p-multiselect-token-icon pi pi-times-circle"
                                @click="removePathology(option.id, ind)"></span>
                        </div>
                      </template>
                      <template #option="slotProps">
                        <div class="country-item">
                          <div>{{ slotProps.option.name }}</div>
                        </div>
                      </template>
                    </MultiSelect>
                    <template v-if="v$.pathologies.$model.length > 0">
                      <div class="mb-2" v-for="(path, ind) in v$.pathologies.$model">
                        <InputText :placeholder="'Descripción para ' + v$.pathologies.$model[ind].name" v-model="v$.pathologies.$model[ind].desc"
                                   class="w-100" type="text" @input="isValidPathology(ind, v$.pathologies.$model[ind].desc)"/>
                        <div v-if="errorPathologies[ind]" class="text-danger text-start pt-2">
                          <p v-if="v$.pathologies.$model[ind].desc.length > 70" class="error-messages">
                            La enfermedad debe tener menos de 70 caracteres
                          </p>
                          <p v-if="!newregex().test(v$.pathologies.$model[ind].desc)"
                             class="error-messages">
                            Caracteres no válidos
                          </p>
                        </div>
                      </div>
                    </template>
                  </div>
                  <div class="col-lg-6 col-12 mb-5">
                    <div v-for="(disease, ind) in diseases" class="w-100 mb-2">
                      <div class="h-auto p-inputgroup ">
                    <span class="p-float-label w-100">
                      <InputText id="diseases" v-model="diseases[ind].name" :class="{ 'p-invalid': errorDiseases[ind] }"
                                 class="w-100" type="text"
                                 @input="isValidDisease(ind, diseases[ind].name, diseases[ind].description)"/>
                      <InputText id="description" v-model="diseases[ind].description"
                                 :class="{ 'p-invalid': errorDiseases[ind] }"
                                 class="w-100"
                                 placeholder="Descripción" type="text"
                                 @input="isValidDisease(ind, diseases[ind].name, diseases[ind].description)"/>
                      <span v-if="ind !== 0" class="p-inputgroup-addon">
                          <Button class="p-button-rounded p-button-secondary p-button-outlined"
                                  icon="pi pi-trash"
                                  style="width:50px; height: 30px;"
                                  @click="removeDisease(ind)">
                          </Button>
                      </span>
                      <span class="p-inputgroup-addon">
                          <Button class="p-button-rounded p-button-secondary p-button-outlined" icon="pi pi-plus"
                                  style="width:50px; height: 30px;"
                                  @click="addDisease()">
                          </Button>
                      </span>
                      <label for="allergies">Enfermedad</label>
                    </span>
                      </div>
                      <div v-if="errorDiseases[ind]" class="text-danger text-start pt-2">
                        <p v-if="diseases[ind].name.length < 3" class="error-messages">
                          La enfermedad debe tener al menos 3 caracteres
                        </p>
                        <p v-if="diseases[ind].name.length > 70" class="error-messages">
                          La enfermedad debe tener menos de 70 caracteres
                        </p>
                        <p v-if="!newregex().test(diseases[ind].name)" class="error-messages">
                          Caracteres no válidos
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row justify-content-between p-4">
              <BButton :disabled="noForm <= 1" class="rounded-pill w-auto" variant="secondary" @click="previousForm()">
                <BIcon icon="arrow-left"/>
                <span class="me-3"> Anterior </span></BButton>
              <BButton v-if="noForm <= 1" class="rounded-pill w-auto" variant="primary" @click="nextForm()"><span
                  class="me-3"> Siguiente </span>
                <BIcon icon="arrow-right"/>
              </BButton>
              <BButton v-else :disabled="!(!v$.$invalid && setNow && !errorDiseases.find(val => val === true) && !errorPathologies.find(val => val === true))"
                       class="rounded-pill w-auto" type="submit" variant="success"><span
                  class="me-3"> Guardar </span>
              </BButton>
            </div>
          </form>
        </div>
      </transition-group>
    </div>
  </div>

</template>

<script>
import Header from "@/components/Header.vue";
import MultiSelect from 'primevue/multiselect';
import {getPathologies, saveExpedient, updateExpedient} from "@/modules/expedient/services/service-expedient";
import {decrypt, encrypt} from "@/config/security";
import Loader from "@/components/loader.vue";
import Dropdown from 'primevue/dropdown';
import {email, helpers, required} from "@vuelidate/validators";
import {newregex} from "@/utils/regex";
import {isBefore, subYears} from "date-fns";
import {reactive} from "@vue/composition-api";
import {useVuelidate} from "@vuelidate/core";
import InputNumber from 'primevue/inputnumber';
import Textarea from 'primevue/textarea';
import {onError, onQuestion, onSuccess} from "@/kernel/alerts";
import {getNamesByToken} from "@/kernel/utils";
import {getExpedient} from "@/modules/treatment/services/teatment-service";
import Unautorized from "@/components/Unautorized.vue";
import NotFound from "@/components/NotFound.vue";

export default {
  name: 'ModifyExpedient',
  components: {NotFound, Unautorized, Loader, Header, MultiSelect, Dropdown, InputNumber,Textarea},
  data() {
    return {
      selectedPathologies: null,
      pathologies: [],
      diseases: [{name: '', description: ''}],
      genders: [{name: 'Masculino', value: 'Masculino'}, {name: 'Femenino', value: 'Femenino'}],
      marital: [{name: 'Soltero', value: 'Soltero'}, {name: 'Casado', value: 'Casado'}, {
        name: 'Viudo',
        value: 'Viudo'
      }],
      folio: '',
      params: {},
      errorDiseases: [],
      errorPathologies: [],
      noForm: 1,
      isLoading: true,
      unauthorized: false,
      expFound: false,
      setNow: false
    }
  },

  setup() {
    const expedient = reactive({
      name: '',
      surname: '',
      lastname: '',
      birthday: null,
      sex: '',
      phone: '',
      place_of_birth: '',
      marital_status: '',
      email: '',
      gender: '',
      occupation: '',
      weight: 0,
      height: 0,
      allergies: '',
      pathologies: [],
    })

    const isEighteenYearsOld = (value) => {
      const eighteenYearsAgo = subYears(new Date(), 3)
      return isBefore(new Date(value), eighteenYearsAgo)
    }

    const phoneRegex = /^(?:\d{3}-\d{3}-\d{4}|\d{10})$/
    const weightRegex = /^[0-9]*\.?[0-9]{0,2}$/

    const rules = {
      name: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Nombre no válido", (value) => value.length > 3 && value.length < 70),
      },
      surname: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 3 && value.length < 70),
      },
      lastname: {
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => {
          if (value === '') return true
          return newregex.test(value)
        }),
        length: helpers.withMessage("Apellido no válido", (value) => {
          if (value === '') return true
          return value.length < 70 && value.length > 3
        }),
      },
      birthday: {
        required: helpers.withMessage("Campo obligatorio", required),
        isEighteenYearsOld: helpers.withMessage("Fecha inválida", (value) => isEighteenYearsOld(value)),
      },
      sex: {
        required: helpers.withMessage("Campo obligatorio", required),
      },
      phone: {
        required: helpers.withMessage("Campo obligatorio", required),
        isPhoneNumber: helpers.withMessage("Número de teléfono no válido", (value) => phoneRegex.test(value.toString())),
      },
      place_of_birth: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Lugar de nacimiento no válido", (value) => value.length > 3 && value.length < 150),
      },
      marital_status: {
        required: helpers.withMessage("Campo obligatorio", required),
      },
      email: {
        required: helpers.withMessage("Campo obligatorio", required),
        email: helpers.withMessage("Correo electrónico no válido", email),
        length: helpers.withMessage("Email no válido", (value) => value.length > 3 && value.length < 100),
      },
      gender: {
        required: helpers.withMessage("Campo obligatorio", required),
      },
      occupation: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Ocupación no válida", (value) => value.length > 3 && value.length < 150),
      },
      weight: {
        required: helpers.withMessage("Campo obligatorio", required),
        weight: helpers.withMessage("Formato no válido", (value) => weightRegex.test(value)),
        min: helpers.withMessage("Peso mínimo de 30kg", (value) => value > 30),
        max: helpers.withMessage("Peso máximo de 300kg", (value) => value < 300),
      },
      height: {
        required: helpers.withMessage("Campo obligatorio", required),
        weight: helpers.withMessage("Formato no válido", (value) => weightRegex.test(value)),
        min: helpers.withMessage("Altura mínima de 1m", (value) => value > 1),
        max: helpers.withMessage("Altura máxima de 2.5m", (value) => value < 2.5),
      },
      allergies: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Alergias no válidas", (value) => value.length > 3 && value.length < 150),
      },
      pathologies: {},

    }

    const v$ = useVuelidate(rules, expedient)
    return {expedient, v$}
  },

  watch: {
    noForm() {
      if (this.noForm === 2) {
        setTimeout(() => {
          this.setNow = true
        }, 500)
      } else {
        this.setNow = false
      }
    }
  },

  methods: {
    newregex() {
      return newregex
    },
    async getPathologies() {
      this.isLoading = true
      const {status, data} = await getPathologies()
      if (status !== 200) {

      } else {
        const page = JSON.parse(await decrypt(data.result))
        this.pathologies = page.content
        this.pathologies.map(path =>{
          return{
            ...path,
            desc: ''
          }
        })
      }
      this.isLoading = false
    },

    async getExpedient() {
      const {status, data} = await getExpedient(await decrypt(this.$route.params.idExpedient))
      if (status === 200) {
        this.expFound = true;
        const expedient = JSON.parse(await decrypt(data.result))
        this.folio = expedient.folio
        this.params.id = expedient.id
        this.params.person_id = expedient.person_id
        this.params.physic_id = expedient.physic_id
        this.params.patient_id = expedient.patient_id
        this.v$.name.$model = expedient.name
        this.v$.surname.$model = expedient.surname
        this.v$.lastname.$model = expedient.lastname || ''
        this.v$.birthday.$model = new Date(expedient.birthday).toISOString().slice(0,10);
        this.v$.sex.$model = {name: expedient.sex, value: expedient.sex}
        this.v$.phone.$model = expedient.phone
        this.v$.place_of_birth.$model = expedient.place_of_birth
        this.v$.marital_status.$model = {name: expedient.marital_status, value: expedient.marital_status}
        this.v$.email.$model = expedient.email
        this.v$.gender.$model = {name: expedient.gender, value: expedient.gender}
        this.v$.occupation.$model = expedient.occupation
        this.v$.weight.$model = expedient.weight
        this.v$.height.$model = expedient.height
        this.v$.allergies.$model = expedient.allergies
        this.v$.pathologies.$model = expedient.pathologicalRecords.map(pathology => {
          return {
            id: pathology.typePathological,
            name: pathology.name,
            description: pathology.description,
            desc: pathology.description
          }
        })

        if (expedient.diseases.length > 0) {
          this.diseases = expedient.diseases
        }else {
          this.diseases = [{name: '', description: ''}]
        }
      }
      if (status !== 200 && status !== 401 && status !== 500) {
        switch (data.text) {
          case 'Not found':
            await onError('Ha ocurrido un error', 'No se ha encontrado el expediente')
            break;
          case 'Unauthorized user':
            await onError('Ha ocurrido un error', 'No tienes permisos para ver este expediente');
            this.unauthorized = true
            break;
          default:
            await onError('Ha ocurrido un error', 'Error al cargar el expediente')
            break;
        }
      }
    },

    async update(exp) {
      this.diseases.forEach((disease, ind) => {
        this.isValidDisease(ind, disease.name, disease.description);
        this.$set(this.errorDiseases, ind, this.errorDiseases[ind]);
      });
      if (this.errorDiseases.find(error => error === true)) {
        return
      }

      this.v$.pathologies.$model.forEach((pathology, ind) => {
        if (pathology.desc === undefined){
          pathology.desc = ''
        }
        this.isValidPathology(ind, pathology.desc);
        this.$set(this.errorPathologies, ind, this.errorPathologies[ind]);
      });
      if (this.errorPathologies.find(error => error === true)) {
        return
      }

      if (await onQuestion('Actualizar expediente', '¿Está seguro de actualizar este expediente?')) {

        const created_by = getNamesByToken() || 'Doctor'

        const {pathologies, sex, marital_status, gender, lastname, ...rest} = exp
        if (this.diseases.length === 1 && this.diseases[0].name === '' && this.diseases[0].description === '') {
          this.diseases = []
        }
        const req = {
          ...rest,
          ...this.params,
          created_by,
          sex: sex.name,
          marital_status: marital_status.name,
          gender: gender.name,
          pathologicalRecords: pathologies.map(pathology => {
            return {
              id: 1,
              typePathological: pathology.id,
              name: pathology.name,
              description: pathology.desc,
              expedient: 1
            }
          }),
          diseases: this.diseases.map(disease => {
            return {id:1, name: disease.name, description: disease.description}
          })
        }
        if (lastname !== ''){
          req.lastname = lastname
        }
        const {data, status} = await updateExpedient(req)
        if (status !== 200 && status !== 401 && status !== 500) {
          let message = ''
          switch (data.text) {
            case 'email already registered':
              message = 'El correo electrónico ya está registrado';
              break;
            case 'phone already registered':
              message = 'El número de teléfono ya está registrado';
              break;
            case 'Not found':
              message = 'No se ha encontrado el expediente';
              break;
            case 'Unauthorized user':
              message = 'No tienes permisos para modificar este expediente';
              this.unauthorized = true
              break;
            default:
              message = 'Error al guardar el expediente';
              break;
          }
          await onError('Ha ocurrido un error', message)
        }
        if (status === 200) {
          onSuccess('Expediente actualizado', 'El expediente ha sido actualizado exitosamente').then(async () => {
            const param = JSON.parse(await decrypt(data.result)).id
            await this.$router.push({name: 'treatments', params: {idExpedient: await encrypt(param.toString())}})
          })
        }
      }
    },

    removePathology(id, ind) {
      this.v$.pathologies.$model[ind].desc = ''
      this.v$.pathologies.$model = this.v$.pathologies.$model.filter(pathology => pathology.id !== id)
      this.errorPathologies.splice(ind, 1)
    },

    addDisease() {
      this.diseases.push({name: '', description: ''})
      this.errorDiseases.push(false)
    },
    removeDisease(index) {
      this.diseases.splice(index, 1)
      this.errorDiseases.splice(index, 1)
    },

    isValidDisease(ind, name, description) {
      if (ind === 0 && name.length === 0 && description.length === 0 && this.diseases.length === 1) {
        this.errorDiseases[ind] = false
        return
      }
      this.errorDiseases[ind] = !(name.length > 3 && name.length < 70 && newregex.test(name) &&
          description.length > 3 && description.length < 150 && newregex.test(description))
    },

    isValidPathology(ind, description) {
      this.errorPathologies[ind] = !(description.length > 3 && description.length < 150 && newregex.test(description))
    },

    nextForm() {
      this.noForm++
    },
    previousForm() {
      this.noForm--
    },

    goBack(){
      this.$router.go(-1)
    }
  },

  mounted() {
    this.isLoading = true
    Promise.all([
      this.getPathologies(),
      this.getExpedient()
    ]).then(() => {
      this.isLoading = false
    })
  }
}

</script>
<style scoped>

.form {
  padding: 20px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
  background-color: white;
  text-align: left;
  margin-top: 20px;
  overflow: hidden;
  min-height: 82%;
}

.vertical-align {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.transition {
  width: 0;
  height: 0;
  overflow: hidden;
}

.transition-up {
  animation-name: slide;
  animation-duration: 1s;
  animation-fill-mode: forwards;
}

@keyframes slide {
  from {
    width: auto;
    height: auto;
    opacity: 0;
    transform: translateX(-100%);
  }
  to {
    width: auto;
    height: auto;
    opacity: 1;
    transform: translateX(0);
  }

}

.error-messages {
  font-size: 13px;
  margin-bottom: 0;
}

.always-active label {
  color: #444;
  font-size: 12px;
  transform: translateY(-20px);
  position: absolute;
  top: -10px;
  left: 10px;
}

.always-active :focus-within label {
  color: #444;
  font-size: 12px;
  transform: translateY(-20px);
}
</style>

<style>
.fade-enter-active {
  transition: all 1s;
}

.fade-leave-active {
  transition: all .1s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>