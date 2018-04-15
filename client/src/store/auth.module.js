import { FETCH_LOGGED_USER, IS_AUTHENTICATED, LOGIN, LOGIN_OAUTH, LOGOUT, REGISTER } from "./actions.type";
import qs from "qs";
import Vue from 'vue';
import { PURGE_AUTH, SET_AUTH } from "./mutations.type";
import { showErrorToasts } from "../ToastHandler";

const state = {
  loggedUser: null,
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
        .then(data => {
          commit(SET_AUTH, data);
          resolve(data);
        })
        .catch((error) => showErrorToasts(error.response.data))
    );
  },
  [FETCH_LOGGED_USER]({ commit }) {
    return new Promise((resolve) => {
      Vue.axios.get('/users/accounts/me')
        .then(data => {
          commit(SET_AUTH, data);
          resolve(data);
        })
        .catch((error) => error.response.data)
    })
  },
  [REGISTER]({ commit }, payload) {
    return new Promise((resolve) => {
      Vue.axios.post('/users/accounts', payload)
        .catch((error) => showErrorToasts(error.response.data));
      resolve();
    }
    );
  },
  [LOGOUT]({ commit }) {
    return new Promise((resolve) => {
      Vue.axios.post('/users/logout')
        .then(() => {
          commit(PURGE_AUTH);
          resolve();
        })
        .catch((error) => error.response.data);
      resolve();
    })
  }
};

const mutations = {
  [SET_AUTH](state, data) {
    state.loggedUser = data;
    state.error = null;
  },
  [PURGE_AUTH](state) {
    state.loggedUser = null;
    state.error = null;
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
