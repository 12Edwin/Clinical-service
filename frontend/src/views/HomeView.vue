<template>
  <div class="w-100">
    <div class="w-100" style="display: flex; align-items: center; justify-content: center;">
      <div>
        <Carousel :numScroll="1" :numVisible="1" :responsiveOptions="responsiveOptions" :value="notices"
                  style="margin-top: 20px;">
          <template #header>
          </template>
          <template #item="slotProps">
            <div class="car-item">
              <div class="car-content">
                <div>
                  <img :alt="slotProps.data.title" :src="require(`@/${slotProps.data.image}`)"
                       class="image-carrusel"/>
                </div>
                <div class="mt-3" style="padding-left: 10%; padding-right: 10%;">
                  <h4 class="car-title">{{ slotProps.data.title }}</h4>
                  <div class="car-subtitle">{{ slotProps.data.description }}</div>
                </div>
              </div>
            </div>
          </template>
        </Carousel>
      </div>
    </div>

      <div class="w-100 d-flex justify-content-center mb-5 mt-5">
        <div class="w-75">
          <h3 class="welcome-heading" style="color:black">Bienvenido a nuestro Hospital</h3>
          <h6 class="welcome-text">
            Bienvenido a nuestro Hospital San J, donde la salud y el
            bienestar de nuestros pacientes son nuestra máxima prioridad.
            Nos enorgullece ofrecer una atención médica de alta calidad,
            personalizada y centrada en el paciente.
            Nuestro equipo de profesionales de la salud está dedicado a
            mejorar y mantener su salud a través de la prevención, el
            diagnóstico temprano y el tratamiento de diversas condiciones
            médicas. Con una combinación de tecnología médica avanzada,
            instalaciones modernas y personal altamente capacitado, nos
            esforzamos por proporcionar una experiencia de atención médica
            excepcional. Gracias por elegirnos como su socio de atención médica.
          </h6>
        </div>
      </div>



      <b-row class="w-100 justify-content-center align-items-center" style="background-color: #5cab8c">
        <div class="col-6">
          <img alt="imagen-1" height="450px" src="../assets/img/imagen1.jpg"
               style="padding: 10px; border-radius: 20px;">
        </div>
        <div class="col-6">
          <h4 class="text-white">Quienes somos</h4>
          <h6 class="text-white" style=" text-align: justify !important;">
            Somos el Hospital San J, una institución de salud comprometida
            con brindar atención médica de alta calidad a nuestra comunidad.
            Contamos con un equipo de profesionales altamente capacitados y
            dedicados a ofrecer un cuidado compasivo y personalizado.
            Nuestro hospital está equipado con tecnología médica de
            vanguardia para garantizar que nuestros pacientes reciban el
            mejor tratamiento posible. Creemos en la importancia de la
            atención preventiva y trabajamos arduamente para educar a
            nuestros pacientes sobre la importancia de mantener un estilo
            de vida saludable. En el Hospital San J, nos esforzamos por
            superar las expectativas de nuestros pacientes y proporcionar
            una atención excepcional en todo momento.
          </h6>
        </div>
      </b-row>

    <div class="w-100 px-4 mt-5">
      <h4>Artículos</h4>
      <b-row>
        <div v-for="(item, index) in cardData" :key="index" class="col-lg-4 col-md-6 col-sm-12 mt-2">
          <Card style="min-height: 500px;">
            <template #header>
              <img :alt="'Imagen ' + (index + 1) + ' de ejemplo'" :src="require(`@/${item.image}`)"
                   class="card-img-top">
            </template>
            <template #title>
              <h4 class="">{{ item.title }}</h4>
            </template>
            <template #content>
              <h6 class="">{{ item.description }}</h6>
            </template>
            <template #footer>

            </template>
          </Card>
        </div>
      </b-row>
    </div>

      <div class="py-4 w-100 mt-4">
        <div class="pb-5 pt-3 px-4 w-100 text-white justify-content-center" style="background-color: #5cab8c;">

          <h5 class="mb-5 w-auto">Dirección: Avenida Univ. Tec. 1, Emiliano Zapata, México</h5>

          <div class="d-flex w-100 justify-content-center p-0">
            <div class="col-md-4 m-0 text-center">
              <ul class="m-0">
                <h5>Servicios</h5>
                <li>Urgencias 24 Horas</li>
                <li>Centro de Especialidades Médicas</li>
                <li>Hospitalización</li>
                <li>Laboratorio e Imagenología</li>
                <li>Farmacia</li>
                <li>Cafetería</li>
                <li>Estacionamiento</li>
              </ul>
            </div>
            <div class="col-md-4 text-center">
              <ul class="m-0">
                <h5>Instalaciones</h5>
                <li>Urgencias</li>
                <li>Habitaciones</li>
                <li>Laboratorio</li>
                <li>Farmacia</li>
                <li>Cafetería</li>
                <li>Estacionamiento</li>
              </ul>
            </div>
            <div class="col-md-4 text-center">
              <ul class="m-0">
                <h5>Mapa del Sitio</h5>
                <li>Inicio</li>
                <li>Nosotros</li>
                <li>Servicios</li>
                <li>Paquetes y Promociones</li>
                <li>Contacto</li>
              </ul>
            </div>
          </div>

        </div>
      </div>

      <div class="text-center py-2 text-white" style="background-color: #2a715a;">
        <p>© Hospital San J 2024. Todos los derechos reservados.</p>
      </div>

  </div>
