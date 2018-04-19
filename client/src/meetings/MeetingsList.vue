<template>
  <div class="meetings-list">
    <meeting
      v-for="meeting in meetings"
      :meeting="meeting"
      :key="meeting.id"/>
  </div>
</template>

<script>
import Meeting from './Meeting';

export default {
  name: 'MeetingsList',
  components: {
    Meeting,
  },
  props: {
    meetings: {
      type: Array,
      required: true,
    }
  },
}
</script>

<style scoped>
  .meetings-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
</style>