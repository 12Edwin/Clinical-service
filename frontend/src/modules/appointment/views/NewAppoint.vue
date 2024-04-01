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
                                    <b-col class="mb-2" cols="12">
                                        <div class="field text-start">
                                            <label for="space-selector" class="form-label-required">Espacio:</label>
                                            <Dropdown id="space-selector" class="text-center" v-model="selectedSpace" :options="spaces"
                                                optionLabel="name" placeholder="Seleccione un espacio" />
                                        </div>
                                    </b-col>
                                </b-row>
                                <b-row>
                                    <b-col cols="12" sm="12">
                                        <div class="p-1">
                                            <FullCalendar :options="calendarOptions" id="myCustomCalendar">
                                            </FullCalendar>
                                        </div>
                                    </b-col>
                                </b-row>
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
import ModalSaveAppoint from './ModalSaveAppoint.vue';
export default {
    components: {
        FullCalendar,
        Dropdown,
        Divider,
        Calendar,
        ModalSaveAppoint
    },
    name: 'NewAppoint',
    data(){
        return{ 
            calendarOptions: {
                plugins: [ dayGridPlugin, interactionPlugin ],
                initialView: 'dayGridMonth',
                locale: esLocale,
                events: [
                    { title: 'Cita', date: '2024-03-01', backgroundColor: '#2a715a', borderColor: '#2a715a', textColor: '#ffffff'},
                    { title: 'event 2', date: '2024-02-29' },
                    {
                        title: 'Revisión Médica',
                        start: '2024-03-01T09:00:00',
                        end: '2024-03-01T10:00:00'
                    },
                    // Evento al mediodía
                    {
                        title: 'Consulta Nutricional',
                        start: '2024-03-01T12:00:00',
                        end: '2024-03-01T13:00:00'
                    },
                    // Evento en la tarde
                    {
                        title: 'Cita de Seguimiento',
                        start: '2024-03-01T15:00:00',
                        end: '2024-03-01T16:00:00'
                    },
                ],
                selectable: true,
                weekends: false,
                dateClick: this.handleDateClick,
                headerToolbar: {
                    start: 'title',
                    center: '',
                    end: 'today prev,next'
                },
                views: {
                    dayGridMonth: {
                        titleFormat: { year: 'numeric', month: 'long' } // Ejemplo: Septiembre 2023
                    }
                } 
            },
            selectedSpace: null,
            spaces: [
                { name: 'Espacio 1', code: '1' },
                { name: 'Espacio 2', code: '2' },
                { name: 'Espacio 3', code: '3' },
                { name: 'Espacio 4', code: '4' }
            ],
            calendarEvents: [
                { title: 'Evento 1', start: '2024-03-01' },
                { title: 'Evento 2', start: '2024-03-02', end: '2024-03-03' },
            ],
            displaySaveModal: false
        }
    },
    methods: {
        handleDateClick(arg) {
            alert('date click! ' , arg.dateStr)
        },
        openModalSaveSpeciality() {
            this.displaySaveModal = true;
        },
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