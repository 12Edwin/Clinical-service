<template>
  <div class="w-100" style="height: 100vh">
    <div class="w-100 h-100 background">
      <div class="d-flex justify-content-center align-items-center h-100">
        <b-col class="rounded" cols="12" lg="4" md="10" style="opacity:1; min-height: 60vh">
          <div class="w-100 h-100 rounded overflow-hidden" style="background-color:white">
            <!-- Card que pide el número del usuario -->
            <Card v-if="writeNumber" :class="{'trans-up': writeNumber}" class="cards h-100 my-0 shadow-lg">
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
                          <p v-if="v$.phone.$dirty && v$.phone.pattern.$invalid"
                             class="error-messages">
                            {{ v$.phone.pattern.$message }}
                          </p>
                        </div>
                      </div>
                    </b-col>
                    <b-col class="d-flex justify-content-center mt-3" cols="12">
                      <Button :disabled="v$.phone.$invalid || isLoading" class="p-button-rounded button-style w-75"
                              label="Enviar código" :loading="isLoading"
                              @click="sentNumber()"/>
                    </b-col>
                    <b-col class="d-flex justify-content-center mt-3" cols="12">
                      <Button v-if="!user" class="p-button-link w-75" label="Iniciar sesión"
                              @click="$router.push({ name: 'login' })"/>
                      <Button v-if="user" class="p-button-rounded p-button-danger p-button-outlined w-75"
                              label="Cancelar"
                              @click="$router.push({ name: 'perfil' })"/>
                    </b-col>
                  </b-row>
                </div>
              </template>
            </Card>

            <!-- Card que pide el token  -->
            <Card v-if="writeCode" :class="{'trans-up': writeCode}" class="trans my-0 cards h-100 shadow-lg">
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
                          <i class="pi pi-code"/>
                          <InputText v-model="v$.token.$model" maxlength="5" type="text"/>
                          <label for="field-password">Ingrese el código sms</label>
                      </span>
                      </div>
                    </b-col>
                    <b-col class="d-flex justify-content-center mt-3" cols="12">
                      <Button :disabled="v$.token.$invalid || isLoading" class="p-button-rounded button-style w-75"
                              label="Escribir contraseña" :loading="isLoading"
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
            <Card v-if="writePass" :class="{'trans-up': writePass}" class="trans my-0 cards shadow-lg">
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
                          <Password v-model="v$.password.$model" :feedback="false" toggleMask/>
                          <label for="field-password">Ingrese la nueva contraseña</label>
                      </span>
                        <div class="text-danger text-start pt-1">
                          <p v-if="v$.password.$dirty && v$.password.pattern.$invalid"
                             class="error-messages">
                            {{ v$.password.pattern.$message }}
                          </p>
                        </div>
                      </div>
                    </b-col>
                    <b-col class="mb-3 d-flex justify-content-center" cols="12" lg="12">
                      <div class="field w-75">
                      <span class="p-float-label p-input-icon-right">
                          <Password v-model="v$.password2.$model" :feedback="false" toggleMask/>
                          <label for="field-password2">Vuelva a escribir la contraseña</label>
                      </span>
                        <div class="text-danger text-start pt-1">
                          <p v-if="v$.password2.$dirty && v$.password2.pattern.$invalid"
                             class="mb-0 error-messages">
                            {{ v$.password2.pattern.$message }}
                          </p>
                          <p v-if="v$.password2.$dirty && v$.password2.equals.$invalid"
                             class="mb-0 error-messages">
                            {{ v$.password2.equals.$message }}
                          </p>
                        </div>
                      </div>
                    </b-col>
                    <b-col class="d-flex justify-content-center mt-3" cols="12">
                      <Button class="p-button-link w-75" label="Volver a enviar el código"
                              @click="sentNumber()"/>
                    </b-col>
                    <b-col class="d-flex justify-content-center mt-3" cols="12">
                      <Button :disabled="isLoading || v$.password.$invalid || v$.password2.$invalid"
                          class="p-button-rounded button-style w-75" :loading="isLoading"
                              label="Cambiar contraseña" @click="sentNewPass()"/>
                    </b-col>
                  </b-row>
                </div>
              </template>
            </Card>
          </div>

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
import {helpers} from '@vuelidate/validators'
import {reactive} from '@vue/composition-api'
import service from "@/modules/password-recovery/service/recovery-pass"
import {encrypt} from '@/config/security';
import Toast from 'primevue/toast';
import Password from 'primevue/password';

