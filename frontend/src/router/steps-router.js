import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);
export default new Router({
    mode: 'history',
    routes: [
      { path: '/generate-code', component: ()=> import("../modules/password-recovery/views/Phone.vue") },
      { path: '/valid-code', component: ()=> import("../modules/password-recovery/views/ValidCode.vue") },
    ],
  });