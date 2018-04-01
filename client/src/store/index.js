import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';
import state from './defaultStore';

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
    register(state, payload) { }
  },
  actions: {
    createRoom({ commit }, payload) {
      return new Promise((resolve, reject) => {
        Vue.http.post('/api/meetings/rooms', payload)
          .then(response => {
            commit('createMeeting', response);
            resolve();
          });
      }).catch(error => reject(error));
    },
    removeMeeting(context, payload) {
      this.$http.delete('/api/meetings/rooms').then(response => {
        context.commit({
          type: 'removeMeeting',
          meeting: payload
        });
      }).catch(error => error);
    },
    initMeetings(context, payload) {
      context.commit({
        type: 'initMeetings',
        meetings: payload
      });
    },
    loginOAuth(context, payload) {
      vueAuth.login(payload.user, payload.requestOprions)
        .then((response) => {
          context.commit('isAuthenticated', {
            isAuthenticated: vueAuth.isAuthenticated()
          });
        });
    },
    login({ commit }, payload) {
      return new Promise((resolve, reject) => {
        Vue.http.post('/users/login', payload, {
          headers: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        })
          .then(response => {
            commit('login', {
              isLogged: true,
              loggedUser: response
            });
            resolve(response);
          })
          .catch(error => reject(error));
      });
    },
    register({ commit }, payload) {
      return new Promise((resolve, reject) => {
        Vue.http.post('/users/register', payload, {
          headers: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        })
          .then(response => {
            commit('register');
            resolve(response);
          })
          .catch(error => reject(error));
      })
    }
  },
  plugins: [createLogger()]
});