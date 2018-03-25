<template>
    <div>
        <h2>meetings</h2>
        <meeting-list :meetings="meetings" @onRemoveMeeting="handleRemoveMeeting"></meeting-list>
        <add-meeting @onAddMeeting="handleAddMeeting"></add-meeting>
    </div>
</template>

<script>
import MeetingList from "@/components/meetings/MeetingList";
import AddMeeting from "@/components/meetings/AddMeeting";

export default {
  name: "meetings-hub",
  components: {
    MeetingList,
    AddMeeting
  },
  computed: {
    meetings() {
      return this.$store.state.meetings;
    }
  },
  methods: {
    handleAddMeeting(meeting) {
      this.$store.dispatch("createMeeting", meeting);
    },

    handleRemoveMeeting(meeting) {
      this.$store.dispatch("removeMeeting", meeting);
    },
    async initMeetings() {
      let meetings = await fetch("meetings.json").then(data => data.json());
      this.$store.dispatch("initMeetings", meetings);
    }
  },
  mounted() {
    this.initMeetings();
  }
};
</script>