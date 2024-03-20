import Doctors from "@/modules/doctor/views/Doctors.vue";
import Area from "@/modules/area/views/Area.vue";
import Specialities from "@/modules/speciality/views/Specialities.vue"
import SaveDoctor from "@/modules/doctor/views/SaveDoctor.vue"
export default [
    {
        path: 'doctors',
        name: 'doctors',
        component: {
            render(c){
                return c("router-view")
            }
        },
        meta: { title: ""},
        children:[
            {
                path: '',
                name: 'doctors',
                component: Doctors,
                meta: {
                    title:'Doctores'
                }
            },
            {
                path: 'register',
                name: 'register',
                component: SaveDoctor,
                meta: {
                    title:'Registro'
                }
            }
        ]
    },
    {
        path: 'areas',
        name: 'areas',
        component: Area,
        meta: {
            title:'Áreas'
        }
    },
    {
        path: 'specialities',
        name: 'specialities',
        component: Specialities,
        meta: {
            title:'Especialidades'
        }
    },

]