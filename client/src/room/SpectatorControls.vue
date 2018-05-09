<template>
  <a :class="['button', 'is-circle-300', canJoinTransmission() ? 'is-primary' : 'is-dark is-not-clickable']" :disabled="!canJoinTransmission()" @click="start">
    <div class="stream-initializer-prompt">
      <div v-if="canJoinTransmission()">
        <h5 class="is-size-1 has-text-weight-bold">Join</h5>
        <h6 class="is-size-4">stream</h6>
      </div>
      <div v-else-if="isPlanned()">
        <h6 class="is-size-4 has-text-weight-semibold">Starts in</h6>
        <countdown :deadline="formattedDateStart" :on-deadline="forceUpdate" :on-deadline-delay="1000"></countdown>
      </div>
      <div v-else>
        <h5 class="is-size-2 has-text-weight-bold">This stream</h5>
        <h6 class="is-size-3">can't be joined</h6>
      </div>
    </div>
  </a>
</template>

<script>
  import { isPlanned, canJoinTransmission } from "./RoomUtils";
  import Countdown from '../common/Countdown';
  import * as dateformat from 'dateformat';

  export default {
    name: "SpectatorControls",
    props: {
      start: {
        type: Function,
        required: true,
      },
      startAt: {
        type: String,
        required: true,
      },
      status: {
        type: String,
        required: true,
      },
    },
    components: {
      Countdown,
    },
    computed: {
      formattedDateStart() {
        return dateformat(new Date(this.startAt), 'default');
      }
    },
    methods: {
      forceUpdate() {
        this.$forceUpdate();
      },
      canJoinTransmission() {
        return canJoinTransmission(this.status, this.startAt);
      },
      isPlanned() {
        return isPlanned(this.status, this.startAt);
      },
    },
  }
</script>

<style scoped>
  .stream-initializer-prompt {
    display: flex;
    flex-direction: column;
    justify-content: center;
    line-height: 36px;
  }
</style>