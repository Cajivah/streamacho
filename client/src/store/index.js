import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';
import auth from './auth.module';
import room from './room.module'
import transmission from './transmission.module';
import chat from './chat.module';
import socket from './socket.module';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    room,
    transmission,
    chat,
    socket,
  },
  plugins: [createLogger()]
});
