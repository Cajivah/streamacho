import Vue from 'vue';
import VeeValidate from 'vee-validate';
import VueAuthenticate from 'vue-authenticate';
import axios from 'axios';
import VueAxios from 'vue-axios';
import store from './store';
import { authConfig } from './config';
import router from './router';
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.min.css';
import './common.scss';

import App from './App.vue';

Vue.use(VueAxios, axios);
Vue.use(VeeValidate);
Vue.use(VueAuthenticate, authConfig);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
