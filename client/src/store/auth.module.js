import {FETCH_LOGGED_USER, IS_AUTHENTICATED, LOGIN, LOGIN_OAUTH, LOGOUT, REGISTER} from "./actions.type";
import qs from "qs";
import Vue from 'vue';
import {PURGE_AUTH, SET_AUTH, SET_ERROR} from "./mutations.type";

const state = {
    loggedUser: null,
    errors: null,
};

const getters = {
  isAuthenticated: state => !!state.loggedUser,
  loggedUser: state => state.loggedUser
};

const actions = {
  [LOGIN_OAUTH](context, payload) {
    vueAuth.login(payload.user, payload.requestOptions)
      .then(response => {
        context.commit(IS_AUTHENTICATED, {
          isAuthenticated: vueAuth.isAuthenticated()
        });
      });
  },
  [LOGIN]({ commit }, payload) {
    return new Promise(resolve =>
      Vue.axios
        .post('/users/login', qs.stringify(payload), {
          headers: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        })
        .then(({data}) => {
          commit(SET_AUTH, data);
          resolve(data);
        })
        .catch((error) => commit(SET_ERROR, error))
    );
  },
  [FETCH_LOGGED_USER]({commit}) {
    return new Promise((resolve) => {
      Vue.axios.get('/users/accounts/me')
        .then(({data}) => {
          commit(SET_AUTH, data);
          resolve(data);
        })
        .catch((error) => {
          commit(SET_ERROR, error);
        })
    })
  },
  [REGISTER]({ commit }, payload) {
    return new Promise((resolve) => {
      Vue.axios.post('/users/accounts', payload)
        .catch((error) => commit(SET_ERROR, error));
      resolve();
      }
    );
  },
  [LOGOUT]({commit}) {
    return new Promise((resolve) => {
      Vue.axios.post('/users/logout')
        .then(() => commit(PURGE_AUTH))
        .catch((error) => commit(SET_ERROR, error));
      resolve();
    })
  }
};

const mutations = {
  [SET_ERROR] (state, error) {
    state.errors = error;
  },
  [SET_AUTH] (state, data) {
    state.loggedUser = data;
    state.errors = {};
  },
  [PURGE_AUTH] (state) {
    state.loggedUser = null;
    state.errors = {};
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
