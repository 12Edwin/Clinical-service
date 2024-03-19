<template>
  <div class="login">
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
                <InputText id="username" type="text" v-model="credentials.username"/>
                <label for="username">Username</label>
              </span>
            </div>
          </b-col>
        </b-row>

        <b-col class="mt-4 mb-2" lg="12">
          <div>
            <span class="p-float-label p-input-icon-right">
              <InputText type="password" v-model="credentials.password"></InputText>
              <label for="password">Password</label>
            </span>
          </div>
        </b-col>
        <div>
          <Button class="p-button-rounded" label="Inicar Sesion" @click="login(credentials)"/>
        </div>
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
import jwtDecode from 'jwt-decode';
export default {
  name: 'login',
  components: {
    Button,
    InputText,
    Password,
    Card,
    Carousel
  },
  data() {
    return {
      isLoading: true,
      credentials: {
        username: '',
        password: ''
      }
    }
  },

  methods: {
    async login(credentials){
      const {data, status} = await services.login(credentials)
      if(status === 200 || status === 201){
        console.log("data =>",data)
        console.log("token =>",data.token)
/*         localStorage.setItem('token', data.token)
        const token = jwtDecode(data.token)
        localStorage.setItem('role', JSON.stringify(token.role.name))
        localStorage.setItem('privileges', JSON.stringify(token.role.privileges))
        console.log("token =>",token) */

      }
    }
  }

}
</script>
<style scoped>
.login {
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
  height: 85%;
  border: 1px solid #ccc;
  border-radius: 10px 10px !important;
  overflow: auto;
}

h1 {
  font-weight: bolder !important;
  font-family: Arial, Helvetica, sans-serif !important;
}

.p-button {
  width: 50%;
  margin-top: 50px !important;
  background-color: #2383f0 !important;
  border: 0px solid !important;
}

.p-inputtext {
  width: 380px;
}

Button {
  width: 380px;
}

@media(max-width: 768px){
  .p-button{
    width: 100%;
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