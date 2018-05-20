<template>
  <div
    :class="{'spectators':!isOrganisers}"
    class="chat-message">
    <div class="message-content">
      <div
        :class="{'message-author-spectator':!isOrganisers}"
        class="message-author">
        <p class="author" >{{ message.authorUsername }}</p>
        <div class="vl"/>
        <p class="date">{{ formattedDate }}</p>
      </div>
      <p class="text">{{ message.text }}</p>
    </div>
  </div>
</template>

<script>
import dateFormat from 'dateformat';

export default {
  name: 'Message',
  props: {
    message: {
      required: true,
      type: Object,
    },
    isOrganisers: {
      required: true,
      type: Boolean,
    }
  },
  computed: {
    formattedDate() {
      const date = this.message.sendAt ? new Date(this.message.sendAt) : new Date();
      return `${dateFormat(date, 'shortDate')} ${dateFormat(date, 'HH:MM')}`;
    }
  }
}
</script>

<style scoped>
  .chat-message {
    display: flex;
    align-items: center;
    margin: 3px;
    padding: 3px;
  }
  .chat-message:not(:last-child) {
    margin-bottom: 0.5rem;
  }
  .spectators {
    flex-direction: row-reverse;
  }
  .message-content {
    background-color: #fafafa;
    width: fit-content;
    max-width: 90%;
    min-width: 220px;
    padding: 0.7rem 0.7rem;
    border-radius: 3px;
    color: #666;
  }
  .message-author {
    display: flex;
    align-items: center;
    margin-bottom: 0.3rem;
  }
  .message-author-spectator {
    direction: rtl;
  }
  .author {
    font-weight: 600;
    text-overflow: ellipsis;
  }
  .vl {
    height: 1rem;
    border-left: 1px solid #888;
    margin: 0 0.5rem;
  }
  .date {
    font-size: 0.8rem;
    min-width: 82px;
  }
  .text {
    font-size: 0.9rem;
    word-wrap: break-word;
  }
</style>