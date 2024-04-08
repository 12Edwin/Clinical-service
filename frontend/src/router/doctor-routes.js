import Appoints from "@/modules/appointment/views/Appoints.vue";
import Treatments from "@/modules/treatment/views/Treatments.vue";
import ClinicalHistory from "@/modules/expedient/views/ClinicalHistory.vue";
import CreateExpedient from "@/modules/expedient/views/CreateExpedient.vue";
export default [
    {
        path: 'appoints',
        name: 'appoints',
        meta:{
            title: 'Citas',
            role: "doctor"
        },
        component: {
            render(c){
                return c("router-view")
            }
        },
        meta: { title: ""},
        children:[
            {
                path: '',
                name: 'appoints',
                component: Appoints,
                meta: {
                    title:'Citas',
                    role: "doctor"
                }
            },
            {
                path: "new-appoint",
                name: "NewAppoint",
                component: () => import("@/modules/appointment/views/NewAppoint.vue"),
                meta: {
                    title: "Nueva cita",
                    role: "doctor"
                }
            }
        ]
    },
    {
        path: 'treatments/:idExpedient',
        name: 'treatments',
        component: Treatments,
        meta:{
            title: 'Tratamientos',
            role: "doctor"
        }
    },
    {
        path: 'create_expedient',
        name: 'create-expedient',
        component: CreateExpedient,
        meta:{
            title: 'Crear expediente',
            role: "doctor"
        }
    },
    {
        path: 'clinicalHistory',
        name: 'clinical-history',
        component: ClinicalHistory,
        meta:{
            title: 'Historial clínico',
            role: "doctor"
        }
    },
]