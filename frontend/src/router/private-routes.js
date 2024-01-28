import adminRoutes from "@/router/admin-routes";
import doctorRoutes from "@/router/doctor-routes";
import Perfil from "@/modules/user/views/Perfil.vue";

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
            })
        ]
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