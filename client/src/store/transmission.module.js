import {JOIN_TRANSMISSION, DESTROY_TRANSMISSION, CREATE_TRANSMISSION, ERASE_TRANSMISSION} from './actions.type';
import { PURGE_TRANSMISSION, SET_ERROR, SET_TRANSMISSION } from './mutations.type';
import Vue from 'vue';

const state = {
  transmission: null,
};

const getters = {
  transmission: state => state.transmission,
};

const actions = {
  [CREATE_TRANSMISSION]({ commit }, payload) {
    const { roomId } = payload;
    return new Promise((resolve, reject) =>
      Vue.axios.post(`/meetings/rooms/${roomId}/sessions`)
        .then(({ data }) => {
          commit(SET_TRANSMISSION, data);
          resolve(data);
        })
        .catch(({ error }) => {
          commit(SET_ERROR, error);
          reject(error);
        })
    );
  },
  [JOIN_TRANSMISSION]({ commit }, payload) {
    const { roomId } = payload;
    return new Promise((resolve, reject) =>
      Vue.axios.post(`/meetings/rooms/${roomId}/join`)
        .then(({ data }) => {
          commit(SET_TRANSMISSION, data);
          resolve(data);
        })
        .catch(({ error }) => {
          commit(SET_ERROR, error);
          reject(error);
        })
    );
  },
  [DESTROY_TRANSMISSION]({ commit }, payload) {
    const { roomId } = payload;
    return new Promise((resolve, reject) => {
      Vue.axios.delete(`/meetings/rooms/${roomId}/sessions`)
        .then(() => {
          commit(PURGE_TRANSMISSION);
          resolve();
        })
        .catch(({ error }) => {
          commit(SET_ERROR, error);
          reject(error);
        });
    })
  },
  [ERASE_TRANSMISSION]({ commit }) {
    return new Promise(resolve => {
      commit(PURGE_TRANSMISSION);
      resolve();
    })
  }
};

const mutations = {
  [SET_TRANSMISSION](state, data) {
    state.transmission = data;
    state.errors = null;
  },
  [PURGE_TRANSMISSION](state) {
    state.transmission = null;
    state.errors = null;
  },
  [SET_ERROR](state, error) {
    state.errors = error;
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
