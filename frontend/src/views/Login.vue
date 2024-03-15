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
                <i class="pi pi-user"></i>
                <InputText id="username" type="text" v-model="username" />
                <label for="username">Username</label>
              </span>
            </div>
          </b-col>
        </b-row>
        <b-col class="mt-4 mb-2" lg="12">
          <div class="field">
            <span class="p-float-label p-input-icon-right">
              <i class="pi" :class="inputType == 'text' ? 'pi-eye-slash' : 'pi-eye'" @click="setTypeInput(inputType)"></i>
              <InputText :type="inputType" v-model="password"></InputText>
              <label for="password">Password</label>
            </span>
          </div>
        </b-col>
          <Button class="p-button-rounded" label="Inicar Sesion" />
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
      password: null,
      inputType: 'password',
      username: null
    }
  },
  methods: {
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
    background-color: #2a715a;
  }


  img {
    max-width: 100%;
    max-height: 350px;
    width: auto;
    height: auto;
  }

  .cards {
    width: 800px;
    height: 750px;
    border: 1px solid #ccc;
    border-radius: 10px 10px !important;
    overflow: hidden;
  }
  .pi{
    cursor: pointer;
  }

  h1 {
    font-weight: bolder !important;
    font-family: Arial, Helvetica, sans-serif !important;
  }

  .p-button {
    width: 50%;
    margin-top: 20px !important;
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