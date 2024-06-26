import Doctors from "@/modules/doctor/views/Doctors.vue";
import Specialities from "@/modules/speciality/views/Specialities.vue"
import SaveDoctor from "@/modules/doctor/views/SaveDoctor.vue"
import ServicePrivate from "@/modules/service-private/views/ServicePrivate.vue"
import Pathology from "@/modules/pathology/views/Pathology.vue";
import Space from "@/modules/spaces/views/Space.vue";
export default [
    {
        path: 'doctors',
        name: 'doctors',
        meta: {
            role: "admin"
        },
        component: {
            render(c){
                return c("router-view")
            }
        },
        children:[
            {
                path: '',
                name: 'doctors',
                component: Doctors,
                meta: {
                    title:'Doctores',
                    role: "admin"

                }
            },
            {
                path: 'register',
                name: 'register',
                component: SaveDoctor,
                meta: {
                    title:'Registro',
                    role: "admin"
                }
            }
        ]
    },
    {
        path: 'specialities',
        name: 'specialities',
        component: Specialities,
        meta: {
            title:'Especialidades',
        }
    },
    {
        path: 'privateService',
        name: 'service',
        component: ServicePrivate,
        meta: {
            title:'Servicios Clinicos',
        }
    },
    {
        path: 'pathology',
        name: 'pathology',
        component: Pathology,
        meta: {
            title:'Patologias',
        }
    },
    {
        path: 'spaces',
        name: 'space',
        component: Space,
        meta: {
            title:'Espacios',
        }
    },
]