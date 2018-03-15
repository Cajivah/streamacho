<template>
      <li class="row">{{ meeting.name }}, date: {{ meeting.date}} <button class="btn btn-danger" v-on:click="removeMeeting(meeting)">Remove</button></li>
</template>

<script>
  export default {
    name: "meeting",
    props: {
      meeting: {
        type: Object,
      }
    },
    methods: {
      removeMeeting(meeting) {
        this.$emit('onRemoveMeeting', meeting);
      }
    }
  }
</script>

<style scoped>

</style>
