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
  props: {
    meeting: Object
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
  box-shadow: 0px 0px 2px 2px #d0d0d0;
  padding: 30px;
  margin: 20px;
  background-color: #efefef;
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
