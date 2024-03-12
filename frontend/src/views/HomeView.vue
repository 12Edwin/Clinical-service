<template>
  <div class="home">
     <!-- navbar -->
  <nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <div class="container">
      <a class="navbar-brand" href="#">Hospital San Edwin</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
        aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <ul class="navbar-nav">
          <li class="nav-item active">
            <a class="nav-link" href="#">Inicio</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Promociones</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Paquetes</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Especialidades</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/services">Servicios</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Aseguradoras</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Directorio</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Blog</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Contacto</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  
    <!-- Carrusel -->
  <div class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="../assets/img/imagen1.jpg" class="d-block" height="700px" width="1500px" alt="Imagen 1">
        <div class="carousel-caption d-none d-md-block">
          <h5>Atención de primer nivel</h5>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam
            adipiscing vitae.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="../assets/img/imagen2.jpg" class="d-block" height="700px" width="1500px" alt="Imagen 2">
        <div class="carousel-caption d-none d-md-block">
          <h5>Personal calificado</h5>
          <p>Vitae tempus quam pellentesque nec nam aliquam sem et tortor. </p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="../assets/img/imagen3.jpg" class="d-block" height="700px" width="1500px" alt="Imagen 3">
        <div class="carousel-caption d-none d-md-block">
          <h5>Contamos con todos lo necesario</h5>
          <p>In cursus turpis massa tincidunt dui ut. Id cursus metus aliquam eleifend mi in nulla. Bibendum enim
            facilisis gravida neque. Id aliquet lectus proin nibh nisl condimentum id venenatis. 3</p>
        </div>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Anterior</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Siguiente</span>
    </button>
  </div>

  <div class="container mt-5">
    <div class="row text-center">
      <h3 class="animate__animated animate__fadeIn">Bienvenido a nuestro Hospital</h3>
      <p class="animate__animated animate__fadeIn">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam adipiscing vitae.
        Vitae tempus quam pellentesque nec nam aliquam sem et tortor. In cursus turpis massa tincidunt dui ut. Id cursus
        metus aliquam eleifend mi in nulla. Bibendum enim facilisis gravida neque. Id aliquet lectus proin nibh nisl
        condimentum id venenatis. Nibh cras pulvinar mattis nunc sed blandit. Dui faucibus in ornare quam. A erat nam at
        lectus urna. Tincidunt vitae semper quis lectus nulla at volutpat. Libero id faucibus nisl tincidunt eget nullam
        non nisi. Habitasse platea dictumst vestibulum rhoncus. Egestas egestas fringilla phasellus faucibus scelerisque
        eleifend donec pretium vulputate.
      </p>
    </div>
  </div>

  <div class="container-fluid mt-5" style="background-color: #204570;">
    <div class="row">
      <div class="col-md-6">
        <img src="../assets/img/imagen1.jpg" height="450px" alt="imagen-1" style="padding: 30px; border-radius: 20px;">
      </div>
      <div class="col-md-6" style="padding: 130px;">
        <h4 class="text-white">Quienes somos</h4>
        <p class="text-white animate__animated animate__fadeIn">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ac ut consequat semper viverra nam libero justo laoreet sit. Quis eleifend quam adipiscing vitae.
        Vitae tempus quam pellentesque nec nam aliquam sem et tortor. In cursus turpis massa tincidunt dui ut. Id cursus
        metus aliquam eleifend mi in nulla. Bibendum enim facilisis gravida neque. 
      </p>
      </div>
    </div>
  </div>

  <div class="container mt-5">
    <div class="row mt-5 text-center">
      <h3>Nuestros artículos</h3>
    </div>
  </div>

  <div>
    <div class="container mt-5">
      <div class="row equal-height-cards">
        <div class="col-md-4">
          <div class="card text-center">
            <img src="../assets/img/imagen3.jpg" class="card-img-top" alt="Imagen de ejemplo">
            <div class="card-body">
              <h5 class="card-title">¿Cómo funciona la Terapia Intensiva?</h5>
              <p class="card-text">Este es un ejemplo de texto para la tarjeta. Puedes agregar información adicional
                aquí.
              </p>
              <a href="#" class="btn btn-primary">Leer más</a>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card text-center">
            <img src="../assets/img/imagen4.jpg" class="card-img-top" alt="Imagen de ejemplo">
            <div class="card-body">
              <h5 class="card-title">¿Qué es el glaucoma?</h5>
              <p class="card-text">Otra tarjeta con un poco de texto adicional. ¡Personalízala según tus necesidades!
              </p>
              <a href="#" class="btn btn-primary">Leer más</a>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card text-center">
            <img src="../assets/img/imagen2.jpg" class="card-img-top" alt="Imagen de ejemplo">
            <div class="card-body">
              <h5 class="card-title">Alimentos que mejoran tu piel.</h5>
              <p class="card-text">Una tercera tarjeta con contenido relevante. ¡Ajusta según tus preferencias!</p>
              <a href="#" class="btn btn-primary">Leer más</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <footer class="bg-white">
    <div class="py-4">
      <div class="row text-center text-white" style="background-color: #204570;">



        <!-- Parte turquesa del footer con servicios e información adicional -->
        <div class="col-md-12">
          <div class="row">
            <!-- Parte blanca del footer con dirección -->
            <div class="col-md-12 mt-3">
              <p>Dirección: Avenida Univ. Tec. 1, Emiliano Zapata, México</p>
            </div>
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

            <div class="col-md-3"></div>

          </div>
        </div>
      </div>
    </div>

    <div class="linea"></div>

    <div class="text-center py-2 text-white" style="background-color: #405de0;">
      <p>© Hospital San Edwin 2024. Todos los derechos reservados.</p>
    </div>
  </footer>

  </div>
</template>

<script>
export default {
  name: 'HomeView',
  components: {
    Button: ()=> import ('primevue/button')
  },
  data(){
    return{
      isLoading: true
    }
  }
}
</script>

<style>
ul {
  list-style-type: none;
}

.linea {
  border-top: 1px solid #fff;
  margin-top: 15px;
}

.card {
  height: 590px !important;
  justify-content: center;
  align-items: center;
  flex: 1;
  border-color: white !important;
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2) !important;
}
</style>