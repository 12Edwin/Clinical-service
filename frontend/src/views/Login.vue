<template>
  <div id="login">

    <Card class="cards mx-5">
      <template #header>
        <div class="d-flex justify-content-center align-items-center">
          <img src="../assets/img/logo.png" alt="">
        </div>
        <h1>Hospital</h1>
      </template>

      <template #content>
        <b-row>
          <form @submit.prevent="submitForm">
            <b-col class="mt-4 mb-2" lg="12">

              <div class="field w-100">

                <span class="p-float-label p-input-icon-right">
                  <i class="pi pi-key"></i>
                  <InputText id="field-username" type="text" v-model="v$.username.$model"
                    :class="{ 'invalid-field-custom': v$.username.$error }" />
                  <label for="field-username" class="form-label-required">Codigo de acceso</label>
                </span>
                <b-row class="justify-content-center">
                  <b-col cols="6">
                    <div class="text-danger text-start pt-2">
                      <p class="error-messages" v-if="v$.username.$dirty && v$.username.required.$invalid">
                        {{ v$.username.required.$message }}
                      </p>
                      <p class="error-messages" v-if="v$.username.$dirty && v$.username.onlyLettersAndAccents.$invalid">
                        {{ v$.username.onlyLettersAndAccents.$message }}
                      </p>
                    </div>
                  </b-col>
                </b-row>
              </div>
            </b-col>
            <b-col class="mt-4 mb-2" lg="12">
              <div class="field">
                <span class="p-float-label p-input-icon-right">
                  <i class="pi" :class="inputType == 'text' ? 'pi-eye-slash' : 'pi-eye'"
                    @click="setTypeInput(inputType)"></i>
                  <InputText id="field-password" :type="inputType" v-model="v$.password.$model"
                    :class="{ 'invalid-field-custom': v$.password.$error }"></InputText>
                  <label for="field-password" class="form-label-required">Contraseña</label>
                </span>
                <b-row class="justify-content-center">
                  <b-col cols="6">
                    <div class="text-danger text-start pt-2">
                      <p class="error-messages" v-if="v$.password.$dirty && v$.password.required.$invalid">
                        {{ v$.password.required.$message }}
                      </p>
                      <p class="error-messages" v-if="v$.password.$dirty && v$.password.text.$invalid">
                        {{ v$.password.text.$message }}
                      </p>
                    </div>
                    <div ref="container" class="frc-captcha" data-sitekey="FCMTFFSTDML9Q0M7" data-lang="es"></div>
                  </b-col>
                </b-row>
              </div>
            </b-col>
          </form>
        </b-row>

        <Button class="p-button-rounded" :disabled="!disbaleButton()" label="Inicar sesion" @click="login(credentials)" />

      </template>
    </Card>

  </div>
</template>

<script>
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';
import Password from 'primevue/password'
import Card from 'primevue/card/Card';
import Carousel from 'primevue/carousel';
import services from "@/modules/auth-services/Auth"
import InlineMessage from 'primevue/inlinemessage';
import { useVuelidate } from '@vuelidate/core';
import { required, helpers } from '@vuelidate/validators';
import { reactive, ref } from '@vue/composition-api'
import { newregex } from "@/utils/regex"
import { WidgetInstance } from 'friendly-challenge'
export default {
  name: 'login',
  components: {
    Button,
    InputText,
    Password,
    Card,
    Carousel,
    InlineMessage
  },
  data() {
    return {
      isLoading: true,
      inputType: 'password',
      loginError: false,
      formData: {
        username: '',
        password: '',
        captchaToken: null,
      },
      
      container: ref(),
      widget: ref()
    }
  },
  setup() {
    const credentials = reactive({
      username: '',
      password: ''
    })

    const rules = {
      username: {
        required: helpers.withMessage("Ingrese codigo de acceso", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
      },
      password: {
        required: helpers.withMessage("Ingrese su contraseña", required),
        text: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
      },
    }
    const v$ = useVuelidate(rules, credentials)
    return { credentials, v$ }
  },

  methods: {
    async login(credentials) {
      const { data, status } = await services.login(credentials)
      if (status === 200 || status === 201) {
        localStorage.setItem('token', data.token)
        this.$router.push({ name: 'doctors' })
      }
    },
     async verifyCaptcha() {
      let {response, status} = await services.captcha()
      if (status === 200 || status === 201) {
        console.log("Respuesta buena:",response);
      }
      console.log("respuesta mala: ",response.data);
    },
    doneCallback() {
      this.verifyCaptcha();
    },
    setTypeInput(t) {
      if (t === 'text') {
        this.inputType = 'password';
        return;
      }
      this.inputType = 'text';
    },
    disbaleButton(){
      if (!this.v$.username.$dirty && !this.v$.password.$dirty) {
        return false;
      }
      return !this.v$.username.$invalid && !this.v$.password.$invalid
    } ,  

    errorCallback: (err) => {
      console.log("There was an error when trying to solve the Captcha.");
      console.log(err);
    },
    submitForm: () => { },

  },
  mounted() {
    if (this.$refs.container) {
      this.widget = new WidgetInstance(
        this.$refs.container, {
        startMode: "",
        doneCallback: this.doneCallback,
        errorCallback: this.errorCallback,
      }
      );
    }
  },
  beforeDestroy() {
    if (this.widget) {
      this.widget.destroy();
    }
  },
}
</script>
<style scoped>
#login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: rgb(92, 171, 140);
  background: linear-gradient(180deg, rgba(92, 171, 140, 1) 0%, rgba(42, 113, 90, 1) 51%, rgba(0, 0, 0, 1) 100%);
  overflow: hidden;
}


img {
  max-width: 100%;
  max-height: 350px;
  width: auto;
  height: auto;
}

.cards {
  width: 800px;
  height: 770px;
  border: 1px solid #ccc;
  border-radius: 10px 10px !important;

}

.pi {
  cursor: pointer;
}

h1 {
  font-weight: bolder !important;
  font-family: Arial, Helvetica, sans-serif !important;
}

.p-inputtext {
  width: 380px;
}

Button {
  width: 50%;
  max-width: 380px;
  margin-top: 20px !important;
  background-color: #2383f0 !important;
  border: 0px solid !important;
}

@media(max-width: 768px) {
  Button {
    width: 50%;
  }
}


@media (max-width: 768px) {
  .p-inputtext {
    width: 100%;
  }

}

.inputs {
  margin-top: 25px;
}

.my-custom-dialog .p-dialog {
  max-width: 90%;
}

@media (max-width: 768px) {
  .my-custom-dialog .p-dialog {
    max-width: 95%;
  }
}

.invalid-field-custom {
  border-color: rgba(255, 0, 0, 1) !important;
  box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
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
</style>