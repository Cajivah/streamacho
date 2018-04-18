<template>
  <div>
    <h2 class='header is-large header-centered'>{{ headerText }}</h2>
    <meetings-list :meetings=exampleMeetings />
  </div>
</template>

<script>
  import MeetingsList from '@/meetings/MeetingsList';

  export default {
    name: "MeetingsPanel",
    props: {
      organisedOnly: {
        default: false,
        type: Boolean
      }
    },
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
    },
    computed: {
      headerText() {
        return this.organisedOnly ? 'My rooms' : 'Rooms';
      }
    }
  }
</script>

<style scoped>
  .header-centered {
    text-align: center;
    font-size: 1.5em;
  }
</style>