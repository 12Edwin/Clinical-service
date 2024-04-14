<template>
  <div class="w-100" style="height: 100vh">
    <div class="w-100 h-100 background">
      <div class="d-flex justify-content-center align-items-center h-100">
        <b-col cols="12" lg="4" md="10">
          <!-- Card que pide el token  -->
          <Card v-if="this.writeCode" class="cards h-100 shadow-lg">
            <template #header>
              <div class="d-flex justify-content-center align-items-center">
                <h4 class="m-1">Ingrese el código</h4>
              </div>
            </template>
            <template #content>
              <div class="p-fluid grid">
                <b-row>
                  <b-col>
                    <div class="d-flex justify-content-center">
                      <img alt="hospital_logo" class="img-style"
                           src="../../../assets/img/logo.png">
                    </div>
                  </b-col>
                </b-row>
                <b-row>
                  <b-col class="mb-3 d-flex justify-content-center" cols="12" lg="12">
                    <div class="field w-75">
                      <span class="p-float-label p-input-icon-right">
                          <i class="pi pi-eye"/>
                          <InputText v-model="v$.token.$model" type="text"/>
                          <label for="field-password">Ingrese el código que se le envió por
                              sms</label>
                      </span>
                    </div>
                  </b-col>
                  <b-col class="d-flex justify-content-center mt-3" cols="12">
                    <Button class="p-button-rounded button-style w-75" label="Enviar código"
                            @click="writePassword()"/>
                  </b-col>
                  <b-col class="d-flex justify-content-center mt-3" cols="12">
                    <Button class="p-button-link w-75" label="Iniciar sesión"
                            @click="$router.push({ name: 'login' })"/>
                  </b-col>
                </b-row>
              </div>
            </template>
          </Card>
          <!-- Card para ingresar las 2 contraseñas -->
          <Card v-if="this.writePass" class="cards shadow-lg">
            <template #header>
              <div class="d-flex justify-content-center align-items-center">
                <h4 class="m-1">Ingrese la nueva Contraseña</h4>
              </div>
            </template>
            <template #content>
              <div class="p-fluid grid">
                <b-row>
                  <b-col>
                    <div class="d-flex justify-content-center">
                      <img alt="hospital_logo" class="img-style"
                           src="../../../assets/img/logo.png">
                    </div>
                  </b-col>
                </b-row>
                <b-row>
                  <b-col class="mb-3 d-flex justify-content-center" cols="12" lg="12">
                    <div class="field w-75">
                      <span class="p-float-label p-input-icon-right">
                          <i class="pi pi-eye"/>
                          <InputText v-model="v$.password.$model" type="pasword"/>
                          <label for="field-password">Ingrese la nueva contraseña</label>
                      </span>
                    </div>
                  </b-col>
                  <b-col class="mb-3 d-flex justify-content-center" cols="12" lg="12">
                    <div class="field w-75">
                      <span class="p-float-label p-input-icon-right">
                          <i class="pi pi-eye"/>
                          <InputText v-model="v$.password2.$model" type="pasword"/>
                          <label for="field-password2">Vuelva a escribir la contraseña</label>
                      </span>
                    </div>
                  </b-col>
                  <b-col class="d-flex justify-content-center mt-3" cols="12">
                    <Button class="p-button-link w-75" label="Volver a enviar el código"
                            @click="sentNumber()"/>
                  </b-col>
                  <b-col class="d-flex justify-content-center mt-3" cols="12">
                    <Button class="p-button-rounded button-style w-75"
                            label="Cambiar contraseña" @click="sentNewPass()"/>
                  </b-col>
                </b-row>
              </div>
            </template>
          </Card>
          <!-- Card que pide el número del usuario -->
          <Card v-if="this.writeNumber" class="cards shadow-lg">
            <template #header>
              <div class="d-flex justify-content-center align-items-center">
                <h4 class="m-1">Ingresa tú número</h4>
              </div>
            </template>
            <template #content>
              <div class="p-fluid grid">
                <b-row>
                  <b-col>
                    <div class="d-flex justify-content-center">
                      <img alt="hospital_logo" class="img-style"
                           src="../../../assets/img/logo.png">
                    </div>
                  </b-col>
                </b-row>
                <b-row>
                  <b-col class="mb-3 d-flex justify-content-center" cols="12" lg="12">
                    <div class="field w-75">
                                                <span :class="{ 'invalid-field-custom': v$.phone.$error }"
                                                      class="p-float-label p-input-icon-right">
                                                    <i class="pi pi-phone"/>
                                                    <InputText id="field-phone" v-model="v$.phone.$model"
                                                               :useGrouping="false"
                                                               type="number"/>
                                                    <label for="field-phone">Número de
                                                        teléfono</label>
                                                </span>
                      <div class="text-danger text-start pt-1">
                        <p v-if="v$.phone.$dirty && v$.phone.minLength.$invalid"
                           class="error-messages">
                          {{ v$.phone.minLength.$message }}
                        </p>
                        <p v-if="v$.phone.$dirty && v$.phone.maxLength.$invalid"
                           class="error-messages">
                          {{ v$.phone.maxLength.$message }}
                        </p>
                      </div>
                    </div>
                  </b-col>
                  <b-col class="d-flex justify-content-center mt-3" cols="12">
                    <Button class="p-button-rounded button-style w-75" label="Enviar código"
                            @click="sentNumber()"/>
                  </b-col>
                  <b-col class="d-flex justify-content-center mt-3" cols="12">
                    <Button v-if="!user" class="p-button-link w-75" label="Iniciar sesión"
                            @click="$router.push({ name: 'login' })"/>
                    <Button v-if="user" class="p-button-rounded button-style w-75"
                            label="Cancelar"
                            @click="$router.push({ name: 'perfil' })"/>
                  </b-col>
                </b-row>
              </div>
            </template>
          </Card>

        </b-col>
      </div>
    </div>
    <Toast/>
  </div>
