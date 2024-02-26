<template>
  <div>
    <h1 style="padding: auto;">Perfil</h1>
    <div class="container">
      <div class="card">
        <p>Nombre</p>
        <p class="p-input">Noe</p>
        <p>Apellido Paterno</p>
        <p class="p-input">Merida</p>
        <p>Apellido Materno</p>
        <p class="p-input">Puente</p>
        <p>Correo Electronico</p>
        <p class="p-input">20213tn121@utez.edu.mx</p>
        <b-button block href="/login" class="btn" variant="primary">Cerrar sesión</b-button>
      </div>

    </div>

  </div>
</template>

<script>
export default {

}
</script>

<style>
.container {
  max-width: 1100px;
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(90vh - 60px);
}

.card {
  max-width: 1100px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 150vh;
}

.p-input {
  border: 0.5px solid black;
  border-radius: 4px;
  border-color: black;
  width: 500px;
  font-weight: normal;
  text-align: left;
  font-size: large;
}

p {
  font-weight: bolder;
  font-size: large;
  
}

.btn {
  width: 500px;
  margin-top: 30px;
}
</style>