import {CREATE_ROOM, FETCH_ROOMS, REMOVE_ROOM} from "./actions.type";
import {SET_ERROR, SET_MEETINGS} from "./mutations.type";

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
  [CREATE_ROOM] ({commit}, payload) {
    return new Promise(resolve =>
      this.$http.post('/meetings/rooms', payload))
      .catch(({error}) => commit(SET_ERROR, error));
  },
  [REMOVE_ROOM] ({commit}, payload) {
    return new Promise(resolve => {
      this.$http.delete(`/meetings/rooms/${payload.roomId}`)
        .catch(({error}) => commit(SET_ERROR, error))
    })
  },
  [FETCH_ROOMS] ({commit}, payload) {
    return new Promise(resolve =>
      this.$http.get('meetings/rooms')
        .then(data => commit(SET_MEETINGS, data))
        .catch(error => commit(SET_ERROR, error))
    )
  }
};

const mutations = {
  [SET_ERROR](state, error) {
    state.errors = error;
  },
  [SET_MEETINGS](state, data) {
    state.meetings = data.meetings;
    state.errors = {};
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
