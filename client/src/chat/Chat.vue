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
import { FETCH_CHAT_MESSAGES, SEND_CHAT_MESSAGE, SUBSCRIBE_TOPIC } from '../store/actions.type';
import { mapGetters } from 'vuex';
import MessageList from './MessageList';
import MessageInput from './MessageInput';
import { APPEND_CHAT_MESSAGE, CLEAR_CHAT_MESSAGES, UNSUBSCRIBE } from '../store/mutations.type';

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
    const store = this.$store;
    const chatId = this.chatId;
    store.dispatch(FETCH_CHAT_MESSAGES, { chatId })
      .then(() => store.dispatch(
        SUBSCRIBE_TOPIC,
        {
          topic: `/chat/${chatId}`,
          onMessage: function(message) { store.commit(APPEND_CHAT_MESSAGE, { message }); }
        })
      );
  },
  beforeDestroy() {
    this.$store.commit(CLEAR_CHAT_MESSAGES);
    this.$store.commit(UNSUBSCRIBE, { topic: `/chat/${this.chatId}` });
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
    min-height: 0px;
  }
  .message-input {
    margin: 3px 15px;
  }
</style>