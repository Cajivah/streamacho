<template>
  <div id="app">
    <navigation></navigation>
    <div class="container">
      <h2>meetings</h2>
      <meeting-list :meetings="meetings" @onRemoveMeeting="handleRemoveMeeting"></meeting-list>
      <add-meeting @onAddmeeting="handleAddmeeting"></add-meeting>
    </div>
  </div>
</template>

<script>
import meetingList from '@/components/meetings/MeetingList';
import AddMeeting from '@/components/meetings/AddMeeting';
import Navigation from '@/components/Navigation';

export default {
  name: 'app',
  components: {
    meetingList,
    AddMeeting,
    Navigation
  },
  computed: {
    meetings() {
      return this.$store.state.meetings;
    }
  },
  methods: {
    handleAddmeeting(meeting) {
      this.$store.dispatch('addMeeting', meeting);
    },

    handleRemovemeeting(meeting) {
      this.$store.dispatch('removeMeeting', meeting);
    },
    async initData() {
      let meetings = await fetch('meetings.json').then(data => data.json());
      this.$store.dispatch('initData', meetings);
    }    
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
