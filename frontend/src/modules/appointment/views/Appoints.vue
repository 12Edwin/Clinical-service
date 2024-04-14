<template>
  <div class="h-100 w-100">
    <div key="content">
      <b-row>
        <b-col cols="12">
          <Header style="margin-bottom: 20px" title="Gestión de citas"/>
        </b-col>
        <b-col cols="12">
          <panel class="fadeclass">
            <template #header>
              <div class="d-flex justify-content-between w-100 align-items-center">
                <p class="h5"><b>Citas registradas</b></p>
              </div>
            </template>
            <b-row>
              <b-col cols="12">
                <div class="p-fluid grid form-container">
                  <b-row>
                    <b-col>
                      <div class="field text-start">
                        <Dropdown id="space-selector" class="text-center" v-model="selectedSpace" :options="spaces"
                          optionLabel="name" placeholder="Seleccione un espacio" optionValue="id"
                          @change="onSpaceSelected()" />
                      </div>
                    </b-col>
                    <b-col cols="12">
                      <div class="flex align-items-center mt-3">
                        <Chip style="margin-right: 9px;" v-for="(badge, index) in badges" :label="badge.status" :icon="badge.icon" class="mr-2"
                          :style="{'background-color': setDotBackgrund(badge.status), 'color': 'white'}" :key="index"/>
                      </div>
                    </b-col>
                    <b-col class="mt-3" cols="12">
                      <transition-group name="fade">
                        <Loader v-if="isLoading" key="load"/>
                        <div v-else key="content">
                          <FullCalendar :options="calendarOptions" id="myCustomCalendar">
                            <template v-slot:eventContent='{ event }'>
                              <div>
                                <b-row>
                                <b-col cols="12" lg="12" md="4" sm="3">
                                  <div class="my-custom-event" @click="openAppointDetail(event.extendedProps.appointment)">
                                    <span class="my-event-dot"
                                      :style="{'background-color': setDotBackgrund(event.extendedProps.appointment.status)}"></span>
                                    <div class="my-event-info">
                                      <span class="my-event-title"><b>{{ event.title }}</b></span>
                                      <span class="my-event-time">{{ formatCalendarDate(event.start) }} - {{formatCalendarDate(event.end) }}</span>
                                    </div>
                                    <div>
                                  </div>
                                  </div>
                                </b-col>
                              </b-row>
                              </div>
                            </template>
                          </FullCalendar>
                        </div>
                      </transition-group>
                    </b-col>
                  </b-row>
                </div>
              </b-col>
            </b-row>
          </panel>
          <ModalDetailAppoint :visible.sync="displayModal" :appoint="appoint" @onSpaceSelected="onSpaceSelected"/>
          <Toast/>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import Dropdown from 'primevue/dropdown';
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import esLocale from '@fullcalendar/core/locales/es';
import spaceServices from "@/modules/spaces/services/spaces-services"
import {decrypt, encrypt} from "@/config/security"
import appointServices from "@/modules/appointment/services/appoint-services"
import Toast from 'primevue/toast';
import Chip from 'primevue/chip';
import ModalDetailAppoint from './ModalDetailAppoint.vue';
import moment from 'moment'
import Delete from '../components/Delete.vue';
import { format12Time } from '@/utils/regex';
import Menu from 'primevue/menu';
import Header from '@/components/Header.vue';
import Loader from "@/components/loader.vue";
export default {
  components:{
    Dropdown,
    FullCalendar,
    Toast,
    Chip,
    ModalDetailAppoint,
    Delete,
    Menu,
    Header,
    Loader
  },
  data(){
    return {
      calendarOptions: {
        plugins: [ dayGridPlugin, interactionPlugin ],
        initialView: 'dayGridMonth',
        locale: esLocale,
        events: this.appoints,
        weekends: false,
        dayMaxEventRows: 2,
        eventLimitText: "Ver más",
        headerToolbar: {
          start: 'title',
          center: '',
          end: 'today prev,next'
        },
        views: {
          dayGridMonth: {
            titleFormat: { year: 'numeric', month: 'long' }
          }
        },
      },
      spaces: [],
      selectedSpace: null,
      appoints: [],
      badges: [
        {
          status: 'Pendiente',
          icon: 'pi pi-history'
        },
        {
          status: 'Reprogramada',
          icon: 'pi pi-history'
        },
        {
          status: 'Cancelada',
          icon: 'pi pi-times'
        },
        {
          status: 'Completada',
          icon: 'pi pi-check'
        }
      ],
      displayModal: false,
      appoint: null,
      onDrag: false,
      items: [
				{
					label: 'Update',
					icon: 'pi pi-refresh',
					command: () => {
						this.$toast.add({severity:'success', summary:'Updated', detail:'Data Updated', life: 3000});
					}
				},
				{
					label: 'Delete',
					icon: 'pi pi-times',
					command: () => {
						this.$toast.add({ severity: 'warn', summary: 'Delete', detail: 'Data Deleted', life: 3000});
					}
				},
				{
					label: 'Vue Website',
					icon: 'pi pi-external-link',
					command: () => {
						window.location.href = 'https://vuejs.org/'
					}
				},
				{
					label: 'Upload',
					icon: 'pi pi-upload',
					command: () => {
						window.location.hash = "/fileupload"
					}
				}
			],
      isLoading: false
    }
  },
  methods: {
    async onSpaceSelected(){
            if(this.selectedSpace != null){
                this.isLoading = true
                try {
                    this.appoints = []
                    const id = JSON.parse(JSON.stringify(this.selectedSpace))
                    const {status, data : {result}} = await appointServices.getAppointmentsBySpace(await encrypt(id))
                    if(status === 200 || status === 201){
                        this.isLoading = false
                        const appointsDecryoted = JSON.parse(await decrypt(result))
                        if(appointsDecryoted.length > 0){
                            appointsDecryoted.map((appoint) => {
                                const {endHour, startHour, space:{name}} = appoint
                                this.appoints.push({
                                    title: `${name}`,
                                    start: new Date(startHour).toISOString(),
                                    end: new Date(endHour).toISOString(),
                                    appointment: appoint,
                                })
                            }) 
                        }else{
                          this.isLoading = false
                          this.$toast.add({severity:'info', summary: 'Sin citas', detail:'¡Este espacio no contiene citas hasta ahora!', life: 3000});
                        }
                    } 
                    
                } catch (error) {
                    this.isLoading = false
                    console.log("Error: ", error)
                }
            }
    },
    async getAllSpaces(){
            try {
                const {status, data : {result}} = await spaceServices.getAllSpaces()
                this.isLoading = true
                if(status === 200 || status === 201){
                  this.isLoading = false
                    const {content} = JSON.parse(await decrypt(result))
                    this.spaces = content
                }
            }catch(error){
                this.isLoading = false
                console.log("Error: ", error)
            }
    },
    formatCalendarDate(pop){
      return moment(pop).format(format12Time)
    },
    setDotBackgrund(status){
            let color = '';
            switch (status) {
                case 'Pendiente':
                    color = 'orange'
                    break;
                case 'Completada':
                    color = '#368368'
                    break;
                case 'Cancelada':
                    color = 'gray'
                    break;
                case 'Reprogramada':
                    color = '#2196F3'
                    break;
                default:
                    color = 'red'
                    break;
            }
            return color
    },
    openAppointDetail(appoint){
      this.displayModal = true
      this.appoint = JSON.stringify(appoint)
    },
    
  },
  mounted(){
        this.getAllSpaces()
  },
  watch: {
        appoints: {
            handler: function (newVal) {
                this.calendarOptions.events = newVal
            },
            deep: true
        },
    }
}
</script>

<style>
#myCustomCalendar .fc-button {
  background: #2a715a;
  color: #fff;
  border-color: #2a715a;
  border-radius: 32px;
}

#myCustomCalendar .fc-button:hover {
  background-color: #368368;
  border-color: #368368;
}

</style>

<style scoped>
.my-custom-event {
    display: flex;
    align-items: center;
    padding-left: 10px;
    cursor: pointer;
}

.my-event-dot {
    width: 10px;
    height: 10px;
    background-color: blue;
    border-radius: 50%;
    margin-right: 5px;
}

.my-event-info {
    display: flex;
    flex-direction: column;
}

.my-event-title {
    font-size: 14px;
    margin-bottom: 2px;
    color: #000;
}

.my-event-time {
    font-size: 12px;
    color: #000;
}

.fadeclass{
  animation-name: fade;
  animation-duration: 1s;
}

@keyframes fade {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
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
