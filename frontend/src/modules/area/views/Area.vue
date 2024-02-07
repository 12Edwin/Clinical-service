<template>
  <div>
    <div class="container mt-5">
      <div class="row equal-height-cards">
        <div class="col-md-4" v-for="(area, index) in areas" :key="index">
          <div class="card text-center">
            <div class="card-img-wrapper">
              <img :src="area.image" class="card-img-top" alt="Imagen area">
            </div>
            <div class="card-body">
              <h5 class="card-title">{{ area.title }}</h5>
              <p class="card-text">{{ area.description }}</p>
            </div>
            <div class="card-footer">
              <a href="#" class="btn btn-success btn-alejar">Ingresar</a>
              <a href="#" class="btn btn-success btn-alejar">Información</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      areas: [
        {
          title: 'EMERGENCIAS',
          description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna ',
          image: 'https://imgs.search.brave.com/t_YNe6V4A1aMZHazQuQagdqV1wBbQDSMjqL3x6xTeno/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/ZXVyb2lubm92YS5l/ZHUuZXMvaW1nL3N1/YmlkYXNFZGl0b3Iv/ZGlzZSVDMyVCMW8l/MjBzaW4lMjB0JUMz/JUFEdHVsbyUyMC0l/MjAyMDIyLTA1LTI1/dDEzMDA1NS0xNjUz/NDc2NDcyLjk4MQ',
        },
        {
          title: 'QUIROFANO',
          description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna ',
          image: 'https://cdn.aarp.net/content/aarpe/es/home/salud/medicare-y-medicaid/info-2021/derecho-de-pacientes-apelar-estado-de-observacion/_jcr_content/root/container_main/container_body_main/container_body1/container_body_cf/container_image/articlecontentfragment/cfimage.coreimg.50.932.jpeg/content/dam/aarp/health/conditions_treatments/2021/03/1140-medicare-observation-rule-esp.jpg',
        },
        {
          title: 'HOSPITALIZACION',
          description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna ',
          image: 'https://imgs.search.brave.com/t_YNe6V4A1aMZHazQuQagdqV1wBbQDSMjqL3x6xTeno/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/ZXVyb2lubm92YS5l/ZHUuZXMvaW1nL3N1/YmlkYXNFZGl0b3Iv/ZGlzZSVDMyVCMW8l/MjBzaW4lMjB0JUMz/JUFEdHVsbyUyMC0l/MjAyMDIyLTA1LTI1/dDEzMDA1NS0xNjUz/NDc2NDcyLjk4MQ',
        },
        {
          title: 'LABORATORIO',
          description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna ',
          image: 'https://imgs.search.brave.com/t_YNe6V4A1aMZHazQuQagdqV1wBbQDSMjqL3x6xTeno/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/ZXVyb2lubm92YS5l/ZHUuZXMvaW1nL3N1/YmlkYXNFZGl0b3Iv/ZGlzZSVDMyVCMW8l/MjBzaW4lMjB0JUMz/JUFEdHVsbyUyMC0l/MjAyMDIyLTA1LTI1/dDEzMDA1NS0xNjUz/NDc2NDcyLjk4MQ',
        },
        {
          title: 'REHABILITAZIÓN',
          description: 'lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna ',
          image: 'https://imgs.search.brave.com/t_YNe6V4A1aMZHazQuQagdqV1wBbQDSMjqL3x6xTeno/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/ZXVyb2lubm92YS5l/ZHUuZXMvaW1nL3N1/YmlkYXNFZGl0b3Iv/ZGlzZSVDMyVCMW8l/MjBzaW4lMjB0JUMz/JUFEdHVsbyUyMC0l/MjAyMDIyLTA1LTI1/dDEzMDA1NS0xNjUz/NDc2NDcyLjk4MQ',
        },
      ]
    };
  }
};
</script>

<style>
.card {
  height: 450px !important;
  justify-content: center;
  align-items: center;
  flex: 1;
  border-color: white !important;
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2) !important;
  padding: 2px;
  margin-top: 5px;
}

.card-img-wrapper {
  height: 190px;
  overflow: hidden;
}

.card-img-top {
  width: 100%;
  height: auto;
}

.card-footer {
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  margin: 0 auto;
}

.btn-alejar {
  margin: 0 10px;
}
</style>
