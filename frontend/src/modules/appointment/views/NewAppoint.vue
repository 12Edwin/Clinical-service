<template>
    <div  class="w-100">
        <b-row>
            <b-col cols="12">
                <Header style="margin-bottom: 20px" title="Gestión de citas"/>
            </b-col>
            <b-col cols="12">
            <panel class="fadeclass">
                <template #header>
                    <div class="d-flex justify-content-between w-100 align-items-center">
                        <p class="h5"><b>Nueva cita</b></p>
                    </div>
                </template>
                <b-row>
                    <b-col cols="12">
                        <div class="p-fluid grid form-container">
                           <div style="position: relative;" class="w-100 h-100">
                                <transition-group name="fade" type="transition">
                                    <div key="main">
                                        <b-row>
                                            <b-col cols="12" md="12" lg="5" sm="12">
                                                <div class="p-fluid grid form-container mb-3">
                                                    <b-row>
                                                        <b-col class="mt-2 mb-2" cols="12">
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
                                                    <div>
                                                        <b-row>
                                                            <b-col cols="12" sm="12" class="d-flex justify-content-end mt-1">
                                                                <div v-if="selectedSpace != null">
                                                                    <p><small style="color: #b0acac">{{ showDate ? 'Fecha: '+ dateSelected : '¡Selecciona una fecha!'}}</small></p>
                                                                </div>
                                                            </b-col>
                                                        </b-row>
                                                        <b-row>
                                                            <b-col cols="12" md="6" lg="6" sm="12" class="mb-4">
                                                                <div class="field text-start">
                                                                    <span class="p-float-label p-input-icon-right">
                                                                        <Calendar id="start-hour-field" :timeOnly="true"
                                                                            hourFormat="12" showTime :manualInput="false"
                                                                            v-model="newAppoint.startHour"
                                                                            @input="datesAreFilled()"
                                                                            :inputStyle="{ 'border-color': areSame || isBeforeEnd || isBeforeCurrent ? '#ff0000' : ''}" 
                                                                            :disabled="!showDate"
                                                                        />
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
                                                                        <p class="error-messages" v-if="isBeforeCurrent">
                                                                            La hora de inicio no puede ser menor a la hora
                                                                            actual
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </b-col>
                                                            <b-col cols="12" md="6" lg="6" sm="12">
                                                                <div class="field text-start ">
                                                                    <span class="p-float-label p-input-icon-right">
                                                                        <Calendar id="end-hour-field" :timeOnly="true" showTime
                                                                            :manualInput="false" hourFormat="12"
                                                                            v-model="newAppoint.endHour"
                                                                            @input="datesAreFilled()"
                                                                            :inputStyle="{ 'border-color': areSame || isGraterThanFive ? '#ff0000' : ''}" :disabled="!showDate"/>
                                                                        <label for="end-hour-field"
                                                                            class="form-label-required">Hora fin</label>
                                                                    </span>
                                                                    <div class="text-danger text-danger text-start pt-2">
                                                                        <p class="error-messages" v-if="areSame">
                                                                            Las horas no pueden ser iguales
                                                                        </p>
                                                                        <p class="error-messages" v-if="isGraterThanFive">
                                                                            La cita no puede durar más de 5 horas
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </b-col>
                                                        </b-row>
                                                        <b-row class="mt-2 justify-content-end mt-3">
                                                            <b-col cols="12" lg="4" md="6" sm="6">
                                                                <Button icon="pi pi-times" label="Cancelar"
                                                                    class="p-button-rounded p-button-secondary" 
                                                                    @click="() => $router.push({name: 'clinical-history'})"
                                                                />
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
                                            <b-col cols="12" md="12" lg="7" sm="12">
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
                                                                                    <span class="my-event-time">{{ formatCalendarDate(event.start) }} - {{ formatCalendarDate(event.end) }}</span>
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
import { format12Time, formatDate, formatDate2, formatTime } from '@/utils/regex';
import Header from '@/components/Header.vue';
import { onQuestion, onError, onSuccess } from '@/kernel/alerts';

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
        Dialog,
        Header
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
                dateDidMount: (info) => {this.dateDidMount(info)},
                validRange: this.validRange(),
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
            isBeforeCurrent: false,
            isGraterThanFive: false,
            isLoading: false,
            onSave: false,
            showDate: false
        }
    },
    methods: {
        dateDidMount(info) {
            console.log(info)
            if (info.date < new Date()) {
                info.el.classList.add('fc-day-past');
            }else{
                info.el.classList.add('fc-day-ok');
            }
        },
        validRange() {
            const today = new Date();
            today.setHours(0, 0, 0, 0); 
            return {
                start: today
            };
        },
        formatCalendarDate(pop){
            return moment(pop).format(format12Time)
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
            if(this.newAppoint.startHour && this.newAppoint.startHour !== '' && this.newAppoint.endHour && this.newAppoint.endHour !== '' && this.selectedSpace != null && this.dateSelected != null){
                if(this.isFormValid){
                    try {
                        if(await onQuestion('¿Estás seguro de agendar esta cita?')){
                            const {startHour, endHour} = this.newAppoint
                            const start = this.formatHour(startHour)
                            const end = this.formatHour(endHour)
                            const appoint = {
                                start_hour: moment(this.formatDate(start)).format(formatDate),
                                end_hour: moment(this.formatDate(end)).format(formatDate), 
                                treatment: await this.getTreatmentFromUrl(),
                                space: this.selectedSpace
                            }
                            this.onSave = true
                            const appointEncrypted = await encrypt(JSON.stringify(appoint))
                            const {status, data: {text}} = await appointServices.saveAppointment(appointEncrypted)
                            if(status === 400){
                                this.onSave = false
                                let message = 'Ocurrion un error al agendar la cita'
                                switch (text) {
                                    case 'User not found':
                                        message = 'Usuario no encontrado'
                                        break;
                                    case 'Not found':
                                        message = 'Cita no encontrada'
                                        break;
                                    case 'Treatment not found':
                                        message = 'Tratamiento no encontrado'
                                        break;
                                    case 'Expedient not found':
                                        message = 'Expediente no encontrado'
                                        break;
                                    case 'Unauthorized user':
                                        message = 'Usuario no autorizado'
                                        break;
                                    case "Maximum 5 hours":
                                        message = 'La cita no puede durar más de 5 horas'
                                        break;
                                    case "Minimum 1 hour":
                                        message = 'La cita debe durar al menos 1 hora'
                                        break;
                                    case "Invalid schedule":
                                        message = 'Horario no válido'
                                        break;
                                    case "The space is busy in this schedule":
                                        message = 'El espacio está ocupado en este horario'
                                        break;
                                    case "Cannot be more than one month from now":
                                        message = 'La cita no puede programarse después de un mes'
                                        break;
                                }
                                await onError('Ocurrió un error al agendar la cita', message).then(() => {
                                    this.cleanFields()
                                    this.onSpaceSelected()
                                })
                            }
                            if(status === 200 || status === 201){ 
                                this.onSave = false
                                await onSuccess('Cita agendada', 'La cita ha sido agendada con éxito')
                                    .then(() => {
                                        this.cleanFields()
                                        this.onSpaceSelected()
                                    }
                                )
                            }
                        this.onSave = false
                        this.showDate = false
                        }
                    } catch (error) {
                        this.onSave = false
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
            const startTime = moment(this.formatHour(start), formatTime)
            const endTime = moment(this.formatHour(end), formatTime)
            const currentHour = moment()
            const date = moment(this.dateSelected)

            this.areSame = false;
            this.isBeforeEnd = false;
            this.isBeforeCurrent = false;
            this.isBeforeToday = false;
            this.isGraterThanFive = false;
            
            if (date.isSame(currentHour.format(formatDate2))) {
                if(currentHour.isAfter(startTime)){
                    this.isBeforeCurrent = true;
                }
            }
            if (startTime.isSame(endTime)) {
                this.areSame = true;
            }
            if (startTime.isAfter(endTime)) {
                this.isBeforeEnd = true;
            }
            if( startTime.isBefore(moment())){
                this.isBeforeToday = true;
            }
            if(endTime.diff(startTime, 'hours') > 5){
                this.isGraterThanFive = true;
            }
            this.isFormValid = !this.areSame && !this.isBeforeEnd && !this.isBeforeCurrent && !this.isGraterThanFive;
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
            this.showDate = true
            this.newAppoint = {
                startHour: '',
                endHour: ''
            }
            this.areSame = false;
            this.isBeforeEnd = false;
            this.isFormValid = false;
            this.isBeforeCurrent = false;
            this.isGraterThanFive = false;
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
        },
        cleanFields(){
            this.newAppoint = {
                startHour: '',
                endHour: ''
            }
            this.areSame = false;
            this.isBeforeEnd = false;
            this.isFormValid = false;
            this.isBeforeCurrent = false;
            this.dateSelected = null
            this.isGraterThanFive = false;
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
    background: transparent;
    color: #b0acac;
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

.fc-day-past {
  background-color: #f0f0f0 !important;
  color: #ccc !important;
  cursor: not-allowed !important;
}

.fc-day-ok{
    cursor: pointer !important;
}
</style>