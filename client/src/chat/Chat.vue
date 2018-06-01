<template>
  <div class="chat-wrapper">
    <div class="message-wrapper">
      <message-list 
        :messages="chatMessages"
        :organiser="organiser"/>
    </div>
    <div class="message-input">
      <message-input 
        :on-send="onSendMessage"/>
    </div>
  </div>
</template>

<script>
import {
  DESTROY_CHAT,
  FETCH_CHAT_MESSAGES,
  SEND_CHAT_MESSAGE,
  SUBSCRIBE_CHAT,
} from '../store/actions.type';
import { mapGetters } from 'vuex';
import MessageList from './MessageList';
import MessageInput from './MessageInput';

export default {
  name: 'Chat',
  components: {
    MessageList,
    MessageInput,
  },
  props: {
    chatId: {
      required: true,
      type: Number,
    },
    organiser: {
      required: true,
      type: String,
    },
  },
  computed: {
    ...mapGetters([
      'chatMessages',
      'loggedUser',
    ]),
  },
  created() {
    const { dispatch } = this.$store;
    const chatId = this.chatId;
    dispatch(FETCH_CHAT_MESSAGES, { chatId })
      .then(() => dispatch(SUBSCRIBE_CHAT, { chatId }));
  },
  beforeDestroy() {
    this.$store.dispatch(DESTROY_CHAT, { chatId: this.chatId });
  },
  methods: {
    onSendMessage(msg) {
      this.$store.dispatch(SEND_CHAT_MESSAGE, { chatId: this.chatId, text: msg });
    },
  }
}
</script>

<style scoped>
  .chat-wrapper {
    background-color: #ccc;
    height: calc(100vh - 70px - 52px);
    max-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 23%;
    max-width: 23%;
  }
  .message-wrapper {
    display: flex;
    flex: 1;
    min-height: 0;
  }
  .message-input {
    margin: 3px 15px;
  }
</style>