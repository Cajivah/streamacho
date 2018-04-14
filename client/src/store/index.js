import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';
import auth from './auth.module';
import meeting from './meeting.module'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    meeting
  },
  plugins: [createLogger()]
});
