import Appoints from "@/modules/appointment/views/Appoints.vue";
import Treatments from "@/modules/treatment/views/Treatments.vue";
import ClinicalHistory from "@/modules/expedient/views/ClinicalHistory.vue";
export default [
    {
        path: 'appoints',
        name: 'appoints',
        component: Appoints,
        meta:{
            title: 'Citas',
            role: "doctor"
        }
    },
    {
        path: 'treatments',
        name: 'treatments',
        component: Treatments,
        meta:{
            title: 'Tratamientos',
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