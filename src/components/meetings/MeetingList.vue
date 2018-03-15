<template>
  <div>
    <ul>
      <meeting v-for="meeting in meetings" :meeting="meeting" @onRemoveMeeting="removeMeeting"></meeting>
    </ul>
    <p v-if="!meetings.length">No meetings!</p>
  </div>
</template>

<script>
import meeting from '@/components/meetings/Meeting.vue';

  export default {
    name: "meetingList",
    components: {
      meeting
    },
    props: {
      meetings: {
        type: Array,
      }
    },
    methods: {
      removeMeeting(meeting) {
        this.$emit('onRemoveMeeting', meeting);
      }
    }
  }
</script>

<style scoped>

</style>
