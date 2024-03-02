import HomeView from "@/views/HomeView.vue";
import Login from "@/views/Login.vue";
import FreeDoctorsVue from "@/modules/doctor/views/FreeDoctors.vue";
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

]