<template>
    <div  class="w-100">
        <b-row>
            <b-col cols="12">
            <panel>
                <template #header>
                    <div class="d-flex justify-content-between w-100 align-items-center">
                        <p class="h5"><b>Generación de citas</b></p>
                    </div>
                </template>
                <b-row>
                    <b-col cols="12">
                        <div class="p-fluid grid form-container">
                           <div style="position: relative;" class="w-100 h-100">
                                <transition-group name="fade" type="transition">
                                    <div key="main">
                                        <b-row>
                                            <b-col cols="12" md="6" lg="6" sm="12">
                                                <div class="p-fluid grid form-container mb-3">
                                                    <b-row>
                                                        <b-col class="mt-3 mb-2" cols="12">
                                                            <div class="field text-start">
                                                                <label for="space-selector"
                                                                    class="form-label-required">Espacio:</label>
                                                                <Dropdown id="space-selector" class="text-center"
                                                                    v-model="selectedSpace" :options="spaces" optionLabel="name"
                                                                    placeholder="Seleccione un espacio" optionValue="id"
                                                                    @change="onSpaceSelected()"/>
                                                            </div>
                                                        </b-col>
                                                    </b-row>
                                                    <div v-if="dateSelected != null">
                                                        <b-row>
                                                            <b-col cols="12" sm="12" class="d-flex justify-content-end mt-1">
                                                                <div>
                                                                    <p>Fecha: <small class="text-muted">{{ dateSelected }}</small></p>
                                                                </div>
                                                            </b-col>
                                                        </b-row>
                                                        <b-row>
                                                            <b-col cols="12" md="6" lg="6" sm="12" class="mb-4">
                                                                <div class="field text-start">
                                                                    <span class="p-float-label p-input-icon-right">
                                                                        <Calendar id="start-hour-field" :timeOnly="true"
                                                                            hourFormat="24" showTime :manualInput="false"
                                                                            v-model="newAppoint.startHour"
                                                                            @input="datesAreFilled()"
                                                                            :inputStyle="{ 'border-color': areSame || isBeforeEnd ? '#ff0000' : ''}" />
                                                                        <label for="start-hour-field"
                                                                            class="form-label-required">
                                                                            Hora inicio</label>

                                                                    </span>
                                                                    <div class="text-danger text-danger text-start pt-2">
                                                                        <p class="error-messages" v-if="areSame">
                                                                            Las hora de incio no pueden ser iguales
                                                                        </p>
                                                                        <p class="error-messages" v-if="isBeforeEnd">
                                                                            La hora de inicio no puede ser mayor a la hora de
                                                                            fin
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </b-col>
                                                            <b-col cols="12" md="6" lg="6" sm="12">
                                                                <div class="field text-start ">
                                                                    <span class="p-float-label p-input-icon-right">
                                                                        <Calendar id="end-hour-field" :timeOnly="true" showTime
                                                                            :manualInput="false" hourFormat="24"
                                                                            v-model="newAppoint.endHour"
                                                                            @input="datesAreFilled()"
                                                                            :inputStyle="{ 'border-color': areSame ? '#ff0000' : ''}" />
                                                                        <label for="end-hour-field"
                                                                            class="form-label-required">Hora fin</label>
                                                                    </span>
                                                                    <div class="text-danger text-danger text-start pt-2">
                                                                        <p class="error-messages" v-if="areSame">
                                                                            Las horas no pueden ser iguales
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </b-col>
                                                        </b-row>
                                                        <b-row class="mt-2 justify-content-end mt-3">
                                                            <b-col cols="12" lg="4" md="6" sm="6">
                                                                <Button icon="pi pi-times" label="Cancelar"
                                                                    class="p-button-rounded p-button-secondary" />
                                                            </b-col>
                                                            <b-col cols="12" lg="4" md="6" sm="6">
                                                                <Button icon="pi pi-save" label="Guardar"
                                                                    class="p-button-rounded button-style" @click="saveAppoint"
                                                                    :disabled="!isFormValid && !onSave"  :loading="onSave"/>
                                                            </b-col>
                                                        </b-row>
                                                    </div>   
                                                </div>
                                            </b-col>
                                            <b-col cols="12" md="6" lg="6" sm="12">
                                                <div style="position: relative;" class="p-1 w-100 h-100" v-if="selectedSpace != null">
                                                    <transition-group name="fade" type="transition">
                                                        <Loader v-if="isLoading" key="load"/>
                                                        <div v-else key="main">
                                                            <FullCalendar :options="calendarOptions" id="myCustomCalendar">
                                                                <template v-slot:eventContent='{event}'>
                                                                    <b-row>
                                                                        <b-col cols="12" lg="12" md="4" sm="3">
                                                                            <div class="my-custom-event">
                                                                                <span class="my-event-dot" :style="{'background-color': setDotBackgrund(event.extendedProps.statusEvent)}"></span>
                                                                                <div class="my-event-info text-center">
                                                                                    <span class="my-event-title"><b>{{ event.title }}</b></span>
                                                                                    <span class="my-event-time">{{ formatCalendarDate(event._instance.range.start) }} - {{ formatCalendarDate(event._instance.range.end) }}</span>
                                                                                </div>
                                                                            </div>
                                                                        </b-col>
                                                                    </b-row>
                                                                </template>
                                                            </FullCalendar>
                                                        </div>
                                                    </transition-group>
                                                </div>
                                                <div v-else
                                                    class="p-1 d-flex justify-content-center align-items-center alert-container">
                                                    <p>Seleccione un espacio para visualizar las citas.</p>
                                                </div>
                                            </b-col>
                                        </b-row>
                                    </div>
                                </transition-group>
                           </div>
                           <Toast/>
                            <Dialog/>
                        </div>
                    </b-col>
                </b-row>
            </panel>
            </b-col>
        </b-row>
    </div>
