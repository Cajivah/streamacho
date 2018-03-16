import Vue from 'vue';
import VeeValidate from 'vee-validate';
import store from './store';
import router from './router';
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.min.css';

import App from '@/App.vue';

Vue.use(VeeValidate);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
