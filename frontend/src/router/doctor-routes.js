import Appoints from "@/modules/appointment/views/Appoints.vue";
import Treatments from "@/modules/treatment/views/Treatments.vue";
import ClinicalHistory from "@/modules/expedient/views/ClinicalHistory.vue";
import CreateExpedient from "@/modules/expedient/views/CreateExpedient.vue";
import ModifyExpedient from "@/modules/expedient/views/ModifyExpedient.vue";
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
                path: "new-appoint/:idTreatment",
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
        path: 'modify_expedient/:idExpedient',
        name: 'modify-expedient',
        component: ModifyExpedient,
        meta:{
            title: 'Modificar expediente',
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