<template>
  <div class="home" style="background-color: white;">
    <!-- navbar -->
    <Menubar :model="navbarItems"
      style="background-color: white; justify-content: center; border: none; position: fixed; top: 0; width: 100%; z-index: 1000; font-weight: bold;" />

    <div style="display: flex; align-items: center; justify-content: center;">
      <div style=" margin-top: 50px;">
        <Carousel style="margin-top: 20px;" :value="notices" :numVisible="1" :numScroll="1"
          :responsiveOptions="responsiveOptions">
          <template #header>
          </template>
          <template #item="slotProps">
            <div class="car-item">
              <div class="car-content">
                <div>
                  <img :src="require(`@/${slotProps.data.image}`)" class="image-carrusel"
                    :alt="slotProps.data.title" />
                </div>
                <div style="padding-left: 10%; padding-right: 10%;">
                  <h4 class="car-title">{{ slotProps.data.title }}</h4>
                  <div class="car-subtitle">{{ slotProps.data.description }}</div>
                </div>
              </div>
            </div>
          </template>
        </Carousel>
      </div>
    </div>




    <div class="container">
      <div class="row text-center">
        <h3 class="welcome-heading">Bienvenido a nuestro Hospital</h3>
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


    <div class="container-fluid" style="background-color: #5cab8c;">
      <div class="row align-items-center">
        <div class="col-md-6">
          <img src="../assets/img/imagen1.jpg" height="450px" alt="imagen-1"
            style="padding: 30px; border-radius: 20px;">
        </div>
        <div class="col-md-6" style="padding: 130px;">
          <h4 class="text-white">Quienes somos</h4>
          <h6 class="text-white">
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
      </div>
    </div>

    <div class="container">
      <div class="row">
        <h4>Artículos</h4>
        <div class="col-lg-4 col-md-6 col-sm-12 mt-2" v-for="(item, index) in cardData" :key="index">
          <Card style="min-height: 500px;">
            <template #header>
              <img :src="require(`@/${item.image}`)" class="card-img-top" :alt="'Imagen ' + (index + 1) + ' de ejemplo'">
            </template>
            <template #title>
              <h4 class="">{{ item.title }}</h4>
            </template>
            <template #content>
              <h6 class="">{{ item.description }}</h6>
            </template>
            <template #footer>
              <!-- <router-link :to="item.link" class="p-button p-button-primary">Leer más</router-link> -->
            </template>
          </Card>
        </div>
      </div>
    </div>

    <footer class="bg-white">
      <div class="py-4">
        <div class="row text-center text-white" style="background-color: #5cab8c;">
          <!-- Parte turquesa del footer con servicios e información adicional -->
          <div class="col-md-12 mt-3">
  <p class="footer-address">Dirección: Avenida Univ. Tec. 1, Emiliano Zapata, México</p>
</div>
<div class="row">
  <div class="col-md-4 text-center">
    <h5>Servicios</h5>
    <ul>
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
    <h5>Instalaciones</h5>
    <ul>
      <li>Urgencias</li>
      <li>Habitaciones</li>
      <li>Laboratorio</li>
      <li>Farmacia</li>
      <li>Cafetería</li>
      <li>Estacionamiento</li>
    </ul>
  </div>
  <div class="col-md-4 text-center">
    <h5>Mapa del Sitio</h5>
    <ul>
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

      <div class="linea"></div>

      <div class="text-center py-2 text-white" style="background-color: #2a715a;">
        <p>© Hospital San J 2024. Todos los derechos reservados.</p>
      </div>
    </footer>

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
      navbarItems: [
        {
          label: 'Hospital San J',
          to: "/home",
        },
        {
          label: 'Blog',
          to: '/blog'
        },
        {
          label: 'Servicios',
          to: '/services'
        },
        {
          label: 'Nuestros Doctores',
          to: "/our_doctors"
        },
        {
          label: '',
          icon: 'pi pi-fw pi-user',
          to: "/login",
          end: true // this will align the item to the right
        },
      ],

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
    handleScroll() {
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
  width: 76%;
}

@media only screen and (max-width: 768px) {
  .image-carrusel {
    height: 300px;
    width: 98%;
  }
}

.welcome-heading,
.welcome-text {
  opacity: 0;
  transition: opacity 0.5s ease;
}

.welcome-heading.show,
.welcome-text.show {
  opacity: 1;
}

.card-img-top {
  height: 200px;
}

.card-content {
  height: 100%;
}

/* .card-style {
  border-radius: 5%;
  border-width: 1px;
  border-color: #ff0000;
} */

.card-style {
  border-radius: 5px;
  box-shadow: 5px 0 5px -2px rgba(0, 0, 0, 0.2), 0 5px 5px -2px rgba(0, 0, 0, 0.2);
  height: 400px !important;
}

.h6 {
  font-weight: 200;
}
.footer-address {
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .col-md-4 {
    margin-bottom: 20px;
  }
}

li{
  list-style-type: none;
}

</style>
