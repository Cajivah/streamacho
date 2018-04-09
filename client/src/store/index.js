import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';
import state from './defaultStore';
import qs from 'qs';

Vue.use(Vuex);

export default new Vuex.Store({
  state,
  getters: {
    isAuthenticated() {
      return vueAuth.isAuthenticated();
    }
  },
  mutations: {
    createMeeting(state, payload) {
      state.meetings.push(payload.meeting);
    },
    removeMeeting(state, payload) {
      state.meetings.splice(state.meetings.indexOf(payload.meeting), 1);
    },
    initMeetings(state, payload) {
      state.meetings = payload.meetings;
    },
    isAuthenticated(state, payload) {
      state.isAuthenticated = payload.isAuthenticated;
    },
    login(state, payload) {
      state.authentication.isLogged = payload.isLogged;
      state.authentication.loggedUser = payload.loggedUser;
    },
    register(state, payload) {}
  },
  actions: {
    createRoom({ commit }, payload) {
      return Promise.resolve(Vue.axios.post('/api/meetings/rooms', payload));
    },
    removeMeeting(context, payload) {
      Vue.axios
        .delete('/api/meetings/rooms')
        .then(response => {
          context.commit({
            type: 'removeMeeting',
            meeting: payload
          });
        })
        .catch(error => error);
    },
    initMeetings(context, payload) {
      context.commit({
        type: 'initMeetings',
        meetings: payload
      });
    },
    loginOAuth(context, payload) {
      vueAuth.login(payload.user, payload.requestOprions).then(response => {
        context.commit('isAuthenticated', {
          isAuthenticated: vueAuth.isAuthenticated()
        });
      });
    },
    login({ commit }, payload) {
      return Promise.resolve(
        Vue.axios.post('/users/login', qs.stringify(payload), {
          headers: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        })
      );
    },
    register({ commit }, payload) {
      return Promise.resolve(
        Vue.axios.post('/users/accounts', payload, {
          headers: {
            'Content-type': 'application/json'
          }
        })
      );
    }
  },
  plugins: [createLogger()]
});
