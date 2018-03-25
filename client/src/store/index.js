import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authentication: {
      isLogged: false,
      isAuthenticated: false,
      loggedUser: {}
    },
    meetings: []
  },
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
    registerUser(state, payload) { },
    loginSuccess(state, payload) {
      state.authentication.isLogged = payload.isLogged;
      state.authentication.loggedUser = payload.loggedUser;
    },
    loginFailed(state, payload) {
      state.authentication.isLogged = payload.isLogged;
    },
    registerSuccess(state, payload) {

    },
    registerFailed(state, payload) { }
  },
  actions: {
    createMeeting(context, payload) {
      context.commit({
        type: 'createMeeting',
        meeting: payload
      });
    },
    removeMeeting(context, payload) {
      context.commit({
        type: 'removeMeeting',
        meeting: payload
      });
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
    loginSuccess(context, payload) {
      context.commit({
        type: 'loginSuccess',
        authentication: {
          isLogged: payload.isLogged,
          loggedUser: payload.loggedUser
        }
      });
    },
    loginFailed(context, payload) {
      context.commit({
        type: 'loginFailed',
        authentication: {
          isLogged: payload.isLogged
        }
      });
    },
    registerSuccess(context, payload) {
      context.commit({
        type: 'registerSuccess'
      });
    },
    registerFailed(context, payload) {
      context.commit({
        type: 'registerFailed'
      });
    }
  },
  plugins: [createLogger()]
});