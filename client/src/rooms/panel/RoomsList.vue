<template>
  <div class="rooms-list">
    <room
      class="room"
      v-for="room in rooms"
      :room="room"
      :key="room.id"/>
  </div>
</template>

<script>
import Room from '@/rooms/card/Room';

export default {
  name: 'RoomsList',
  components: {
    Room,
  },
  props: {
    rooms: {
      type: Array,
      required: true,
    }
  },
}
</script>

<style scoped>
  .rooms-list {
    display: grid;
    grid-gap: 50px;
    grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  }
</style>