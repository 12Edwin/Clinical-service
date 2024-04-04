<template>
    <div class="w-100">
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
                                <b-row>
                                    <b-col cols="12" md="6" lg="6" sm="12">
                                        <div class="p-fluid grid form-container mb-3">
                                            <b-row>
                                                <b-col class="mt-3 mb-2" cols="12">
                                                    <div class="field text-start">
                                                        <label for="space-selector" class="form-label-required">Espacio:</label>
                                                        <Dropdown id="space-selector" class="text-center" v-model="selectedSpace" :options="spaces"
                                                            optionLabel="name" placeholder="Seleccione un espacio" @change="onSpaceSelected()" />
                                                    </div>
                                                </b-col>
                                            </b-row>
                                            <b-row>
                                                <b-col cols="12" sm="12">
                                                    <div class="field text-start mt-3">
                                                        <span class="p-float-label p-input-icon-right">
                                                            <InputText id="start-hour-field" :timeOnly="true" hourFormat="12" showTime :manualInput="false" v-model="newAppoint.startHour" />
                                                            <label for="start-hour-field" class="form-label-required">Fecha seleccionada: </label>
                                                        </span>
                                                    </div>
                                                </b-col>
                                            </b-row>
                                            <b-row>
                                                <b-col cols="12" md="6" lg="6" sm="12">
                                                    <div class="field text-start mt-3">
                                                        <span class="p-float-label p-input-icon-right">
                                                            <Calendar id="start-hour-field" :timeOnly="true" hourFormat="12" showTime :manualInput="false" v-model="newAppoint.startHour" />
                                                            <label for="start-hour-field" class="form-label-required">Hora
                                                                inicio</label>
                                                        </span>
                                                    </div>
                                                </b-col>
                                                <b-col cols="12" md="6" lg="6" sm="12">
                                                    <div class="field text-start mt-3">
                                                        <span class="p-float-label p-input-icon-right">
                                                            <Calendar id="start-hour-field" :timeOnly="true" hourFormat="12" />
                                                            <label for="start-hour-field" class="form-label-required">Hora
                                                                fin</label>
                                                        </span>
                                                    </div>
                                                </b-col>
                                            </b-row>
                                            <b-row class="mt-3">
                                                <b-col cols="12" class="d-flex justify-content-end">
                                                    <Button icon="pi pi-times" label="Cancelar" class="p-button-rounded p-button-secondary w-25" />
                                                    <Button label="Registrar" icon="pi pi-plus" class="p-button-rounded button-style w-25" @click="saveAppoint()" />
                                                </b-col>
                                            </b-row>
                                        </div>
                                    </b-col>
                                    <b-col cols="12" md="6" lg="6" sm="12">
                                        <div class="p-1">
                                            <FullCalendar :options="calendarOptions" id="myCustomCalendar">
                                                <template v-slot:eventContent='arg'>
                                                    <b>{{ arg.event.title }}</b>
                                                </template>
                                            </FullCalendar>
                                        </div>
                                    </b-col>
                                </b-row>
                            </div>
                        </b-col>
                    </b-row>
                </panel>
                <Toast/>
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
import ModalSaveAppoint from './ModalSaveAppoint.vue';
import spaceServices from "@/modules/spaces/services/spaces-services"
import appointServices from "@/modules/appointment/services/appoint-services"
import { decrypt, encrypt } from '@/config/security';
import Toast from 'primevue/toast';
import InputText from 'primevue/inputtext/InputText';

export default {
    components: {
        FullCalendar,
        Dropdown,
        Divider,
        Calendar,
        ModalSaveAppoint,
        Toast,
        InputText
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
                eventClick: this.handleEventClick,
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
            selectedSpace: null,
            spaces: [],
            displaySaveModal: false,
            appoints: [],
            newAppoint: {
                startHour: '',
                endHour: ''
            }
        }
    },
    methods: {
        saveAppoint(){
            console.log('Save appoint', this.newAppoint);
        },
        handleDateClick(arg) {
            alert('date click! ' )
        },
        openModalSaveSpeciality() {
            this.displaySaveModal = true;
        },
        async onSpaceSelected(){
            if(this.selectedSpace != null){
                try {
                    this.appoints = []
                    const {id} = JSON.parse(JSON.stringify(this.selectedSpace))
                    const {status, data : {result}} = await appointServices.getAppointmentsBySpace(await encrypt(id))
                    if(status === 200 || status === 201){
                        const appointsDecryoted = JSON.parse(await decrypt(result))
                        if(appointsDecryoted.length > 0){
                            appointsDecryoted.map((appoint) => {
                                const {endHour, startHour, space:{name}} = appoint
                                this.appoints.push({
                                    title: `${name}`,
                                    start: new Date(startHour).toISOString(),
                                    end: new Date(endHour).toISOString()
                                })
                            }) 
                        }else{
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
        }
    }
}
</script>

<style scoped>

.button-style{
    background: #2a715a;
    border: none;
/*     top: 410px; */
}

/* .button-cancel{
    border: none;
    top: 410px;

} */
.button-style:hover{
    background-color: #368368 !important;
}

.form-label-required::after{
    content: " *";
    color: #ff0000;
}
</style>