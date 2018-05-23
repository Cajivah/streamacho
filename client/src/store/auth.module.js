import {
  FETCH_LOGGED_USER,
  IS_AUTHENTICATED,
  LOGIN,
  LOGIN_OAUTH,
  LOGOUT,
  REGISTER,
  ACTIVATE_ACCOUNT,
  SAVE_PATH, FETCH_USERS
} from './actions.type';
import qs from 'qs';
import Vue from 'vue';
import { CLEAR_USERS, PURGE_AUTH, SET_AUTH, SET_LOGIN_REDIRECT_PATH, SET_USERS } from './mutations.type';
import { showErrorToasts } from '../ToastHandler';

const state = {
  loggedUser: null,
  afterLoginRedirect: null,
  users: [],
};

const getters = {
  isAuthenticated: state => !!state.loggedUser,
  loggedUser: state => state.loggedUser,
  afterLoginRedirect: state => state.afterLoginRedirect,
  users: state => state.users,
  userNames: state => state.users.map(user => user.username),
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
        .then(({ data }) => {
          commit(SET_AUTH, data);
          resolve(data);
        })
        .catch((error) => showErrorToasts(error.response.data))
    );
  },
  [FETCH_LOGGED_USER]({ commit }) {
    return new Promise((resolve, reject) => {
      Vue.axios.get('/users/accounts/me')
        .then(({ data }) => {
          commit(SET_AUTH, data);
          resolve(data);
        })
        .catch((error) => {
          reject(error.response.data);
        })
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
    })
  },
  [ACTIVATE_ACCOUNT](_, token) {
    return new Promise((resolve, reject) =>
      Vue.axios.patch(`/users/verification?token=${token}`)
        .then(resolve)
        .catch(reject)
    );
  },
  [SAVE_PATH]({ commit }, payload) {
    const { afterLoginRedirect } = payload;
    return new Promise((resolve) => {
      commit(SET_LOGIN_REDIRECT_PATH, afterLoginRedirect);
      resolve();
    })
  },
  [FETCH_USERS]({ commit }, params) {
    return new Promise((resolve, reject) => {
      Vue.axios.get('/users/accounts', { params })
        .then(({ data }) => {
          commit(SET_USERS, data.content);
          resolve(data);
        })
        .catch(reject);
    })
  }
};

const mutations = {
  [SET_AUTH](state, data) {
    state.loggedUser = data;
    state.afterLoginRedirect = null;
    state.error = null;
  },
  [PURGE_AUTH](state) {
    state.loggedUser = null;
    state.afterLoginRedirect = null;
    state.error = null;
  },
  [SET_LOGIN_REDIRECT_PATH](state, data) {
    state.afterLoginRedirect = data
  },
  [SET_USERS](state, data) {
    state.users = data;
    state.error = null;
  },
  [CLEAR_USERS](state) {
    state.users = [];
    state.error = null;
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
