<template>
  <div class='meeting-container'>
    <div class='meeting-header'>
      {{ meeting.organizer }}
    </div>
    <div class='meeting-logo'>
      {{ meeting.logo }}
    </div>
    <div class='meeting-content'>
      <div>{{ meeting.title }}</div>
      <div>{{ meeting.description }}</div>
      <div>{{ meeting.date }}</div>
      <button class='button is-primary' @click="'joinRoom()'">Join</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'meeting',
  data() {
    return {
      meeting: {
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
  methods: {
    joinRoom() {
      this.$router.push(`meeting/${meeting.id}`);
    }
  }
};
</script>

<style scoped>
.meeting-container {
  border: 1px solid #ddd;
  box-shadow: 5px 5px #333333;
}

.meeting-header {
  text-align: center;
}

.meeting-logo {
  text-align: center;
}

.meeting-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
