<template>
  <div>
    <form @submit.prevent="save(treatment)">
      <Dialog :closable="false" :closeOnEscape="false" :header="title" :modal="true" :visible.sync="visible"
              >
        <div class="mt-4 p-3 position-relative" style="min-width: 50vw; min-height: 50vh">
          <transition-group name="fade">
            <Loader v-if="isLoading" key="load"/>
            <b-row v-else key="content">
              <b-col class="mb-4" cols="12">
              <span class="p-float-label">
                  <Dropdown id="services" class="w-100" v-model="v$.service.$model" :options="services" optionLabel="name"
                            :class="{ 'p-invalid': v$.service.$error }"/>
                  <label for="services">Servicios</label>
              </span>
                <div v-if="v$.service.$dirty" class="text-danger text-start pt-2">
                  <p v-if="v$.service.required.$invalid" class="error-messages">
                    {{ v$.service.required.$message }}
                  </p>
                </div>
              </b-col>
              <b-col class="mb-4" cols="12">
              <span class="p-float-label">
                  <Textarea id="studies" v-model="v$.studies_description.$model" class="w-100" rows="2"
                            :class="{ 'p-invalid': v$.studies_description.$error }"/>
                  <label for="studies">Estudios de laboratorio</label>
              </span>
                <div v-if="v$.studies_description.$dirty" class="text-danger text-start pt-2">
                  <p v-if="v$.studies_description.required.$invalid" class="error-messages">
                    {{ v$.studies_description.required.$message }}
                  </p>
                  <p v-if="v$.studies_description.onlyLettersAndAccents.$invalid" class="error-messages">
                    {{ v$.studies_description.onlyLettersAndAccents.$message }}
                  </p>
                  <p v-if="v$.studies_description.length.$invalid" class="error-messages">
                    {{ v$.studies_description.length.$message }}
                  </p>
                </div>
              </b-col>
              <b-col class="mb-4" cols="12">
              <span class="p-float-label">
                  <Textarea id="recommendation" v-model="v$.recommendation.$model" class="w-100" rows="2"
                            :class="{ 'p-invalid': v$.recommendation.$error }"/>
                  <label for="recommendation">Recomendaciones</label>
              </span>
                <div v-if="v$.recommendation.$dirty" class="text-danger text-start pt-2">
                  <p v-if="v$.recommendation.required.$invalid" class="error-messages">
                    {{ v$.recommendation.required.$message }}
                  </p>
                  <p v-if="v$.recommendation.onlyLettersAndAccents.$invalid" class="error-messages">
                    {{ v$.recommendation.onlyLettersAndAccents.$message }}
                  </p>
                  <p v-if="v$.recommendation.length.$invalid" class="error-messages">
                    {{ v$.recommendation.length.$message }}
                  </p>
                </div>
              </b-col>
              <b-col class="mb-4" cols="12">
              <span class="p-float-label">
                  <Textarea id="support_home" v-model="v$.support_home.$model" class="w-100" rows="2"
                            :class="{ 'p-invalid': v$.support_home.$error }"/>
                  <label for="support_home">Soporte en casa</label>
              </span>
                <div v-if="v$.support_home.$dirty" class="text-danger text-start pt-2">
                  <p v-if="v$.support_home.required.$invalid" class="error-messages">
                    {{ v$.support_home.required.$message }}
                  </p>
                  <p v-if="v$.support_home.onlyLettersAndAccents.$invalid" class="error-messages">
                    {{ v$.support_home.onlyLettersAndAccents.$message }}
                  </p>
                  <p v-if="v$.support_home.length.$invalid" class="error-messages">
                    {{ v$.support_home.length.$message }}
                  </p>
                </div>
              </b-col>
            </b-row>
          </transition-group>
        </div>
        <template #footer>
          <b-row>
            <b-col cols="12">
              <Button :disabled="saving" class="p-button-rounded p-button-secondary" label="Cancelar"
                      @click="closeModal()"/>
              <Button :disabled="v$.$invalid || saving" class="p-button-rounded p-button-primary" label="Guardar"
                      :loading="saving" type="submit"/>
            </b-col>
          </b-row>
        </template>
      </Dialog>
    </form>
  </div>
</template>

<script>
import Dialog from "primevue/dialog";
import {helpers, required} from "@vuelidate/validators";
import {newregex} from "@/utils/regex";
import {reactive} from "@vue/composition-api";
import {useVuelidate} from "@vuelidate/core";
import Textarea from 'primevue/textarea';
import Dropdown from 'primevue/dropdown';
import {getServices, save, update} from "@/modules/treatment/services/teatment-service";
import {decrypt} from "@/config/security";
import {onError, onQuestion, onSuccess} from "@/kernel/alerts";
import Loader from "@/components/loader.vue";

