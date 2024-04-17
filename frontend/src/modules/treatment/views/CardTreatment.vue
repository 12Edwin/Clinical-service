<template>
  <div class="right">
    <div class="d-flex w-100 justify-content-between">
      <h2 style="color: #333; margin-bottom: 20px; text-transform: uppercase; font-size: 24px;">Tratamientos</h2>
      <BButton :disabled="unauthorized" variant="success" pill class="mb-4 mt-0 w-auto px-4" @click="openRegister"><BIcon icon="plus-circle" class="me-2"/> <label> Crear tratamiento </label></BButton>
    </div>
    <transition-group name="fade" type="transition">
      <loader v-if="isLoading" key="load"/>
      <div key="content" v-else>
        <Accordion>
          <Unautorized message="No tienes permisos para ver estos tratamientos" v-if="unauthorized"/>
          <NotFound message="No se encontraron tratamientos" v-if="treatments.length === 0 && !unauthorized"/>
          <AccordionTab v-for="(treatment, index) in treatments" class="mb-4" :key="index">
            <template v-slot:header>
              <div class="d-flex justify-content-between w-100">
                <label for="service"> <b> Servicio: </b> <span v-tooltip="treatment.service.name">
                  {{ treatment.service.name.length > 30 ? treatment.service.name.substring(0, 30) + '...' : treatment.service.name }}
                </span></label>
                <Button class="p-button-rounded p-button-outlined px-3" @click="openUpdate(treatment)">
                  <BIcon icon="pencil-fill"/>
                </Button>
              </div>
            </template>
            <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
                 data-bs-parent="#accordionExample">
              <div class="accordion-body">
                <div class="row mb-3">
                  <div class="col-sm-6 mb-2">
                    <label for="recommendation" class="form-label">Recomendación:</label>
                    <input type="text" id="recommendation" class="form-control" v-model="treatment.recommendation"
                           readonly>
                  </div>
                  <div class="col-sm-6 mb-2">
                    <label for="price" class="form-label">Precio:</label>
                    <input type="text" id="price" class="form-control" v-model="treatment.service.price" readonly>
                  </div>
                  <div class="col-sm-6 mb-2">
                    <label for="speciality" class="form-label">Especialidad:</label>
                    <input type="text" id="speciality" class="form-control" v-model="treatment.service.speciality.name"
                           readonly>
                  </div>
                  <div class="col-sm-6 mb-2">
                    <label for="studies_description" class="form-label">Descripción de Estudios:</label>
                    <input type="text" id="studies_description" class="form-control"
                           v-model="treatment.studies_description" readonly>
                  </div>
                  <div class="col-sm-6 mb-2">
                    <label for="support_home" class="form-label">Soporte en Casa:</label>
                    <input type="text" id="support_home" class="form-control" v-model="treatment.support_home" readonly>
                  </div>
                  <div class="col-sm-6 mb-2 d-flex align-items-end">
                    <button class="w-100 btn btn-success rounded justify-content-center" @click="toCreateAppoint(treatment.id)" >Crear Cita</button>
                  </div>
                  <div class="col">
                    <label for="appoints"> <b> Citas Terminadas: </b>
                      <span class=""> {{ calcTotalCompletedAppoints }} citas completadas / {{ calcTotalAppoints }} totales </span>
                    </label>
                    <ProgressBar style="border-radius: 15px" :value="calcProgress" :aria-valuemin="0" :aria-valuemax="100"/>
                  </div>
                </div>
              </div>
            </div>
          </AccordionTab>
        </Accordion>
        <ModalTreatment :oldTreatment="selectedTreatment" :expedient="expedient" :title="titleModal" :visible="visible" @close="closeModal"/>
      </div>
    </transition-group>
  </div>
</template>

<script>
import {decrypt, encrypt} from "@/config/security";
import {getTreatments} from "@/modules/treatment/services/teatment-service";
import Accordion from 'primevue/accordion';
import AccordionTab from 'primevue/accordiontab';
import ProgressBar from 'primevue/progressbar';
import Loader from "@/components/loader.vue";
import ModalTreatment from "@/modules/treatment/views/ModalTreatment.vue";
import {onError} from "@/kernel/alerts";
import Unautorized from "@/components/Unautorized.vue";
import NotFound from "@/components/NotFound.vue";

