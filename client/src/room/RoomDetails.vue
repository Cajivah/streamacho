<template>
  <div class="info">
    <figure class="logo is-128x128">
      <img-placeholder
        :string="room.name"
        :url="room.logoUrl"
        :size="128"
      />
    </figure>
    <div class="is-max-width-8">
      <h2 class="is-size-2 mt-2">{{ room.name }}</h2>
      <p class="has-text-grey-dark is-max-width-6 is-whitespace-preserve mb-2">{{ room.description }}</p>
      <div class="tags">
        <span
          v-for="tag in room.tags"
          class="tag is-primary is-size-6 mr-1">
          {{ tag }}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import ImgPlaceholder from '../common/ImgPlaceholder';

export default {
  name: 'RoomDetails',
  components: {
    ImgPlaceholder,
  },
  props: {
    room: {
      required: true,
      type: Object,
    },
  },
}
</script>

<style scoped>
  .logo {
    margin: 40px;
  }
  .info {
    display: flex;

  }
</style>