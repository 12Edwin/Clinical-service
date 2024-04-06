import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Button from 'primevue/button';
import Panel from 'primevue/panel';
import InputText from "primevue/inputtext/InputText";

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

Vue.component('InputText', InputText)
Vue.component('Panel', Panel)
Vue.component('Button', Button)

import DataView from 'primevue/dataview';
Vue.component('DataView', DataView)

import Toolbar from 'primevue/toolbar';
Vue.component('Toolbar', Toolbar)

import Card from 'primevue/card';
Vue.component('Card', Card)
import ConfirmationService from 'primevue/confirmationservice';
Vue.use(ConfirmationService);
Vue.config.productionTip = false

import VueCompositionAPI from '@vue/composition-api'
Vue.use(VueCompositionAPI)

import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)

import Tooltip from 'primevue/tooltip';
Vue.directive('tooltip', Tooltip);

import ToastService from 'primevue/toastservice';

Vue.use(ToastService);

import VueMoment from 'vue-moment'
Vue.use(VueMoment)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')