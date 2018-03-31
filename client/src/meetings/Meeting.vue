<template>
  <div>
    <li class="row">{{ meeting.name }}, date: {{ meeting.date}}<button class="button is-danger" v-on:click="removeMeeting(meeting)">Remove</button></li>
  </div>
</template>

<script>
export default {
  name: "meeting",
  props: {
    meeting: {
      type: Object
    }
  },
  methods: {
    removeMeeting(meeting) {
      this.$emit("onRemoveMeeting", meeting);
    }
  }
};
</script>

<style scoped>

</style>
