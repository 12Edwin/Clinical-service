<template>
    <div style="background-color: white;">
      <Menubar :model="navbarItems"
      style="background-color: white; justify-content: center; border: none; position: fixed; top: 0; width: 100%; z-index: 1000; font-weight: bold;" />
      <h1 class="mt-3">Nuestros Doctores</h1>
      <div class="row mt-5" style="justify-content: center;">
        <div class="col-sm-6" style="padding: 5px;">
          <b-form-input v-model="searchQuery" placeholder="Buscar por nombre o especialidad" />
        </div>
      </div>
      <div class="doctor-list mt-3">
        <transition-group name="fade" tag="div" class="row">
          <div v-for="doctor in filteredDoctors" :key="doctor.id" class="col-md-4">
            <div class="doctor-card">
              <b-card :title="doctor.specialty" :img-src="doctor.image" img-alt="doctor.name" img-top style="background-color: ghostwhite;">
                <p class="mb-0">{{ doctor.name }}</p>
                <!-- <p class="mb-0">Zona;</p>
                <p class="mb-0">{{ doctor.location }}</p> -->
              </b-card>
            </div>
          </div>
        </transition-group>
      </div>
      <footer class="bg-white">
      <div class="text-center py-2 text-white" style="background-color: #2a715a;">
        <p>© Hospital San J 2024. Todos los derechos reservados.</p>
      </div>
    </footer>
    </div>
  </template>
  
  <script>
  import Card from 'primevue/card';
  import Menubar from 'primevue/menubar';

  export default {
    components: {
    Button: () => import('primevue/button'),
    Menubar,
    Card,

  },
    data() {
      return {
        searchQuery: '',
        doctors: [
          { id: 1, name: 'Dr. Juan Pérez', specialty: 'Pediatra', location: 'Ciudad de México', image: 'https://www.shutterstock.com/image-photo/healthcare-medical-staff-concept-portrait-600nw-2281024823.jpg' },
          { id: 2, name: 'Dra. María García', specialty: 'Dermatóloga', location: 'Madrid', image: 'https://www.shutterstock.com/image-photo/profile-photo-attractive-family-doc-600nw-1724693776.jpg' },
          { id: 3, name: 'Dr. Roberto López', specialty: 'Cardiólogo', location: 'Buenos Aires', image: 'https://www.shutterstock.com/image-photo/man-portrait-doctor-wearing-white-600nw-2278090533.jpg' },
          { id: 4, name: 'Dr. Juan Pérez', specialty: 'Pediatra', location: 'Ciudad de México', image: 'https://www.shutterstock.com/image-photo/healthcare-medical-staff-concept-portrait-600nw-2281024823.jpg' },
          { id: 5, name: 'Dra. María García', specialty: 'Dermatóloga', location: 'Madrid', image: 'https://www.shutterstock.com/image-photo/profile-photo-attractive-family-doc-600nw-1724693776.jpg' },
          { id: 6, name: 'Dr. Roberto López', specialty: 'Cardiólogo', location: 'Buenos Aires', image: 'https://www.shutterstock.com/image-photo/man-portrait-doctor-wearing-white-600nw-2278090533.jpg' }
        ],
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
          end: true,
        },
      ],
      };
    },
    computed: {
      filteredDoctors() {
        const query = this.searchQuery.toLowerCase();
        return this.doctors.filter(doctor =>
          doctor.name.toLowerCase().includes(query) ||
          doctor.specialty.toLowerCase().includes(query)
        );
      }
    }
  };
  </script>
  
  <style>
  .doctor-card {
    opacity: 1;
    transition: opacity 0.5s, transform 0.5s;
    margin-bottom: 20px;
    justify-content: center !important;
  }
  
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.5s, transform 0.5s;
  }
  
  .fade-enter, .fade-leave-to {
    opacity: 0;
    transform: translateY(30px);
  }
  
  @media (min-width: 768px) {
    .doctor-list {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
    }
  
    .doctor-card {
      flex: 0 0 calc(33.333% - 40px);
      margin: 20px;
    }
  }
  </style>
  