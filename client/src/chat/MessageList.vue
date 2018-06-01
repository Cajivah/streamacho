<template>
  <div
    ref="messages"
    class="messages"
  >
    <message
      v-for="(msg, index) in messages"
      :message="msg"
      :is-organisers="msg.authorUsername === organiser"
      :key="index"
    />
  </div>
</template>

<script>
import Message from './Message';

export default {
  name: 'ChatMessageList',
  components: {
    Message,
  },
  props: {
    messages: {
      required: true,
      type: Array,
    },
    organiser: {
      required: true,
      type: String,
    },
  },
  data() {
    return {
      isScrolled: false,
    };
  },
  mounted() {
    const messagesContainter = this.$refs.messages;
    messagesContainter.scrollTop = messagesContainter.scrollHeight;
    messagesContainter.onscroll = () => {
      this.isScrolled = ( messagesContainter.scrollHeight - messagesContainter.scrollTop ) !== messagesContainter.clientHeight;
    }
  },
  updated() {
    const messagesContainter = this.$refs.messages;
    if(messagesContainter && !this.isScrolled) {
      messagesContainter.scrollTop = messagesContainter.scrollHeight;
    }
  },
}
</script>

<style scoped>
.messages {
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>