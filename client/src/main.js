import Vue from 'vue';
import VeeValidate from 'vee-validate';
import VueAuthenticate from 'vue-authenticate';
import axios from 'axios';
import VueAxios from 'vue-axios';
import store from './store';
import router from './router';
import Hub from './Hub.vue';

import httpConfigurer from './config/httpConfigurer';
import thirtPartyConfigurer from './config/thirtPartyConfigurer';

// -------STYLES-------
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.min.css';
import './common.scss';


Vue.use(VueAxios, axios, httpConfigurer);
Vue.use(VeeValidate);
Vue.use(VueAuthenticate, thirtPartyConfigurer);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(Hub)
}).$mount('#app');
