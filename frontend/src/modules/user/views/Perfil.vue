<template>
  <div>
    <Header style="margin-bottom: 20px" title="Perfil"/>
    <Panel class="shadow-lg">
      <template #header>
        <div class="d-flex justify-content-between w-100">
          <p class="h5"><b>Bienvenido {{ data.id && data.person.name }}</b></p>
        </div>
      </template>
      <div class=" position-relative panel-content ">
        <transition-group name="fade">
          <Loader v-if="isLoading" key="loader" style="opacity:70%"/>
          <b-row v-else key="content">
            <b-col cols="12" lg="3" md="6">
              <b-img :src="availableImage ? getImage : require(`@/${getImage}`)" alt="profile" class="w-100 mb-2"
                     thumbnail/>
              <my-upload field="profile"
                         ref="upload"
                         @crop-success="cropSuccess"
                         @crop-upload-success="cropUploadSuccess"
                         no-circle
                         @close="toggleShow"
                         @cancel="toggleShow"
                         @toggle="toggleShow"
                         @abort="toggleShow"
                         @mouseup="toggleShow"
                         :model-value="show"
                         method="post"
                         :width="300"
                         :height="300"
                         :lang-ext="lang"
                         :url="url"
                         :headers="headers">
              </my-upload>
              <div class="d-flex justify-content-center w-100">
                <b-row>
                  <b-col cols="12">
                    <h4>{{fullName}}</h4>
                  </b-col>
                </b-row>
              </div>
              <b-row>
                <b-col class="d-flex justify-content-center" cols="12">
                  <p><label style="font-weight: lighter; font-size: 22px;">{{ data.speciality ? data.speciality.name : 'Administrador'}}</label></p>
                </b-col>
              </b-row>
              <b-row>
                <b-col cols="12" class="d-flex justify-content-center w-100">
                  <Button icon="pi pi-camera" class="p-button-rounded p-button-outlined button-margin" @click="toggleShow" v-tooltip.top="'Cambiar foto'" />
                  <Button icon="pi pi-lock-open" class="p-button-rounded p-button-outlined button-margin" @click="() => $router.push({name : 'recovery-password'})" v-tooltip.bottom="'Cambiar contraseña'"/>
                </b-col>
              </b-row>
            </b-col>
            <b-col cols="12" lg="9" md="6">
              <form @submit.prevent="updateProfile(profile)">
                <b-row>
                  <b-col class="d-flex justify-content-left" cols="12"
                         style="border-bottom:0.2px solid #d3d3d3; margin-bottom: 10px;">
                    <h5>Información personal</h5>
                  </b-col>
                </b-row>
                <b-row class="mt-4">
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
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
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
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
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
                    <span class="p-float-label w-100">
                      <InputText id="lastname" v-model=" v$.lastname.$model "
                                 :class="{ 'p-invalid': v$.lastname.$error }"
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
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
                    <span class="always-active w-100 position-relative">

                      <InputText id="birthday" v-model.lazy="v$.birthday.$model"
                                 :class="{ 'p-invalid': v$.birthday.$error }"
                                 class="w-100" placeholder="Fecha de nacimiento" type="date"/>
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
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
                    <span class="p-float-label">
                        <Dropdown id="dropdown" v-model=" v$.sex.$model " :class="{ 'p-invalid': v$.sex.$error }"
                                  :options="sex" class="w-100 text-start"
                                  optionLabel="name"/>
                        <label for="dropdown">Sexo</label>
                    </span>
                    <div v-if="v$.sex.$dirty" class="text-danger text-start pt-2">
                      <p v-if="v$.sex.required.$invalid" class="error-messages">
                        {{ v$.sex.required.$message }}
                      </p>
                    </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-12 mb-5">
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
                </b-row>
                <div class="w-100 d-flex justify-content-end">
                  <Button
                      :disabled="v$.$invalid  || isSaving" :loading="isSaving" icon="pi pi-check"
                      class="p-button-rounded button-style" type="submit" variant="success" label="Guardar">
                  </Button>
                </div>
              </form>
            </b-col>
          </b-row>
          <ChangePassword  :visible.sync="displayModal" key="modal"/>
        </transition-group>
      </div>
    </Panel>
  </div>
</template>

<script>

