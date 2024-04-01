<template>
  <div>
    <FullCalendar :options="calendarOptions" id="myCustomCalendar">
      <template v-slot:eventContent='arg'>
      <b>{{ arg.event.title }}</b>
    </template>
    </FullCalendar>
  </div>
</template>

<script>
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import esLocale from '@fullcalendar/core/locales/es';
export default {
  components: {
    FullCalendar
  },
  data() {
    return {
      calendarOptions: {
        plugins: [ dayGridPlugin, interactionPlugin ],
        initialView: 'dayGridMonth',
        locale: esLocale,
        events: [
          { title: 'Cita', date: '2024-07-01'},
          { title: 'event 2', date: '2024-07-04' }
        ],
        weekends: false,
        dateClick: this.handleDateClick,
        customButtons: {
          appointment: {
            text: `Nueva cita`,
            click: () => {
              this.$router.push({ name: 'NewAppoint' })
            }
          }
        },
        headerToolbar: {
          start: 'title',
          center: '',
          end: 'appointment today prev,next'
        },
        views: {
          dayGridMonth: {
            titleFormat: { year: 'numeric', month: 'long' } // Ejemplo: Septiembre 2023
          }
        } 

      }
    }
  },
  methods: {
    handleDateClick(arg) {
      alert('date click! ' + arg.dateStr)
    }    
  }
}
</script>

<style>
#myCustomCalendar .fc-button {
  background: #2a715a;
  color: #fff;
  border-color: #2a715a;;
}

#myCustomCalendar .fc-button:hover {
  background-color: #368368;
  border-color: #368368;
}
</style>