</template>

<script>
import Menubar from 'primevue/menubar';
import Carousel from 'primevue/carousel';
import CarouselItem from 'primevue/carousel/Carousel';
import Card from 'primevue/card';


export default {
  name: 'HomeView',
  components: {
    Button: () => import('primevue/button'),
    Menubar,
    Carousel,
    CarouselItem,
    Card,

  },

  data() {
    return {
      isLoading: true,

      notices: [
        {
          title: 'Conócenos',
          description: "Somos el Hospital San J, una institución de salud comprometida con brindar atención médica de alta calidad a nuestra comunidad. Contamos con un equipo de profesionales altamente capacitados y dedicados a ofrecer un cuidado compasivo y personalizado. Nuestro hospital está equipado con tecnología médica de vanguardia para garantizar que nuestros pacientes reciban el mejor tratamiento posible. Creemos en la importancia de la atención preventiva y trabajamos arduamente para educar a nuestros pacientes sobre la importancia de mantener un estilo de vida saludable. En el Hospital San J, nos esforzamos por superar las expectativas de nuestros pacientes y proporcionar una atención excepcional en todo momento.",
          image: 'assets/img/home2.jpg'
        },
        {
          title: 'Revisiones',
          description: "En Hospital San J, valoramos la opinión de nuestros pacientes. Nuestro compromiso con la excelencia nos lleva a buscar constantemente formas de mejorar nuestros servicios y atención al paciente. Las revisiones y testimonios de nuestros pacientes son una parte integral de este proceso. Nos enorgullece compartir algunas de las experiencias de nuestros pacientes y cómo nuestro equipo de atención médica ha impactado positivamente en sus vidas. Si eres un paciente actual o anterior, te invitamos a compartir tu experiencia con nosotros.",
          image: 'assets/img/home1.jpg'
        },
        {
          title: 'Acuerdos',
          description: "En el Hospital San J, creemos en la importancia de establecer acuerdos y colaboraciones con otras instituciones de salud y organizaciones para mejorar la atención que brindamos a nuestros pacientes. Estos acuerdos nos permiten compartir conocimientos, recursos y mejores prácticas, y trabajar juntos para enfrentar los desafíos de la atención médica. Estamos orgullosos de nuestros acuerdos con una variedad de organizaciones y continuamos buscando nuevas oportunidades para colaborar y mejorar la atención que brindamos.",
          image: 'assets/img/home3.jpg'
        },
      ],

      responsiveOptions: [
        {
          breakpoint: '1024px',
          numVisible: 3,
          numScroll: 3
        },
        {
          breakpoint: '600px',
          numVisible: 2,
          numScroll: 2
        },
        {
          breakpoint: '480px',
          numVisible: 1,
          numScroll: 1
        }
      ],
      activeIndex: 0,
      cardData: [
        {
          title: '¿Cómo funciona la Terapia Intensiva?',
          description: "La Terapia Intensiva, también conocida como Cuidados Intensivos, es un área especializada de la medicina que se dedica a la atención de pacientes con condiciones críticas o potencialmente mortales. En esta unidad.",
          image: 'assets/img/imagen3.jpg',
          link: '/terapia-intensiva'
        },
        {
          title: '¿Qué es el glaucoma?',
          description: "El glaucoma es una enfermedad del ojo que puede dañar el nervio óptico. El nervio óptico suministra información visual al cerebro. Si se daña, como ocurre con el glaucoma, la visión se verá afectada y puede resultar en ceguera. Por lo general.",
          image: 'assets/img/imagen4.jpg',
          link: '/glaucoma'
        },
        {
          title: 'Alimentos que mejoran tu piel.',
          description: "La piel es el órgano más grande del cuerpo y su estado de salud puede ser un reflejo de nuestra dieta. Algunos alimentos pueden ayudar a mejorar la salud de nuestra piel, proporcionándole los nutrientes necesarios para su reparación y regeneración.",
          image: 'assets/img/imagen2.jpg',
          link: '/alimentos-piel'
        }
      ],
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll(evt) {
      const welcomeHeading = document.querySelector('.welcome-heading');
      const welcomeText = document.querySelector('.welcome-text');
      const boundingHeading = welcomeHeading.getBoundingClientRect();
      const boundingText = welcomeText.getBoundingClientRect();
      if (boundingHeading.top >= 0 && boundingHeading.bottom <= window.innerHeight ||
          boundingText.top >= 0 && boundingText.bottom <= window.innerHeight) {
        welcomeHeading.classList.add('show');
        welcomeText.classList.add('show');
      }
    }
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  }
}
</script>

<style scoped>
.image-carrusel {
  overflow: hidden;
  height: 500px;
  width: 90%;
}

@media only screen and (max-width: 768px) {
  .image-carrusel {
    height: 300px;
    width: 98%;
  }
}

.welcome-heading,
.welcome-text {
  opacity: 1;
  color: black;
  transition: opacity 0.5s ease;
}

.card-img-top {
  height: 200px;
}


@media (max-width: 768px) {
  .col-md-4 {
    margin-bottom: 20px;
  }
}

li {
  list-style-type: none;
}
</style>
<style>
.welcome-heading.show,
.welcome-text.show {
  opacity: 1;
}
</style>
