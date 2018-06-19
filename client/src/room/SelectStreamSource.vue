<template>
  <b-dropdown
    v-model="source"
    :disabled="disabled"
    @active-change="active => isOpen = active"
  >
    <a
      v-tippy
      slot="trigger"
      title="Source of your stream"
      class="button is-info"
      type="button"
    >
      <template v-if="source === 'screen'">
        <b-icon
          icon="desktop"
          icon-pack="fa"/>
        <span>Screen</span>
      </template>
      <template v-else>
        <b-icon
          icon="video-camera"
          icon-pack="fa"/>
        <span>Web Camera</span>
      </template>
      <caret-down :is-open="isOpen"/>
    </a>

    <b-dropdown-item value="camera">
      <div class="media">
        <b-icon
          class="media-left"
          icon="video-camera"
          icon-pack="fa"/>
        <div class="media-content">
          <h3>Camera</h3>
          <small>Show your face to other people</small>
        </div>
      </div>
    </b-dropdown-item>

    <b-dropdown-item value="screen">
      <div class="media">
        <b-icon
          class="media-left"
          icon="desktop"
          icon-pack="fa"/>
        <div class="media-content">
          <h3>Screen</h3>
          <small>Show your screen to other people</small>
        </div>
      </div>
    </b-dropdown-item>
  </b-dropdown>
</template>

<script>
import CaretDown from '../common/CaretDown';
export default {
  name: 'SelectStreamSource',
  components: {
    CaretDown
  },
  props: {
    value: {
      type: String,
      default: 'camera',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data: () => ({
    isOpen: false,
    source: this.value,
  }),
  watch: {
    source(val) {
      this.$emit('input', val);
    }
  }
}
</script>
<style scoped>

</style>