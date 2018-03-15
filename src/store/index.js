import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authentication: {
      isLogged: false,
      loggedUser: {}
    },
    meetings: []
  },
  mutations: {
    createMeeting(state, payload) {
      state.meetings.push(payload.product);
    },
    removeMeeting(state, payload) {
      state.meetings.splice(state.meetings.indexOf(payload), 1);
    },
    initMeetings(state, payload) {
      state.meetings.concat(payload.meetings);
    },
    registerUser(state, payload) {},
    loginSuccess(state, payload) {
      state.authentication.isLogged = payload.isLogged;
      state.authentication.loggedUser = payload.loggedUser;
    },
    loginFailed(state, payload) {
      state.authentication.isLogged = payload.isLogged;
    },
    registerSuccess(state, payload) {

    },
    registerFailed(state, payload) {}
  },
  actions: {
    createMeeting(store, payload) {
      store.commit({
        type: 'createMeeting',
        meeting: payload
      });
    },
    removeMeeting(store, payload) {
      store.commit({
        type: 'removeMeeting',
        meeting: payload
      });
    },
    initMeetings(store, payload) {
      store.commit({
        type: 'initMeetings',
        meetings: payload
      });
    },
    loginSuccess(state, payload) {
      store.commit({
        type: 'loginSuccess',
        authentication: {
          isLogged: payload.isLogged,
          loggedUser: payload.loggedUser
        }
      });
    },
    loginFailed(state, payload) {
      store.commit({
        type: 'loginFailed',
        authentication: {
          isLogged: payload.isLogged
        }
      });
    },
    registerSuccess(state, payload) {
      store.commit({
        type: 'registerSuccess'
      });
    },
    registerFailed(state, payload) {
      store.commit({
        type: 'registerFailed'
      });
    }
  },
  plugins: [createLogger()]
});