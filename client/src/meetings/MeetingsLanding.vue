<template>
  <div>
    <h2 class='header is-large header-centered'>Meetings</h2>
    <meetings-list :meetings=exampleMeetings />
  </div>
</template>

<script>
  import MeetingsList from '@/meetings/MeetingsList';

  export default {
    name: 'meetings-landing',
    props: [
      'organised-only',
    ],
    components: {
      MeetingsList,
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
          id: '1233',
          organizer: 'Wojtek',
          description: 'Blah blah',
          logoURL: 'someURL',
          title: 'Room title',
          date: '30.01.2020',
          tags: ['a', 'b', 'c']
        }, {
          id: '1234',
          organizer: 'Wojtek',
          description: 'Blah blah',
          logoURL: 'someURL',
          title: 'Room title',
          date: '30.01.2020',
          tags: ['a', 'b', 'c']
        }]
      }
    }
  };
</script>
<style scoped>
  .header-centered {
    text-align: center;
    font-size: 1.5em;
  }
</style>