import { CREATE_ROOM, FETCH_ROOMS, FETCH_SELECTED_ROOM, REMOVE_ROOM } from './actions.type';
import { APPEND_ROOMS, CLEAR_ROOMS, SET_ERROR, SET_MEETINGS, SET_SELECTED_ROOM } from './mutations.type';
import Vue from 'vue';
import { showErrorToasts } from '../ToastHandler';

const state = {
  rooms: [],
  selectedRoom: null,
  errors: null,
};

const getters = {
  rooms: state => state.rooms,
  selectedRoom: state => state.selectedRoom,
};

const actions = {
  [CREATE_ROOM](_, payload) {
    return new Promise(resolve =>
      Vue.axios
        .post('/meetings/rooms', payload, {
          headers: {
            'Content-type': 'application/json'
          }
        })
        .then(resolve)
        .catch((error) => showErrorToasts(error.response.data))
    );
  },
  [REMOVE_ROOM]({ commit }, payload) {
    return new Promise(resolve => {
      this.$http.delete(`/meetings/rooms/${payload.roomId}`)
        .catch(({ error }) => commit(SET_ERROR, error))
    })
  },
  [FETCH_ROOMS]({ commit }, params) {
    return new Promise((resolve, reject) =>
      Vue.axios.get('meetings/rooms', { params })
        .then(({ data }) => {
          commit(APPEND_ROOMS, data);
          resolve(data);
        })
        .catch(({ error }) => {
          commit(SET_ERROR, error);
          reject();
        })
    )
  },
  [FETCH_SELECTED_ROOM]({ commit }, { roomId }) {
    return new Promise(( resolve, reject ) =>
      Vue.axios.get(`meetings/rooms/${ roomId }`)
        .then((response) => {
          const data = response.data;
          commit(SET_SELECTED_ROOM, data);
          resolve(data);
        })
        .catch(({ error }) => {
          showErrorToasts(error);
          reject(error);
        })
    );
  },
};

const mutations = {
  [SET_ERROR](state, error) {
    state.errors = error;
  },
  [APPEND_ROOMS](state, payload) {
    state.rooms = [...state.rooms, ...payload.content];
    state.errors = {};
  },
  [CLEAR_ROOMS](state) {
    state.rooms = [];
    state.errors = {};
  },
  [SET_SELECTED_ROOM](state, data) {
    state.selectedRoom = data;
  },
};

export default {
  state,
  actions,
  mutations,
  getters
}