export default {
  components: {NotFound, Unautorized, ModalTreatment, Loader, Accordion, AccordionTab, ProgressBar},

  data() {
    return {
      treatments: [],
      isLoading: true,
      unauthorized: false,
      selected: 0,
      visible: false,
      titleModal: 'Registrar tratamiento',
      selectedTreatment: {}
    }
  },
  props:{
    expedient:{
      type: Number,
      required: true
    }
  },
  methods: {
    async findTreatments() {
      try {
        this.isLoading = true
        const encrypted = this.$route.params.idExpedient
        const id = await decrypt(encrypted)
        if (isNaN(id)) {
          this.$router.replace({name: '404'})
        }
        const {status, data} = await getTreatments(id)
        if (status !== 200 && status !== 401 && status !== 500) {
          let message = 'Ocurrió un error al obtener los tratamientos'
          switch (data.text) {
            case 'Expedient not found':
              message = 'No se encontró el expediente'
              break;
            case 'Unauthorized user':
              message = 'No tienes permisos para ver los tratamientos'
              this.unauthorized = true
              break;
          }
          await onError('Ha ocurrido un error', message)
        }
        if (status === 200) {
          this.treatments = JSON.parse(await decrypt(data.result))
        }
      } catch (e) {
        await this.$router.replace({name: '404'})
      }
      this.isLoading = false
    },

    openRegister() {
      this.titleModal = 'Registrar tratamiento'
      this.visible = true
    },
    openUpdate(treatment){
      this.titleModal = 'Actualizar tratamiento'
      this.selectedTreatment = treatment
      this.visible = true
    },
    closeModal() {
      this.visible = false
      this.findTreatments()
    },
    async toCreateAppoint(id){
      this.$router.push({name: 'NewAppoint', params: {idTreatment: await encrypt(id)}})
    }
  },
  
  computed: {
    calcProgress() {
      return +(this.treatments[this.selected].appoints.filter(appoint => appoint.status === 'Completada').length / this.treatments[this.selected].appoints.filter(appoint => appoint.status !== 'Cancelada').length * 100)
    },
    calcTotalAppoints() {
      return this.treatments[this.selected].appoints.filter(appoint => appoint.status !== 'Cancelada').length
    },
    calcTotalCompletedAppoints() {
      return this.treatments[this.selected].appoints.filter(appoint => appoint.status === 'Completada').length
    }
  },

  mounted() {
    this.findTreatments();

  }
}

</script>

<style scoped>

.right {
  text-align: left;
  position: relative;
  height: 100%;
  background-color: #f5f5f5;
  padding: 20px;
  overflow-y: scroll;
}

.shape {
  padding: 40px 20px 20px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
  background-color: white;
  height: auto;
  overflow: hidden;
  text-align: left;
  position: relative;
  animation-name: resize2;
  animation-duration: 0.5s;
  animation-fill-mode: forwards;
  cursor: pointer;
}

.shape:not(:first-of-type) {
  margin-top: -20px;
}

.increase {
  animation-name: expand;
  animation-fill-mode: both;
  animation-timing-function: ease-in;
  animation-duration: 0.5s;
}


@keyframes expand {
  0% {
    box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
    height: calc(var(--initial-height) + 0px);
  }
  60% {
    height: calc(var(--final-height) - 30px);
  }
  100% {
    height: calc(var(--final-height) + 0px);
    box-shadow: rgb(118, 205, 236, 0.5) 0 8px 12px 0;
  }
}

.decrement {
  animation-name: decrease;
  animation-fill-mode: both;
  animation-timing-function: ease-in;
  animation-duration: 0.7s;
}

@keyframes decrease {
  0% {
    box-shadow: rgb(118, 205, 236, 0.5) 0 8px 12px 0;
    height: calc(var(--final-height) + 0px);
  }
  60% {
    height: calc(var(--initial-height) + 30px);
  }

  100% {
    height: calc(var(--initial-height) + 0px);
    box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
  }

}

.content-card {
  width: auto;
  box-sizing: content-box;
  display: inline-block;
}


.header-acc {
  height: 90px;
}

.head {
  display: inline-block;
  margin-right: 10px;
  margin-left: 10px;
  font-size: 14pt;
}

.accordion-button {
  background-color: #007bff;
  color: white;
}

.accordion-button:hover {
  background-color: #0056b3;
}

.form-label {
  font-weight: bold;
}

.progress-bar {
  background-color: #28a745;
}
</style>

<style>
.p-accordion-header-link {
  box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.2);
}

.p-accordion-header-link:hover {
  animation-name: hoverAccordion;
  animation-fill-mode: forwards;
  animation-duration: 0.5s;
}

@keyframes hoverAccordion {
  from {
    background-color: white;
  }
  to {
    background-color: #76cdec;
    border-radius: 15px;
    box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.4);
    transform: scale(1.03);
  }
}
</style>

