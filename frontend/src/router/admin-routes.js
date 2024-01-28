import Doctors from "@/modules/doctor/views/Doctors.vue";
import Area from "@/modules/area/views/Area.vue";

export default [
    {
        path: 'doctors',
        name: 'doctors',
        component: Doctors,
        meta: {
            title:'Doctores'
        }
    },
    {
        path: 'areas',
        name: 'areas',
        component: Area,
        meta: {
            title:'Áreas'
        }
    }
]