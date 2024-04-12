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
            },
          }
        },
        headerToolbar: {
          start: 'title',
          center: '',
          end: 'appointment today prev,next'
        },
        views: {
          dayGridMonth: {
            titleFormat: { year: 'numeric', month: 'long' }
          }
        },
      },
      isLoading: false,
      appoints: [],
      
    }
  },
  methods: {
    handleDateClick(arg) {
      alert('date click! ' + arg.dateStr)
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
        }    
  }
}
</script>

<style>



</style>