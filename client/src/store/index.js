import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';
import actions from './actions';
import mutations from './mutations';
import state from './defaultStore';

Vue.use(Vuex);

export default new Vuex.Store({
  state,
  getters: {
    isAuthenticated() {
      return vueAuth.isAuthenticated();
    }
  },
  mutations,
  actions,
  plugins: [createLogger()]
});