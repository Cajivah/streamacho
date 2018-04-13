import {FETCH_LOGGED_USER, IS_AUTHENTICATED, LOGIN, LOGIN_OAUTH, REGISTER} from "./actions.type";
import qs from "qs";
import Vue from 'vue';
import {SET_AUTH, SET_ERROR} from "./mutations.type";

const state = {
  authentication: {
    isAuthenticated: false,
    loggedUser: {},
    errors: null,
  },
};

const getters = {
  isAuthenticated(state) {
    return state.isAuthenticated;
  },
  loggedUser(state) {
    return state.loggedUser;
  }
};

const actions = {
  [LOGIN_OAUTH](context, payload) {
    vueAuth.login(payload.user, payload.requestOptions).then(response => {
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
        .then((response) => resolve(response))
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
        .catch((error) => commit(SET_ERROR, error))
    })
  },
  [REGISTER]({ commit }, payload) {
    return new Promise((resolve) =>
      Vue.axios.post('/users/accounts', payload)
        .catch(({error}) => commit(SET_ERROR, error))
    );
  }
};

const mutations = {
  [SET_ERROR] (state, error) {
    state.errors = error;
  },
  [SET_AUTH] (state, data) {
    state.isAuthenticated = true;
    state.loggedUser = data;
    state.errors = {};
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
