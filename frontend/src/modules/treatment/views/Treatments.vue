<template>
  <div style="position: relative" class="w-100 h-100">
    <transition-group name="fade" type="transition">
      <loader v-if="isLoading" key="load"/>
      <div v-else key="main">
        <Header :title="'Expediente Médico, folio: ' + (expedient.folio || 'No definido')"/>
        <b-row>
          <b-col lg="5" md="12" class="mt-4">
            <div class="left"
                 style="background-color: #f5f5f5; border-radius: 10px; padding: 20px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">

              <div class="d-flex justify-content-between">
                <h2 style="color: #333; margin-bottom: 20px; text-transform: uppercase; font-size: 24px;">
                  Información del Paciente
                </h2>
                <Button :disabled="unauthorized" class="p-button-rounded p-button-outlined px-3" @click="goToEdit">
                  <BIcon icon="pencil-fill"/>
                </Button>
              </div>
              <Unautorized message="No tienes permisos para ver este expediente" v-if="unauthorized"/>
              <NotFound message="Expediente no encotrado" v-if="!expedient.id && !unauthorized"/>
              <div class="row" v-if="expedient.id">
                <div class="cols-12">
                  <div class="form-group">
                    <label for="nombre">Nombre:</label>
                    <div><InputText class="input-read" readonly type="text" id="nombre"   :value="expedient.name + ' ' + expedient.surname + ' ' + expedient.lastname"/></div>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="email">Email:</label>
                    <div><InputText class="input-read" readonly type="email" id="email"   v-model="expedient.email"/></div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <label for="telefono">Teléfono:</label>
                    <div><InputText class="input-read" readonly type="tel" id="telefono"   v-model="expedient.phone"/></div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <label for="ocupacion">Ocupación:</label>
                    <div><InputText class="input-read" readonly type="text" id="ocupacion"   v-model="expedient.occupation"/></div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                    <div><InputText class="input-read" readonly type="text" id="fechaNacimiento"   :value="new Date (expedient.birthday).toLocaleDateString()"/></div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="genero">Género:</label>
                    <div><InputText class="input-read" readonly type="text" id="genero"   v-model="expedient.sex"/></div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="lugarNacimiento">Lugar de Nacimiento:</label>
                    <div><InputText class="input-read" readonly type="text" id="lugarNacimiento"   v-model="expedient.place_of_birth"/></div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="estadoCivil">Estado Civil:</label>
                    <div><InputText class="input-read" readonly type="text" id="estadoCivil"   v-model="expedient.marital_status"/></div>
                  </div>
                </div>

                <div class="col-md-4">
                  <div class="form-group">
                    <label for="peso">Peso:</label>
                    <div><InputText class="input-read" readonly type="text" id="peso"   v-model="expedient.weight"/></div>
                  </div>
                </div>

                <div class="col-md-4">
                  <div class="form-group">
                    <label for="altura">Altura:</label>
                    <div><InputText class="input-read" readonly type="text" id="altura"   v-model="expedient.height"/></div>
                  </div>
                </div>

                <div class="col-md-4">
                  <div class="form-group">
                    <label for="sex">Sexo:</label>
                    <div><InputText class="input-read" readonly type="text" id="sex"   v-model="expedient.sex"/></div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <label for="allergies">Alergias:</label>
                    <div><InputText class="input-read" readonly type="text" id="allergies" v-model="expedient.allergies"/></div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <label for="pathology">Patologías:</label>
                    <div class="shape"> <div v-for="pathology in expedient.pathologicalRecords"> <span v-tooltip="pathology.description" class="badge item badge-secondary"> {{ pathology.name }} </span> </div></div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <label for="diseases">Enfermedades:</label>
                    <div class="shape"> <div v-for="disease in expedient.diseases"> <span v-tooltip="disease.description" class="badge item badge-secondary"> {{ disease.name }} </span> </div></div>
                  </div>
                </div>
              </div>
              <BButton class="p-button-rounded px-3 w-auto" variant="outline-danger" @click="goBack">
                <BIcon icon="arrow-left"/>
                <span class="me-3"> Regresar </span>
              </BButton>
            </div>

          </b-col>
          <b-col lg="7" md="12" class="mt-4">
            <CardTreatment :expedient="expedient.id"/>
          </b-col>
        </b-row>
      </div>
    </transition-group>

  </div>
</template>

<script>
import CardTreatment from "@/modules/treatment/views/CardTreatment.vue";
import Loader from "@/components/loader.vue";
import {getExpedient} from "@/modules/treatment/services/teatment-service";
import {decrypt} from "@/config/security";
import Tooltip from "primevue/tooltip";
import InputText from 'primevue/inputtext';
import Header from "@/components/Header.vue";
import {onError} from "@/kernel/alerts";
import Unautorized from "@/components/Unautorized.vue";
import NotFound from "@/components/NotFound.vue";
export default {
  directives: {
    'tooltip': Tooltip
  },
  components: {NotFound, Unautorized, Header, Loader, CardTreatment, InputText},

  data() {
    return {
      expedient: {},
      treatments: [],
      isLoading: true,
      unauthorized: false
    }
  },
  methods: {
    async findExpedient() {
      try {
        this.isLoading = true
        const encrypted = this.$route.params.idExpedient
        const id = await decrypt(encrypted)
        const {status, data} = await getExpedient(id)
        if (status !== 200 && status !== 401 && status !== 500) {
          let message = 'Error al obtener el expediente'
          switch (data.text){
            case 'Not found':
              message = 'Expediente no encontrado';
              break;
            case 'Unauthorized user':
              message = 'No tienes permisos para ver este expediente';
              this.unauthorized = true;
              break;
          }
          await onError('Ha ocurrido un error', message)
        }
        if (status === 200) {
          this.expedient = JSON.parse(await decrypt(data.result))
        }
      } catch (e) {}
      this.isLoading = false
    },

    async goToEdit() {
      this.$router.push({name: 'modify-expedient', params: {idExpedient: this.$route.params.idExpedient}})
    },

    goBack() {
      this.$router.go(-1)
    }
  },

  mounted() {
    this.findExpedient();
  }
}
</script>

<style scoped>
.form-group{
  padding-top: 10px;
}
.left {
  padding: 20px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
  background-color: white;
  height: 100%;
  text-align: left;
}

.shape{
  display: flex;
  flex-wrap: wrap;
  min-height: 30px;
  padding: 5px;
  border-radius: 5px;
  background-color: white;
}

.item{
  font-size: 12pt;
  margin: 5px;
  background-color: #5da7c2;
}

.input-read{
  border: none;
  border-radius: 5px;
  padding: 5px;
  width: 100%;
}

label{
  font-weight: bold;
  color: #333;
}
</style>
<style>
.fade-enter-active {
  transition: all 1s;
}

.fade-leave-active {
  transition: all .1s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>