export default {
  name: 'RecoveryPassword',
  components: {
    Card,
    InputNumber,
    Toast,
    Password
  },
  data() {
    return {
      isLoading: false,
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

    const phonePattern = /^\d{10}$/;
    const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*.,;:?¿()])[A-Za-z\d!@#$%^&*.,;:?¿()]{6,}$/;

    const rules = {
      phone: {
        pattern: helpers.withMessage("Formato incorrecto", value => phonePattern.test(value))
      },
      password: {
        pattern: helpers.withMessage("Formato inválido, Debe contener: 1 mayúscula, 1 número, 1 caracter especial y el mínimo son 6 caracteres", value => passwordPattern.test(value)),
      },
      password2: {
        pattern: helpers.withMessage("Formato inválido", value => passwordPattern.test(value)),
        equals: helpers.withMessage("Las contraseñas no coinciden", value => value === recovery.password)
      },
      token: {
        length: helpers.withMessage("El token debe de contar con mínimo 5 carácteres", val => val.length === 5),
      },

    }
    const v$ = useVuelidate(rules, recovery)
    return {recovery, v$}
  },
  methods: {
    async sentNumber() {
      this.isLoading = true;
      this.v$.token.$model = '';
      this.v$.token.$reset();
      const new_data = {
        phone: this.recovery.phone
      }
      const encoded = await encrypt(JSON.stringify(new_data));
      const {status, data} = await service.send_number(encoded);
      if (status === 200) {
        this.$toast.add({
          severity: 'success',
          summary: '¡Éxito!',
          detail: 'El código se envio exitosamente',
          life: 3000
        });
        this.validCode();
      } else {
        let message = 'Ucurrió un error al enviar el código'
        switch (data.text) {
          case 'Phone not found':
            message = 'El número no fue encontrado';
            break;
          case 'Invalid phone number':
            message = 'Número de teléfono no encontrado';
            break;
          case 'Code not sent':
            message = 'El código no fue enviado';
            break;
          case 'User locked':
            message = 'Usuario bloqueado, contacte a el administrador';
            break;
          case 'User not found':
            message = 'Usuario no encontrado';
            break;
        }
        this.$toast.add({
          severity: 'error',
          summary: '¡Ups!',
          detail: message,
          life: 3000
        });
      }
      this.isLoading = false;
    },

    validCode() {
      this.writeCode = true;
      this.writeNumber = false;
      this.writePass = false;

    },

    writePassword() {
      this.writeCode = false;
      this.writeNumber = false;
      this.writePass = true;

    },
    async sentNewPass() {
      this.isLoading = true;
      const new_data = {
        phone: this.recovery.phone,
        password: this.recovery.password,
        token: this.recovery.token,
      }
      const encoded = await encrypt(JSON.stringify(new_data));
      const {status, data} = await service.new_pass(encoded);
      if (status === 200 || status === 201) {
        this.$toast.add({
          severity: 'success',
          summary: '¡Éxito!',
          detail: 'Contraseña restablecida con éxito',
          life: 3000
        });
        localStorage.removeItem('token')
        await this.$router.push({name: 'login'});
      } else {
        let message = 'Ucurrió un error al reestablecer la contraseña'
        switch (data.text) {
          case 'Expired code':
            message = 'Su código ha expirado';
            break;
          case 'The code has not matched':
            message = 'El código es incorrecto';
            break;
          case 'User locked':
            message = 'Usuario bloqueado, contacte a el administrador';
            break;
          case 'User not found':
            message = 'Usuario no encontrado';
            break;
        }
        this.$toast.add({
          severity: 'error',
          summary: '¡Cuidado!',
          detail: message,
          life: 3000
        });
      }
      this.isLoading = false;
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

p {
  font-size: 12pt !important;
}

.trans {
  opacity: 0;
  width: 0;
  height: 0;
}

.trans-up {
  opacity: 0;
  width: 100%;
  height: 100%;
  animation-name: transition-left;
  animation-duration: 1s;
  animation-fill-mode: forwards;
}

@keyframes transition-left {
  from {
    opacity: 0;
    width: 100%;
    height: 100%;
    transform: translateX(100px);
  }
  to {
    opacity: 1;
    width: 100%;
    height: 100%;
    transform: translateX(0);
  }
}
</style>