import { apiURL } from '../config/httpConfigurer';
import SockJS from 'sockjs-client';
import { over as stompOver } from '@stomp/stompjs'
import { CLOSE_SOCKET, OPEN_SOCKET, SEND_TO_TOPIC, SUBSCRIBE_TOPIC } from './actions.type';
import { ADD_SUBSCRIPTION, CLEAR_SOCKET, SET_ERROR, SET_SOCKET_CLIENT, UNSUBSCRIBE } from './mutations.type';

const state = {
  stompClient: null,
  subscriptions: {},
};

const getters = {
};

const actions = {
  [OPEN_SOCKET]({ commit }, { onConnected }) {
    return new Promise((resolve, reject) => {
      try {
        const socket = new SockJS(`${apiURL}/chat/ws`);
        const client = stompOver(socket);
        client.connect({}, function() {
          commit(SET_SOCKET_CLIENT, { client });
          onConnected && onConnected();
        });
        resolve(client);
      } catch (e) {
        reject(e);
      }
    });
  },
  [CLOSE_SOCKET]({ commit, state }) {
    return new Promise((resolve, reject) => {
      try {
        state.stompClient && state.stompClient.disconnect(function() { commit(CLEAR_SOCKET); }, {});
        resolve()
      } catch (e) {
        reject(e);
      }
    });
  },
  [SUBSCRIBE_TOPIC]({ commit, state, dispatch }, { topic, onMessage }) {
    return new Promise((resolve, reject) => {
      if(!state.stompClient) {
        dispatch(OPEN_SOCKET, { onConnected: function() {dispatch(SUBSCRIBE_TOPIC, { topic, onMessage })} });
      } else {
        try {
          commit(UNSUBSCRIBE, { topic });
          const subscription = state.stompClient.subscribe(topic, function(msg) {
            onMessage(JSON.parse(msg.body));
          });
          commit(ADD_SUBSCRIPTION, { subscription, topic });
          resolve();
        } catch (e) {
          reject(e);
        }
      }
    });
  },
  [SEND_TO_TOPIC]({ state }, { topic, msg }) {
    return new Promise((resolve, reject) => {
      try {
        state.stompClient.send(topic, {}, JSON.stringify(msg));
        resolve(msg);
      } catch (e) {
        reject(e);
      }
    })
  }
};

const mutations = {
  [SET_SOCKET_CLIENT]( state, { client }) {
    state.stompClient = client;
  },
  [CLEAR_SOCKET](state) {
    state.stompClient = null;
  },
  [ADD_SUBSCRIPTION](state, { subscription, topic }) {
    state.subscriptions[topic] = subscription;
  },
  [UNSUBSCRIBE](state, { topic }) {
    state.subscriptions[topic] && state.subscriptions[topic].unsubscribe();
    delete state.subscriptions[topic];
  },
  [SET_ERROR](state, { error }) {
    state.errors = error;
  }
};

export default {
  state,
  actions,
  mutations,
  getters
}
