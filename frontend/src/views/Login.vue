<template>
  <div id="login">
    <div class="card">
      <h2 class="h2">Hospital San Edwin</h2>
      <form @submit.prevent="login" class="form">
        <b-form-group label="Usuario:" label-for="username">
          <b-form-input id="username" v-model="username" placeholder="Ingresa tu usuario"></b-form-input>
        </b-form-group>
        <b-form-group label="Contraseña:" label-for="password">
          <b-form-input type="password" id="password" v-model="password"
            placeholder="Ingresa tu contraseña"></b-form-input>
        </b-form-group>
        <b-button class="btn" type="submit" variant="primary">Iniciar Sesión</b-button>
      </form>

    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  components: {

  },
  data() {
    return {
      isLoading: true,
    }
  },

}
</script>
<style>
#login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #d2d3d4;
}

.card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  max-width: 500px;
  max-height: 450px;
}

.form-group {
  text-align: start;
  margin-top: 20px;
}

.form {
  margin-bottom: 20px;
  width: 100%;
  padding: 10px;
}

.h2 {
  font-weight: bolder;
}

.btn {
  width: 100%;
  margin-top: 30px;
}
</style>