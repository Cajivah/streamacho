<template>
  <div class='meetings-list'>
    <meeting v-for='meeting in meetings' :meeting='meeting' :key='meeting.id'></meeting>
  </div>
</template>

<script>
  import Meeting from './Meeting';

  export default {
    name: "MeetingsList",
    props: [
      'meetings',
    ],
    components: {
      Meeting,
    },
  }
</script>

<style scoped>
  .meetings-list {
    display: flex;
  }
</style>