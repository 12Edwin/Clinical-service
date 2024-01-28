import Vue from 'vue'
import VueRouter from 'vue-router'
import publicRoutes from "@/router/public-routes";
import privateRoutes from "@/router/private-routes";

Vue.use(VueRouter)

const routes = [
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
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next)=>{
  //let permission = false;
  if (!to.matched.some((noAuth) => noAuth.meta.requireAuth)) {
    next();
  } else {
    next({ name: "login" });
  }
})

export default router
