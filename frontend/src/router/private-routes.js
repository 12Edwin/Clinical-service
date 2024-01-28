import adminRoutes from "@/router/admin-routes";
import doctorRoutes from "@/router/doctor-routes";
import Perfil from "@/modules/user/views/Perfil.vue";
import ClinicalHistory from "@/modules/expedient/views/ClinicalHistory.vue";

export default [
    {
        path: '',
        props: { role: "Administrador" },
        component: ()=> import(/* webpackChunkName "admin" */"@/layouts/PrivateLayout.vue"),
        meta:{
        },
        children:[
            ...adminRoutes.map(route => {
                route.meta.requireAuth = false
                return {...route}
            }),
            ...doctorRoutes.map(route => {
                route.meta.requireAuth = true
                return {...route}
            }),
            {
                path: '/clinicalHistory',
                name: 'clinical-history',
                component: ClinicalHistory,
                meta:{
                    title: 'Historial clínico'
                }
            },
            {
                path: '/perfil',
                name: 'perfil',
                component: Perfil,
                meta:{
                    title: 'Perfil'
                }
            }
        ]
    },

]