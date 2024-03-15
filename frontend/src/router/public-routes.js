import HomeView from "@/views/HomeView.vue";
import Login from "@/views/Login.vue";
import FreeDoctorsVue from "@/modules/doctor/views/FreeDoctors.vue";
import Service from "@/modules/service/views/Service.vue"
export default [
    {
        path: 'home',
        name: 'home',
        component: HomeView,
        meta:{
            title: 'Home'
        }
    },
    {
        path: 'our_doctors',
        name: 'our_doctors',
        component: FreeDoctorsVue,
        meta:{
            title: 'Doctors'
        }
    },
    {
        path: 'login',
        name: 'login',
        component: Login,
        meta:{
            title: 'Iniciar sesión'
        }
    },
    {
        path: 'services',
        name: 'services',
        component: Service,
        meta:{
            title: 'Servicios Clinicos'
        }
    },

]