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
                    title:'Doctores',
                    role : 'admin'
                }
            },
            {
                path: 'register',
                name: 'register',
                component: SaveDoctor,
                meta: {
                    title:'Registro',
                    role : 'admin'
                }
            }
        ]
    },
    {
        path: 'areas',
        name: 'areas',
        component: Area,
        meta: {
            title:'Áreas',
            role : 'admin'
        }
    },
    {
        path: 'specialities',
        name: 'specialities',
        component: Specialities,
        meta: {
            title:'Especialidades',
            role : 'admin'
        }
    },

]