import Vue from 'vue';
import {
  DESTROY_CHAT,
  FETCH_CHAT_MESSAGES,
  SEND_CHAT_MESSAGE,
  SEND_TO_TOPIC,
  SUBSCRIBE_CHAT,
  SUBSCRIBE_TOPIC
} from './actions.type';
import { APPEND_CHAT_MESSAGE, CLEAR_CHAT_MESSAGES, SET_CHAT_MESSAGES, SET_ERROR, UNSUBSCRIBE } from './mutations.type';
import { showErrorToasts } from '../ToastHandler';

const state = {
  chatMessages: [],
  errors: null,
};

const getters = {
  chatMessages: state => state.chatMessages,
};

const actions = {
  [FETCH_CHAT_MESSAGES]({ commit }, { chatId }) {
    return new Promise((resolve, reject) => {
      const config = {
        params: {
          size: 10000,
        }
      };
      Vue.axios.get(`/chat-db/chat/${ chatId }`, config)
        .then(({ data }) => {
          commit(SET_CHAT_MESSAGES, data);
          resolve(data);
        })
        .catch(({ error }) => {
          commit(SET_ERROR, error);
          showErrorToasts(error);
          reject(error);
        })
    })
  },
  [SUBSCRIBE_CHAT]({ commit, dispatch }, { chatId }) {
    dispatch(
      SUBSCRIBE_TOPIC,
      {
        topic: `/chat/${chatId}`,
        onMessage: message => commit(APPEND_CHAT_MESSAGE, { message }),
      }
    );
  },
  [DESTROY_CHAT]({ commit }, { chatId }) {
    commit(CLEAR_CHAT_MESSAGES);
    commit(UNSUBSCRIBE, { topic: `/chat/${chatId}` });
  },
  [SEND_CHAT_MESSAGE]({ dispatch }, { chatId, text }) {
    dispatch(SEND_TO_TOPIC, { topic: `/send/${chatId}`, msg: text });
  }
};

const mutations = {
  [SET_CHAT_MESSAGES](state, { content }) {
    state.chatMessages = content;
    state.errors = null;
  },
  [APPEND_CHAT_MESSAGE](state, { message }) {
    state.chatMessages = [...state.chatMessages, message];
  },
  [CLEAR_CHAT_MESSAGES](state) {
    state.chatMessages = [];
    state.errors = null;
  },
  [SET_ERROR](state, payload) {
    state.errors = payload;
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
