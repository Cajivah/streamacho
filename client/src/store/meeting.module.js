import { CREATE_ROOM, FETCH_ROOMS, REMOVE_ROOM } from './actions.type';
import { SET_ERROR, SET_MEETINGS } from './mutations.type';
import Vue from 'vue';
import { showErrorToasts } from '../ToastHandler';

const state = {
  meetings: [],
  errors: null,
};

const getters = {
  meetings(state) {
    return state.meetings;
  },
  errors(state) {
    return state.errors;
  }
};

const actions = {
  [CREATE_ROOM]({ commit }, payload) {
    return new Promise(resolve =>
      Vue.axios
        .post('/meetings/rooms', payload, {
          headers: {
            'Content-type': 'application/json'
          }
        })
        .then(data => {
          resolve(data);
        })
        .catch((error) => showErrorToasts(error.response.data))
    );
  },
  [REMOVE_ROOM]({ commit }, payload) {
    return new Promise(resolve => {
      this.$http.delete(`/meetings/rooms/${payload.roomId}`)
        .catch(({ error }) => commit(SET_ERROR, error))
    })
  },
  [FETCH_ROOMS]({ commit }, query) {
    return new Promise(resolve =>
      Vue.axios.get(`meetings/rooms?query=${query}`)
        .then(({ data }) => commit(SET_MEETINGS, data))
        .catch(({ error }) => commit(SET_ERROR, error))
    )
  },
};

const mutations = {
  [SET_ERROR](state, error) {
    state.errors = error;
  },
  [SET_MEETINGS](state, payload) {
    state.meetings = payload.content;
    state.errors = {};
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