export default {

  name: 'ModalTreatment',
  components: {Loader, Dialog, Textarea, Dropdown},
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    title: {
      type: String,
      required: true
    },
    oldTreatment: {
      type: Object,
      required: true
    },
    expedient:{
      type: Number,
      required: true
    }
  },

  data() {
    return {
      services: [],
      isLoading: true,
      saving: false,
    }
  },

  watch: {
    visible(value) {
      if (value) {
        this.getServices()
      }
    },
    isLoading(value) {
      if (!value) {
        if (this.oldTreatment.id) {
          this.v$.studies_description.$model = this.oldTreatment.studies_description
          this.v$.recommendation.$model = this.oldTreatment.recommendation
          this.v$.support_home.$model = this.oldTreatment.support_home
          this.v$.service.$model = this.services.find(s => s.id === this.oldTreatment.service.id) || '';
        }
      }
    }
  },

  setup() {
    const treatment = reactive({
      studies_description: '',
      recommendation: '',
      support_home: '',
      service: ''
    })

    const rules = {
      studies_description: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 3 && value.length < 150),
      },
      recommendation: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 3 && value.length < 150),
      },
      support_home: {
        required: helpers.withMessage("Campo obligatorio", required),
        onlyLettersAndAccents: helpers.withMessage("Caracteres no válidos", (value) => newregex.test(value)),
        length: helpers.withMessage("Apellido no válido", (value) => value.length > 3 && value.length < 150),
      },
      service:{
        required: helpers.withMessage("Campo obligatorio", required)
      }
    }

    const v$ = useVuelidate(rules, treatment)
    return {treatment, v$}
  },

  methods: {
    closeModal() {
      this.v$.studies_description.$reset()
      this.v$.recommendation.$reset()
      this.v$.support_home.$reset()
      this.$emit('close')
    },

    async getServices() {
      this.isLoading = true
      const {status, data} = await getServices()
      if (status === 200) {
        this.services = JSON.parse(await decrypt(data.result))
      }
      if (status === 404) {
        let message = 'Ocurrió un error al cargar los servicios'
        await onError('Ocurrión un error', message)
      }
      this.isLoading = false
    },

    save(treatment) {
      if (this.oldTreatment.id) {
        this.updateTreatment(treatment)
      } else {
        this.saveTreatment(treatment)
      }
    },
    async saveTreatment(treatment) {
      if (await onQuestion('Guardar tratamiento', '¿Está seguro de guardar el tratamiento?')) {
        const {service, ...rest} = treatment
        const req = {
          ...rest,
          service: service.id,
          expedient: this.expedient
        }
        this.saving = true
        const {status, data} = await save(req)
        if (status === 400){
          let message = 'Ocurrió un error al guardar el tratamiento'
          switch (data.text){
            case 'User not found':
              message = 'Usuario no encontrado'
              break
            case 'Invalid expedient':
              message = 'Expediente no válido'
              break
            case 'Unauthorized user':
              message = 'Usuario no autorizado'
              break
            case 'Invalid service':
              message = 'Servicio no válido'
              break
          }
          await onError('Ocurrió un error', message)
        }
        if (status === 200) {
          await onSuccess('Tratamiento guardado', 'El tratamiento se guardó correctamente')
              .then(() => this.closeModal())
        }
        this.saving = false
      }
    },
    async updateTreatment(treatment) {
      if (await onQuestion('Actualizar tratamiento', '¿Está seguro de actualizar el tratamiento?')) {
        const {service, ...rest} = treatment
        const req = {
          ...rest,
          id: this.oldTreatment.id,
          service: service.id,
          expedient: this.expedient
        }
        this.saving = true
        const {status, data} = await update(req)
        if (status === 400){
          let message = 'Ocurrió un error al actualizar el tratamiento'
          switch (data.text){
            case 'User not found':
              message = 'Usuario no encontrado'
              break
            case 'Not found':
              message = 'Tratamiento no encontrado'
              break
            case 'Invalid expedient':
              message = 'Expediente no válido'
              break
            case 'Unauthorized user':
              message = 'Usuario no autorizado'
              break
            case 'Invalid service':
              message = 'Servicio no válido'
              break
          }
          await onError('Ocurrió un error', message)
        }
        if (status === 200) {
          await onSuccess('Tratamiento actualizado', 'El tratamiento se actualizó correctamente')
              .then(() => this.closeModal())
        }
        this.saving = false
      }
    }
  },
}
</script>
<style scoped>
.error-messages {
  font-size: 13px;
  margin-bottom: 0;
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