import Vue from 'vue';
import VeeValidate from 'vee-validate';
import store from './store';
import router from './router';
import VueAuthenticate from 'vue-authenticate';
import axios from 'axios';
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.min.css';
import './common.scss';

import App from '@/App.vue';

Vue.use(axios);
Vue.use(VeeValidate);
Vue.use(VueAuthenticate, {
  baseUrl: 'http://localhost:3000',
  providers: {
    google: {
      clientId: '',
      redirectUri: 'http://localhost:8080/auth/google_callback'
    },
    facebook: {
      clientId: '',
      redirectUri: 'http://localhost:8080/auth/facebook_callback'
    }
  }
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
