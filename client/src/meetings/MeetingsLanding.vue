<template>
  <div>
    <h2 class='header is-large'>Meetings</h2>
    <meeting></meeting>
  </div>
</template>

<script>
import Meeting from '@/meetings/Meeting';

export default {
  name: 'meetings-landing',
  components: {
    Meeting
  },
  computed: {
    meetings() {
      return this.$store.state.meetings;
    }
  },
  methods: {
    async initMeetings() {
      let meetings = await fetch('meetings.json').then(data => data.json());
      this.$store.dispatch('initMeetings', meetings);
    }
  },
  mounted() {
    this.initMeetings();
  }
};
</script>