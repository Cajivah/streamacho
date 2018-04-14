<template>
  <div>
    <ul>
      <meeting v-bind:key="meetings.indexOf(meeting)" v-for="meeting in meetings" :meeting="meeting" @onRemoveMeeting="removeMeeting"></meeting>
    </ul>
    <p v-if="!meetings.length">No meetings!</p>
  </div>
</template>

<script>
import Meeting from '@/meetings/Meeting.vue';

export default {
  name: 'meetingList',
  components: {
    Meeting
  },
  props: {
    meetings: {
      type: Array
    }
  },
  methods: {
    removeMeeting(meeting) {
      this.$emit('onRemoveMeeting', meeting);
    }
  }
};
</script>

<style scoped>

</style>
