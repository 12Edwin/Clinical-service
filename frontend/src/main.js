import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

import 'primevue/resources/themes/saga-blue/theme.css'       //theme
import 'primevue/resources/primevue.min.css'                 //core css
import 'primeicons/primeicons.css'                           //icons
import PrimeVue from 'primevue/config';
Vue.use(PrimeVue);

import Button from 'primevue/button';
import Panel from 'primevue/panel';
import InputText from "primevue/inputtext/InputText";

Vue.component('InputText', InputText)
Vue.component('Panel', Panel)
Vue.component('Button', Button)

Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')