<template>
  <div class="home" style="background-color: white;">
    <!-- navbar -->
    <Menubar :model="navbarItems"
      style="background-color: white; justify-content: center; border: none; position: fixed; top: 0; width: 100%; z-index: 1000; font-weight: bold;" />

    <!-- Carrusel -->
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
                  <img src="https://picsum.photos/600/300/?image=25" class="image-carrusel"
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
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
          magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam adipiscing
          vitae.
          Vitae tempus quam pellentesque nec nam aliquam sem et tortor. In cursus turpis massa tincidunt dui ut. Id
          cursus
          metus aliquam eleifend mi in nulla. Bibendum enim facilisis gravida neque. Id aliquet lectus proin nibh nisl
          condimentum id venenatis. Nibh cras pulvinar mattis nunc sed blandit. Dui faucibus in ornare quam. A erat nam
          at
          lectus urna. Tincidunt vitae semper quis lectus nulla at volutpat. Libero id faucibus nisl tincidunt eget
          nullam
          non nisi. Habitasse platea dictumst vestibulum rhoncus. Egestas egestas fringilla phasellus faucibus
          scelerisque
          eleifend donec pretium vulputate.
        </h6>
      </div>
    </div>


    <div class="container-fluid" style="background-color: #5cab8c;">
      <div class="row">
        <div class="col-md-6">
          <img src="../assets/img/imagen1.jpg" height="450px" alt="imagen-1"
            style="padding: 30px; border-radius: 20px;">
        </div>
        <div class="col-md-6" style="padding: 130px;">
          <h4 class="text-white">Quienes somos</h4>
          <h6 class="text-white">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore
            magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam adipiscing
            vitae.
            Vitae tempus quam pellentesque nec nam aliquam sem et tortor. In cursus turpis massa tincidunt dui ut. Id
            cursus
            metus aliquam eleifend mi in nulla. Bibendum enim facilisis gravida neque.
          </h6>
        </div>
      </div>
    </div>

    <div class="container">
      <div class="row">
        <h4>Artículos</h4>
        <div class="col-lg-4 col-md-6 col-sm-12 mt-2" v-for="(item, index) in cardData" :key="index">
          <Card style="height: 400px;">
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
  <div class="col-md-4">
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
  <div class="col-md-4">
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
  <div class="col-md-4">
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
  computed: {
    // numVisible() {
    //   return this.responsiveOptions[0].numVisible;
    // },
    // numScroll() {
    //   return this.responsiveOptions[0].numScroll;
    // }
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
          label: 'Conócenos',
        },
        {
          label: 'Blog',
        },
        {
          label: 'Especialidades',
        },
        {
          label: 'Servicios',
          to: '/publicServices'
        },
        {
          label: 'Nuestros Doctores',
          to: "/our_doctors"
        },
        {
          label: "Directorio",
        },
        {
          label: '',
          icon: 'pi pi-fw pi-user',
          to: "/login",
          end: true // Esto alinea este elemento a la derecha
        },
      ],

      notices: [
        {
          title: 'Conoscanos',
          description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam adipiscing vitae.",
          image: 'assets/img/imagen1.jpg'
        },
        {
          title: 'Revisiones',
          description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam adipiscing vitae.",
          image: 'assets/img/imagen2.jpg'
        },
        {
          title: 'Hospital 3',
          description: "",
          image: 'assets/img/imagen3.jpg'
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
          description: 'Este es un ejemplo de texto para la tarjeta. Puedes agregar información adicional aquí.',
          image: 'assets/img/imagen3.jpg',
          link: '/terapia-intensiva'
        },
        {
          title: '¿Qué es el glaucoma?',
          description: 'Otra tarjeta con un poco de texto adicional. ¡Personalízala según tus necesidades!',
          image: 'assets/img/imagen4.jpg',
          link: '/glaucoma'
        },
        {
          title: 'Alimentos que mejoran tu piel.',
          description: 'Una tercera tarjeta con contenido relevante. ¡Ajusta según tus preferencias!',
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

</style>