</template>

<script>
import Card from 'primevue/card/Card';
import InputNumber from 'primevue/inputnumber';
import {useVuelidate} from '@vuelidate/core'
import {helpers, maxLength, minLength, required} from '@vuelidate/validators'
import {reactive} from '@vue/composition-api'
import service from "@/modules/password-recovery/service/recovery-pass"
import {encrypt} from '@/config/security';
import Toast from 'primevue/toast';

export default {
  name: 'RecoveryPassword',
  components: {
    Card,
    InputNumber,
    Toast,
  },
  data() {
    return {
      isLoading: true,
      writeNumber: true,
      writePass: false,
      writeCode: false,
      user: false,
    }

  },
  mounted() {
    this.loggedUserExist();
  },
  setup() {
    const recovery = reactive({
      password: '',
      phone: '',
      password2: '',
      token: ''
    })

    const rules = {
      phone: {
        minLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", minLength(10)),
        maxLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", maxLength(10))
      },
      password: {
        required: helpers.withMessage("Debes de agregar una constraseña", required),
        minLength: helpers.withMessage("La contraseña debe de contar con mínimo 6 digitos", minLength(6)),
        maxLength: helpers.withMessage("La contraseña debe de contar con maximo 20 digitos", maxLength(20))
      },
      password2: {
        required: helpers.withMessage("Debes de agregar una constraseña", required),
        minLength: helpers.withMessage("La contraseña debe de contar con mínimo 6 digitos", minLength(6)),
        maxLength: helpers.withMessage("La contraseña debe de contar con maximo 20 digitos", maxLength(20))
      },
      token: {
        required: helpers.withMessage("Debes ingresar el token", required),
        minLength: helpers.withMessage("El token debe de contar con mínimo 5 carácteres", minLength(5)),
        maxLength: helpers.withMessage("El token debe de contar con maximo 5 carácteres", maxLength(5))
      },

    }
    const v$ = useVuelidate(rules, recovery)
    return {recovery, v$}
  },
  methods: {
    async sentNumber() {
      if (this.recovery.phone !== "") {
        const new_data = {
          phone: this.recovery.phone
        }
        const encoded = await encrypt(JSON.stringify(new_data));
        const {status} = await service.send_number(encoded);
        if (status === 200) {
          this.$toast.add({
            severity: 'success',
            summary: '¡Éxito!',
            detail: 'El código se envio exitosamente',
            life: 3000
          });
          this.validCode();
        } else if(status === 500) {
          this.$toast.add({
            severity: 'error',
            summary: '¡Ups!',
            detail: 'No se pudo enviar el código por sms',
            life: 3000
          });
        } else if(status === 400) {
          this.$toast.add({
            severity: 'error',
            summary: '¡Ups!',
            detail: 'El número no éxiste',
            life: 3000
          });
        } else {
          this.$toast.add({
            severity: 'warn',
            summary: '¡Ups!',
            detail: 'No se pudo enviar el código por sms!',
            life: 3000
          });
        }
      } 
    },

    validCode() {
      if (this.recovery.phone !== "") {
        this.writeCode = true;
        this.writeNumber = false;
        this.writePass = false;
      } 
    },

    writePassword() {
      if (this.recovery.token !== "") {
        this.writeCode = false;
        this.writeNumber = false;
        this.writePass = true;
      } 
    },
    async sentNewPass() {
      if (this.recovery.password && this.recovery.password2 !== "") {
        if (this.recovery.password === this.recovery.password2) {
          const new_data = {
            phone: this.recovery.phone,
            password: this.recovery.password,
            token: this.recovery.token,
          }
          const encoded = await encrypt(JSON.stringify(new_data));
          const {status} = await service.send_number(encoded);
          if (status === 200 || status === 201) {
            this.$toast.add({
              severity: 'success',
              summary: '¡Éxito!',
              detail: 'Contraseña restablecida con éxito',
              life: 3000
            });
          } else {
            this.$toast.add({
              severity: 'error',
              summary: '¡Cuidado!',
              detail: 'Tu contraseña no se pudo restablecer',
              life: 3000
            });
          }
        } else {
          this.$toast.add({severity: 'warn', summary: '¡Cuidado!', detail: 'Tus contraseñas no coinciden', life: 3000});
        }
      }
    },
    async loggedUserExist() {
      const userExist = await service.exist_user();
      this.user = userExist;
    }

  }
}
</script>

<style lang="scss" scoped>
@import "@/styles/colors.scss";

.background {
  height: 100%;
  justify-content: center;
  align-items: center;
  width: 100%;
  background: rgb(92, 171, 140);
  background: linear-gradient(180deg, rgba(92, 171, 140, 1) 0%, rgba(42, 113, 90, 1) 51%, rgba(0, 0, 0, 1) 100%);
}

.cards {
  width: 100%;
  height: 480px;
  border: 1px solid #ccc;
  border-radius: 10px 10px !important;
  padding: 10px;
  margin: 30px 0 30px 0;
}

.img-style {
  max-width: 200px !important;
  max-height: 200px;
}

.button-style {
  background-color: $green-primary;
  color: white;
  border: none;
}

.button-style {
  background-color: $green-primary;
  color: white;
  border: none;
}

 p{
  font-size: 12pt !important;
}
</style>