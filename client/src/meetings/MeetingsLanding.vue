<template>
  <div>
    <h2 class='header is-large header-centered'>Meetings</h2>
    <div class='meetings-list'>
      <meeting v-for='meeting in exampleMeetings' :meeting='meeting'></meeting>
    </div>
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
      exampleMeetings: [{
        id: '123',
        organizer: 'Wojtek',
        description: 'Blah blah',
        logoURL: 'someURL',
        title: 'Room title',
        date: '30.01.2020',
        tags: ['a', 'b', 'c']
      }, {
        id: '123',
        organizer: 'Wojtek',
        description: 'Blah blah',
        logoURL: 'someURL',
        title: 'Room title',
        date: '30.01.2020',
        tags: ['a', 'b', 'c']
      }, {
        id: '123',
        organizer: 'Wojtek',
        description: 'Blah blah',
        logoURL: 'someURL',
        title: 'Room title',
        date: '30.01.2020',
        tags: ['a', 'b', 'c']
      }]
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
<style scoped>
.meetings-list {
  display: flex;
}

.header-centered {
  text-align: center;
  font-size: 1.5em;
}
</style>