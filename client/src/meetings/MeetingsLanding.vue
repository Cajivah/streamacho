<template>
  <div>
    <h2 class='header is-large'>Meetings</h2>
    <meeting :meeting='exampleMeeting'></meeting>
  </div>
</template>

<script>
import Meeting from '@/meetings/Meeting';

export default {
  name: 'meetings-landing',
  components: {
    Meeting
  },
  data() {
    return {
      exampleMeeting: {
        id: '123',
        organizer: 'Wojtek',
        description: 'Blah blah',
        logoURL: 'someURL',
        title: 'Room title',
        date: '30.01.2020',
        tags: ['a', 'b', 'c']
      }
    }
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