import Header from "@/components/Header.vue";
import {getImage, getProfile, updateProfile} from "@/modules/user/service/user-service";
import {getUserIdByToken} from "@/kernel/utils";
import {decrypt} from "@/config/security";
import Loader from "@/components/loader.vue";
import {getServerUrl} from "@/config/http-client.gateway";
import {reactive} from "@vue/composition-api";
import {helpers, required} from "@vuelidate/validators";
import {regexName} from "@/utils/regex";
import {useVuelidate} from "@vuelidate/core";
import {isBefore, subYears} from "date-fns";
import Dropdown from "primevue/dropdown";
import myUpload from 'vue-image-crop-upload';
import {onError, onQuestion, onSuccess, onWarning} from "@/kernel/alerts";
import ChangePassword from '@/modules/user/views/Change-password.vue';
export default {
  components: {Dropdown, Loader, Header, 'my-upload': myUpload, ChangePassword},
  data() {
    return {
      data:{},
      sex:[
        {value: 'Masculino', name: 'Masculino'},
        {value: 'Femenino', name: 'Femenino'}
      ],
      show: false,
      imgDataUrl: '',
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      },
      url: getServerUrl() + '/user/upload/',
      lang: {
        hint: 'Haga clic para subir una imagen',
        loading: 'Cargando imagen…',
        noSupported: 'El navegador no soporta cortar la imagen.',
        success: 'Carga exitosa.',
        fail: 'Carga fallida.',
        preview: 'Previsualización',
        btn: {
          off: 'Cancelar',
          close: 'Cerrar',
          back: 'Atrás',
          save: 'Guardar'
        },
        error: {
          onlyImg: 'Solo archivos de imagen son permitidos.',
          outOfSize: 'El archivo excede el tamaño máximo: 2mb.',
          lowestPx: 'La imagen es demasiado pequeña. Ancho mínimo: 300px.',
        }
      },
      isLoading: true,
      isSaving: false,
      availableImage: true,
      displayModal: false,
    }
  },

  setup() {
    const profile = reactive({
      name: '',
      surname: '',
      lastname: '',
      sex: '',
      phone: '',
      birthday: null,
    })

    const isEighteenYearsOld = (value) => {
      const eighteenYearsAgo = subYears(new Date(), 3)
      return isBefore(new Date(value), eighteenYearsAgo)
    }

    const phoneRegex = /^(?:\d{3}-\d{3}-\d{4}|\d{10})$/

    const rules = {
      name: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => regexName.test(value)),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 3 && value.length < 150),
      },
      surname: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => regexName.test(value)),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 3 && value.length < 150),
      },
      lastname: {
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => value.length > 0 ? regexName.test(value) : true),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 0 ? (value.length > 3 && value.length < 150) : true),
      },
      sex: {
        required: helpers.withMessage("Campo obligatorio", required)
      },
      birthday: {
        required: helpers.withMessage("Campo obligatorio", required),
        isEighteenYearsOld: helpers.withMessage("Fecha inválida", (value) => isEighteenYearsOld(value))
      },
      phone: {
        required: helpers.withMessage("Campo obligatorio", required),
        isPhoneNumber: helpers.withMessage("Número de teléfono no válido", (value) => phoneRegex.test(value.toString())),
      }
    }

    const v$ = useVuelidate(rules, profile)
    return {profile, v$}
  },

  watch:{
    show(value) {
      const element = document.querySelector('.vicp-operate');
      const element2 = document.querySelector('.vicp-close');
      if (element) {
        if (value) {
          element.addEventListener('click', this.toggleShow);
          element2.addEventListener('click', this.toggleShow);
        } else {
          element.removeEventListener('click', this.toggleShow);
          element2.removeEventListener('click', this.toggleShow);
        }
      }
    },
  },

  methods: {
    async getProfile() {
      this.isLoading = true
      const id = getUserIdByToken()
      const {status, data} = await getProfile(id)
      if (status !== 200) {
        let message = 'Error al obtener el perfil'
        switch (data.text) {
          case 'User not found':
            message = 'Usuario no encontrado'
            break
          case 'Unauthorized user':
            message = 'No tienes permisos para ver este perfil'
            break
        }
        this.$toast.add({severity: 'error', summary: 'Error', detail: message, life: 5000})
      } else {
        this.data = JSON.parse(await decrypt(data.result))
        this.v$.name.$model = this.data.person.name
        this.v$.surname.$model = this.data.person.surname
        this.v$.lastname.$model = this.data.person.lastname || ''
        this.v$.sex.$model = {name: this.data.person.sex, value: this.data.person.sex}
        this.v$.birthday.$model = new Date(this.data.person.birthday).toISOString().split('T')[0]
        this.v$.phone.$model = this.data.person.phone

        const {status} = await getImage(this.data.img)
        if (status !== 200) {
          this.availableImage = false
          await onWarning('Imagen inexistente', 'La imagen no fue encontrada')
        }

      }
      this.isLoading = false
    },

    async updateProfile(profile) {

      if (await onQuestion('Quiere actualizar su perfil', 'Solo actualizará información personal')) {
        const {name, surname, lastname, sex, birthday, phone} = profile

        const req = {
          id: this.data.id,
          personProfile: {
            id: this.data.person.id,
            name,
            surname,
            sex: sex.value,
            birthday,
            phone
          }
        }
        if (lastname !== '') {
          req.personProfile.lastname = lastname
        }
        this.isSaving = true
        const {status, data} = await updateProfile(req)

        if (status !== 200) {
          let message = 'Ocurrió un error al actualizar el perfil'
          switch (data.text) {
            case 'User not found':
              message = 'Usuario no encontrado';
              break;
            case 'Unauthorized user':
              message = 'No tiene permisos para actualizar este perfil';
              break
            case 'Phone already registered':
              message = 'Teléfono ya registrado';
              break
          }
          await onError('Ocurrió un error', message)
        } else {
          await onSuccess('Información actualizada', 'Perfil actualizado correctamente')
              .then(() => this.getProfile())
        }
        this.isSaving = false
      }
    },

    toggleShow() {
      this.show = !this.show;
    },

    cropSuccess(imgDataUrl, field){
      this.imgDataUrl = imgDataUrl;
    },

    async cropUploadSuccess(jsonData, field){
      if (jsonData.type === 'SUCCESS') {
        this.availableImage = true
        this.toggleShow()
        await onSuccess('Imagen actualizada', 'Imagen actualizada correctamente')
            .then(() => {
              this.getProfile()
            })
      }
    },
    openChangePassword(){
      this.displayModal = true
    }
  },

  computed: {
    getImage() {
      const timestamp = Date.now();
      return this.availableImage ? `${getServerUrl()}${this.data.img}?t=${timestamp}` : 'assets/latido-del-corazon.gif'
    },
    fullName(){
      return this.data.id ? `${this.data.person.name} ${this.data.person.surname} ${(this.data.person.lastname || '')}` : ''
    }
  },

  mounted() {
    this.getProfile()
  }
}
</script>

<style scoped>
.panel-content {
  min-height: 60vh;
  background-color: white;
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

.button-margin{
  margin-left: 10px
}

.button-style{
    background: #2a715a;
    border: none;
}
.button-style:hover{
    background-color: #368368 !important;
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