</template>
<script> 

import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import esLocale from '@fullcalendar/core/locales/es';
import Dropdown from 'primevue/dropdown';
import Divider from 'primevue/divider';
import Calendar from 'primevue/calendar';
import ModalSaveAppoint from './ModalDetailAppoint.vue';
import spaceServices from "@/modules/spaces/services/spaces-services"
import appointServices from "@/modules/appointment/services/appoint-services"
import { decrypt, encrypt } from '@/config/security';
import Toast from 'primevue/toast';
import InputText from 'primevue/inputtext/InputText';
import moment from 'moment'
import Loader from "@/components/loader.vue";
import Dialog from 'primevue/dialog';
export default {
    components: {
        FullCalendar,
        Dropdown,
        Divider,
        Calendar,
        ModalSaveAppoint,
        Toast,
        InputText,
        Loader,
        Dialog
    },
    name: 'NewAppoint',

    data(){
        return{ 
            calendarOptions: {
                plugins: [ dayGridPlugin, interactionPlugin ],
                initialView: 'dayGridMonth',
                locale: esLocale,
                events: this.appoints,
                selectable: true,
                weekends: false,
                dateClick: this.handleDateClick,
                headerToolbar: {
                start: 'title',
                center: '',
                end: 'today prev,next'
                },
                dayMaxEventRows: 2,
                eventLimitText: "Ver más",
                views: {
                dayGridMonth: {
                    titleFormat: { year: 'numeric', month: 'long' }
                },
                },
            },
            selectedSpace: null,
            spaces: [],
            displaySaveModal: false,
            appoints: [],
            dateSelected: null,
            newAppoint: {
                startHour: '',
                endHour: ''
            },
            areSame: false,
            isBeforeEnd: false,
            isFormValid: false,
            isLoading: false,
            onSave: false
        }
    },
    methods: {
        print(comming){
            console.log(comming)
        },
        formatCalendarDate(pop){
            const format = new Date(pop)
            return moment(format).format('HH:mm')
        },
        datesAreFilled(){
            if(this.newAppoint.startHour !== '' && this.newAppoint.endHour !== ''){
                this.validateHours(this.newAppoint.startHour, this.newAppoint.endHour)
            }
        },
        setDotBackgrund(status){
            let color = '';
            switch (status) {
                case 'Pendiente':
                    color = 'orange'
                    break;
                case 'Completada':
                    color = 'green'
                    break;
                case 'Cancelada':
                    color = 'gray'
                    break;
                case 'Reprogramada':
                    color = 'blue'
                    break;
                default:
                    color = 'red'
                    break;
            }
            return color
        },
        async saveAppoint(){
            if(this.newAppoint.startHour && this.newAppoint.startHour !== '' && this.newAppoint.endHour && this.newAppoint.endHour !== ''){
                if(this.isFormValid){
                    const {startHour, endHour} = this.newAppoint
                    const start = this.formatHour(startHour)
                    const end = this.formatHour(endHour)
                    const appoint = {
                        start_hour: moment(this.formatDate(start)).format('YYYY-MM-DDTHH:mm:ss'),
                        end_hour: moment(this.formatDate(end)).format('YYYY-MM-DDTHH:mm:ss'), 
                        treatment: await this.getTreatmentFromUrl(),
                        space: this.selectedSpace
                    }
                    this.onSave = true
                    const appointEncrypted = await encrypt(JSON.stringify(appoint))
                    try {
                        const {status} = await appointServices.saveAppointment(appointEncrypted)
                        if(status === 200 || status === 201){ 
                            this.$toast.add({severity:'success', summary: '¡Éxito!', detail: '¡Cita guardada con éxito!', life: 3000});
                            this.newAppoint = {
                                startHour: '',
                                endHour: ''
                            }
                            this.areSame = false;
                            this.isBeforeEnd = false;
                            this.isFormValid = false;
                        }
                        this.onSave = false
                    } catch (error) {
                        console.log("Error: ", error)
                    }
                }else{
                    this.onSave = false
                    this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail: '¡Parece que las horas ingresadas no son válidas!', life: 3000});
                }
            }else{
                this.onSave = false
                this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail: '¡Asegurate de llenar todos los campos!', life: 3000});
            }
        },
        async getTreatmentFromUrl(){
            return await decrypt(this.$route.params.idTreatment)
        },
        validateHours(start, end) {
            const startTime = moment(this.formatHour(start), 'HH:mm')
            const endTime = moment(this.formatHour(end), 'HH:mm')
            this.areSame = false;
            this.isBeforeEnd = false;

             if (startTime.isSame(endTime)) {
                this.areSame = true;
            }
            if (startTime.isAfter(endTime)) {
                this.isBeforeEnd = true;
            }

            this.isFormValid = !this.areSame && !this.isBeforeEnd;
        },
        formatHour(unfformatedHour){
            const format = new Date(unfformatedHour)
            let hour = format.getHours().toString().padStart(2, '0')
            let minutes = format.getMinutes().toString().padStart(2, '0')
            let seconds = format.getSeconds().toString().padStart(2, '0')
            return `${hour}:${minutes}:${seconds}`
        },
        formatDate(formmatedHour){
            const date = JSON.parse(JSON.stringify(this.dateSelected))
            return `${date}T${formmatedHour}` 
        },
        handleDateClick(arg) {
            this.dateSelected = arg.dateStr
        },
        openModalSaveSpeciality() {
            this.displaySaveModal = true;
        },
        async onSpaceSelected(){
            if(this.selectedSpace != null){
                try {
                    this.isLoading = true
                    this.appoints = []
                    const id = JSON.parse(JSON.stringify(this.selectedSpace))
                    const {status, data : {result}} = await appointServices.getAppointmentsBySpace(await encrypt(id))
                    if(status === 200 || status === 201){
                        this.isLoading = false
                        const appointsDecryoted = JSON.parse(await decrypt(result))
                        if(appointsDecryoted.length > 0){
                            appointsDecryoted.map((appoint) => {
                                const {endHour, startHour, space:{name}, status} = appoint
                                this.appoints.push({
                                    title: `${name}`,
                                    start: new Date(startHour).toISOString(),
                                    end: new Date(endHour).toISOString(),
                                    statusEvent: status
                                })
                            }) 
                        }else{
                            this.isLoading = false
                            this.$toast.add({severity:'info', summary: 'Sin citas', detail:'¡Este espacio no contiene citas hasta ahora!', life: 3000});
                        }
                    } 
                    
                } catch (error) {
                    console.log("Error: ", error)
                }
            }
        },
        getAppoints(){
            return this.appoints
        },
        handleEventClick(arg) {
            console.log('event click! ', arg.event)
        },
        async getAllSpaces(){
            try {
                const {status, data : {result}} = await spaceServices.getAllSpaces()
                if(status === 200 || status === 201){
                    const {content} = JSON.parse(await decrypt(result))
                    this.spaces = content
                }
            }catch(error){
                console.log("Error: ", error)
            }
        }
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

<style scoped>

.button-style{
    background: #2a715a;
    border: none;
}

.button-style:hover{
    background-color: #368368 !important;
}

.form-label-required::after{
    content: " *";
    color: #ff0000;
}

.alert-container{
    width: 100%; 
    height: 100%;
    background: #FFF3CD;
    color: #856404;
}

.invalid-field-custom{
    border-color:  #ff0000 !important;
    box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
}

.error-messages{
    margin-bottom: 0; font-weight: 350; font-size: 15px;
}

.error-messages::before{
    content: "* ";
    color: #ff0000;
}

.form-label-required::after{
    content: " *";
    color: #ff0000;
}

.my-custom-event {
    display: flex;
    align-items: center;
    padding-left: 10px;
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
</style>