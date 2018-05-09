<template>
  <a :class="['button', 'is-circle-300',  canStartTransmission() ? 'is-primary' : 'is-dark is-not-clickable']" :disabled="!canStartTransmission()" @click="start">
    <div class="stream-initializer-prompt" >
      <div v-if="isPlanned()">
        <h6 class="is-size-4 has-text-weight-semibold">Starts in</h6>
        <countdown :deadline="formattedDateStart" :on-deadline="forceUpdate" :on-deadline-delay="1000"></countdown>
      </div>
      <div v-else-if="canStartTransmission()">
        <h5 class="is-size-1 has-text-weight-bold">Start</h5>
        <h6 class="is-size-4">streaming</h6>
      </div>
      <div v-else>
        <h5 class="is-size-2 has-text-weight-semibold">This stream</h5>
        <h6 class="is-size-3">can't be started</h6>
      </div>
    </div>
  </a>
</template>

<script>
  import Countdown from '../common/Countdown';
  import * as dateformat from 'dateformat';
  import { canStartTransmission, isPlanned } from './RoomUtils';

  export default {
    name: "OrganiserControls",
    props: {
      start: {
        type: Function,
      },
      startAt: {
        type: String,
      },
      status: {
        type: String,
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
      isPlanned() {
        return isPlanned(this.status, this.startAt);
      },
      canStartTransmission() {
        return canStartTransmission(this.status, this.startAt);
      },
    }
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