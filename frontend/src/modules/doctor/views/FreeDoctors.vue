<template>
  <div>
    <h1>Nuestros Doctores</h1>
    <div class="row mt-5 w-100" style="justify-content: center;">
      <div class="col-sm-6" style="padding: 5px;">
        <b-form-input v-model="searchQuery" placeholder="Buscar por nombre o especialidad"/>
      </div>
    </div>
    <div class="w-100 d-flex justify-content-center" style="min-height: 50vh">
      <div class="position-relative mt-3 px-5 w-75">
        <transition-group name="fade">
          <Loader v-if="isLoading" key="loader"/>
          <div v-else key="content" class="doctor-list">
            <div v-for="doctor in filteredDoctors" :key="doctor.id" class="col-md-4">
              <div class="doctor-card">
                <b-card :title="doctor.specialty"
                        img-alt="doctor.name"
                        img-src="https://www.shutterstock.com/image-photo/healthcare-medical-staff-concept-portrait-600nw-2281024823.jpg"
                        img-top style="background-color: ghostwhite;">
                  <p class="mb-0">{{ doctor.name }} {{ doctor.lastname }} {{ doctor.surname }}</p>
                  <p class="mb-0">{{ doctor.speciality }} </p>
                </b-card>
              </div>
            </div>
          </div>
        </transition-group>
      </div>
    </div>
    <div class="text-center py-2 text-white" style="background-color: #2a715a;">
      <p>© Hospital San J 2024. Todos los derechos reservados.</p>
    </div>
  </div>
</template>

<script>
import Card from 'primevue/card';
import Menubar from 'primevue/menubar';
import doctorService from '../services/doctor-service';
import {decrypt} from '@/config/security';
import Loader from "@/components/loader.vue";
import {onError} from "@/kernel/alerts";

export default {
  components: {
    Loader,
    Button: () => import('primevue/button'),
    Menubar,
    Card,

  },
  data() {
    return {
      searchQuery: '',
      doctors: [],
      filteredDoctors: [],
      pageable: {
        page: 0,
        size: 10
      },
      isLoading: true,
      totalRecords: 0,
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
    };
  },


  watch: {
    searchQuery(value) {
      this.filteredDoctors = this.doctors.filter(doctor =>
          doctor.name.toLowerCase().includes(value.toLowerCase()) ||
          doctor.speciality.toLowerCase().includes(value.toLowerCase())
      )
    }
  },
  methods: {
    async pagination(event) {
      if (event !== undefined) {
        const {page, rows} = event;
        this.pageable.page = page;
        this.pageable.size = rows;
        this.rowsPerPage = rows;
      }
      try {
        this.isLoading = true
        const {status, data: {result}} = await doctorService.get_doctors(this.pageable)
        if (status === 200 || status === 201) {
          const decripted = await decrypt(result)
          const {content, totalElements} = JSON.parse(decripted)
          this.totalRecords = totalElements
          this.doctors = content
          this.filteredDoctors = content
        }else {
          await onError('Ocurrió un error', 'Error al cargar los doctores')
        }
      } catch (error) {
      }
      this.isLoading = false
    },
  },
  mounted() {
    this.pagination()
  }
};
</script>

<style scoped>
.doctor-card {
  opacity: 1;
  transition: opacity 0.5s, transform 0.5s;
  margin-bottom: 20px;
  justify-content: center !important;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}

.fade-enter,
.fade-leave-to {
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

.nav-content{
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>