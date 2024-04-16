<template>
  <div class="container">
    <div class="error_container">
      <div class="error_code">
        <p>
          4
        </p>
        <p>
          0
        </p>
        <p>
          3
        </p>
      </div>
      <div class="error_name">
          No estas autorizado
        </div>
        <div class="error_description">
          Lo sentimos, la página que buscas no se encuentra.
        </div>
        <button class="action" @click="()=> $router.back()">Volver</button>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
:root{
  margin: 0;
  padding: 0;
  --white: #f2f5f5;
  --black: #121212;
  --blue: #308bd1;
  --gray: #959695;
}

body{
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  font-family: Monserrat, sans-serif;
  color: var(--white);
  background-color: var(--black);
}

.error_container{
  max-width: 800px;
  display: flex;
  flex-direction: column;
  justify-self: center;
  align-items: center;
  gap: 10px;

}

.error_code{
  width: 100%;
  margin: 0 auto;
  font-size: 6rem;
  font-weight: 800;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;  
}


p:first-of-type{
  animation: fall 1s linear;
  transform: rotateZ(-20deg);
}

p:last-of-type{
  animation: fall 1.8s linear;
  transform: rotateZ(8deg);
}

p:nth-of-type(2){
  color: var(--blue);
  animation: bounce 1.2s 1.8s linear forwards;
  transform: rotateZ(-10deg);
  opacity: 0;
}


.error_name{
  font-size: 1.5rem;
  font-weight: 700;
  padding-block-end: 0 3%;
}

.error_description{
  font-size: 0.9rem;
  text-align: justify;
  line-height: 1.6;
  padding: 0 10%;
  color: var(--gray);
}

.action:hover{
  opacity: 0.8;
}

@media screen and (min-width: 48rem){
  .error_description{
    padding: 0;
  }
}

@keyframes fall {
  0%{
    transform: translateY(-100vh);
    opacity: 0;
  }
  100%{
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes bounce {
  0%,
  40%,
  75%,
  95%{
    transform: translateY(0);
    opacity: 1;
  }
  15%{
    transform: translateY(-25px);
  }
  65%{
    transform: translateY(-15px);
  }
  85%{
    transform: translateY(-5px);
  }
  100%{
    transform: rotateZ(45deg);
    opacity: 1;
  }
}

</style>