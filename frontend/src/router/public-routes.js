import HomeView from "@/views/HomeView.vue";
import Login from "@/views/Login.vue";
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
        path: 'login',
        name: 'login',
        component: Login,
        meta:{
            title: 'Iniciar sesión'
        }
    },

]