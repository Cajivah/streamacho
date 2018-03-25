import Vue from 'vue';
import { VueAuthenticate } from 'vue-authenticate';

export const vueAuth = new VueAuthenticate(Vue.prototype.$http, {
    baseUrl: 'http://localhost:8000'
});