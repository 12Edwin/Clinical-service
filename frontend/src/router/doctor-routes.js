import Appoints from "@/modules/appointment/views/Appoints.vue";
import Treatments from "@/modules/treatment/views/Treatments.vue";

export default [
    {
        path: 'appoints',
        name: 'appoints',
        component: Appoints,
        meta:{
            title: 'Citas'
        }
    },
    {
        path: 'treatments',
        name: 'treatments',
        component: Treatments,
        meta:{
            title: 'Tratamientos'
        }
    }
]