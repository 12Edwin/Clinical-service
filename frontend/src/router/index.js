import Vue from 'vue'
import VueRouter from 'vue-router'
import publicRoutes from "@/router/public-routes";
import privateRoutes from "@/router/private-routes";
import utils from '@/kernel/utils';
Vue.use(VueRouter)

const routes = [
    {
        path:'',
        redirect: '/home'
    },
    {
        path: '/',
        component: {
            render (c){
                return c("router-view")
            }
        },
        children:[
            ...publicRoutes.map(route => {
                route.meta.requireAuth = false
                return {...route}
            }),
            ...privateRoutes.map(route => {
                route.meta.requireAuth = true
                return {...route}
            })
        ]
    },
    {
        path: '/*',
        name: '404',
        component: ()=> import('@/views/404.vue')
    },
    {
        name: "unautorized",
        path: "/unautorized",
        component: () => import("@/components/Unautorized.vue"),
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

 router.beforeEach(async (to, from, next)=> {
    const publicPages = ['/login','/recovery-password', "/our_doctors", "/services", "/home", "/blog"];
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = utils.getToken()
    
    if (authRequired && !loggedIn) {
        return next('/login')
    }
    if(loggedIn){
        const role = await utils.getRoleNameBytoken()

        if(role !== undefined && role !== null && role !== ""){
            if(to.meta && to.meta.role && to.meta.role.toString().toLowerCase() !== role.toString().toLowerCase()){
                return next("/unautorized")
            }
        }else{
            return next("/login")
        }
        next();
    }
    if(loggedIn && to.path.toString().toLowerCase() === "/login"){
        return next("/perfil")
    }
    next()
})


export default router