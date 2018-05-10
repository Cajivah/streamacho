import Vue from 'vue';
import VeeValidate from 'vee-validate';
import VueAuthenticate from 'vue-authenticate';
import VueAxios from 'vue-axios';
import Toasted from 'vue-toasted';
import VueScrollTo from 'vue-scrollto';
import VueTippy from 'vue-tippy'
import store from './store';
import router from './router';
import App from './App.vue';

import httpConfigurer from './config/httpConfigurer';
import thirdPartyConfigurer from './config/thirdPartyConfigurer';

// -------STYLES-------
import './style.scss';
import 'font-awesome/css/font-awesome.min.css';
import { validatorDictionary } from './validatorDictionary.js';

const validatorConfig = {
  dictionary: validatorDictionary
};
Vue.use(VueAxios, httpConfigurer);
Vue.use(VeeValidate, validatorConfig);
Vue.use(VueAuthenticate, thirdPartyConfigurer);
Vue.use(VueTippy, {
  arrow: true,
  flipDuration: 0,
  popperOptions: {
    modifiers: {
      preventOverflow: {
        enabled: false
      }
    }
  }
});

Vue.use(VueScrollTo, {
  container: 'body',
  duration: 600,
  easing: 'ease',
  offset: 0,
  cancelable: true,
  onStart: false,
  onDone: false,
  onCancel: false,
  x: false,
  y: true
});

Vue.use(Toasted);
Vue.toasted.register('error_toast',
  (payload) => {
    if(! payload.message) {
      return 'Oops.. Something Went Wrong..'
    }
    return 'Oops...' + payload.message;
  },
  {
    type : 'error',
    icon : 'fa-exclamation-circle',
    position: 'top-center',
    iconPack: 'fontawesome',
  }
);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
