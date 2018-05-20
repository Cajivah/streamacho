<template>
  <b-field
    type="is-light">
    <b-input
      v-validate="'required'"
      v-model="message"
      name="message"
      placeholder="Type your message and hit enter..."
      maxlength="150"
      type="textarea"
      @keydown.enter.exact.native="sendMessage"
    />
  </b-field>
</template>

<script>
export default {
  name: 'MessageInput',
  props: {
    onSend: {
      required: true,
      type: Function,
    }
  },
  data() {
    return {
      message: ''
    };
  },
  methods: {
    sendMessage: function() {
      this.message && this.onSend({ text: this.message });
      this.reset()
    },
    reset: function() {
      this.message = '';
    },
  }
}
</script>

<style scoped>

</style>