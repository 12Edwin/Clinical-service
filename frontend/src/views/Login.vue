<template>
  <div id="login">
    <Card class="cards">
      <template #header>
        <div class="d-flex justify-content-center align-items-center">
          <img src="../assets/img/logo.png" alt="">
        </div>
        <h1>Hospital</h1>
      </template>
      <template #content>
        <b-row>
          <b-col class="mt-4 mb-2" lg="12">
            <div class="field w-100">
              <span class="p-float-label p-input-icon-right">
                <i class="pi pi-key"></i>
                <InputText id="username" type="text" v-model="credentials.username" />
                <label for="username">Codigo de Acceso</label>
              </span>
            </div>
          </b-col>
        </b-row>
        <b-col class="mt-4 mb-2" lg="12">
          <div class="field">
            <span class="p-float-label p-input-icon-right">
              <i class="pi" :class="inputType == 'text' ? 'pi-eye-slash' : 'pi-eye'" @click="setTypeInput(inputType)"></i>
              <InputText :type="inputType" v-model="credentials.password"></InputText>
              <label for="password">Contraseña</label>
            </span>
          </div>
          <b-row>
        </b-row>
        </b-col>
          <Button class="p-button-rounded" label="Inicar sesion" @click="login(credentials)"/>
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
      credentials: {
        username: '',
        password: ''
      },
      inputType: 'password',
      loginError: false
    }
  },

  methods: {
    async login(credentials){
      const {data, status} = await services.login(credentials)
      if(status === 200 || status === 201){
        localStorage.setItem('token', data.token)
        this.$router.push({name: 'doctors'})
      }
    },
    setTypeInput(t) {
      if(t === 'text') {
        this.inputType = 'password';
        return;
      }
      this.inputType = 'text';
    },
  }

}
</script>
<style scoped>
#login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}


img {
  max-width: 100%;
  max-height: 350px;
  width: auto;
  height: auto;
}

.cards {
  width: 800px;
  height: 700px;
  border: 1px solid #ccc;
  border-radius: 10px 10px !important;

}
.pi{
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

@media(max-width: 768px){
  Button{
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
</style>
</style>