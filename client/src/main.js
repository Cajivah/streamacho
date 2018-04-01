import Vue from 'vue';
import VeeValidate from 'vee-validate';
import VueAuthenticate from 'vue-authenticate';
import VueAxios from 'vue-axios';
import store from './store';
import router from './router';
import App from './App.vue';

import httpConfigurer from './config/httpConfigurer';
import thirtPartyConfigurer from './config/thirtPartyConfigurer';

// -------STYLES-------
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.min.css';
import './common.scss';


Vue.use(VueAxios, httpConfigurer);
Vue.use(VeeValidate);
Vue.use(VueAuthenticate, thirtPartyConfigurer);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
