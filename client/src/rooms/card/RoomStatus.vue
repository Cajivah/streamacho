<template>
  <div class="status level">
    <div class="level-left">
      <span
        v-tippy
        v-if="room.status === 'LIVE'"
        :title="iconTooltipByStatus"
        class="tag has-text-white has-background-danger is-unselectable"
      >
        Live
      </span>
      <span
        v-tippy
        v-else
        :title="iconTooltipByStatus"
        :class="iconByStatus"
        class="icon fa fa-lg has-text-white"
      />
    </div>
    <div class="level-right">
      <timer
        v-tippy
        v-if="room.status === 'LIVE'"
        :title="iconTooltipByStatus"
        :initial-value="initialSecondsValue"
        class="has-text-white is-unselectable timer"
      />
    </div>
  </div>
</template>

<script>
import Timer from '@/common/Timer';

const STATUSES = {
  PLANNED: {
    icon: ['fa-calendar', 'has-text-white'],
    tooltip: 'The stream has been planned'
  },
  LIVE: {
    icon: ['fa-clock-o', 'has-text-white'],
    tooltip: 'The stream is currently live'
  },
  COMPLETED: {
    icon: ['fa-check', 'has-text-white'],
    tooltip: 'The stream has already ended'
  },
  WASTED: {
    icon: ['fa-trash-o', 'has-text-dark'],
    tooltip: 'The stream did not start at the planned time'
  }
};

export default {
  name: 'RoomStatus',
  components: {
    Timer,
  },
  props: {
    room: {
      type: Object,
      required: true,
    }
  },

  computed: {
    initialSecondsValue() {
      return (new Date().getTime() - new Date(this.room.transmissionStartedAt).getTime()) / 1000;
    },

    iconByStatus() {
      return STATUSES[this.room.status].icon;
    },

    iconTooltipByStatus() {
      return STATUSES[this.room.status].tooltip;
    }
  }
}
</script>

<style scoped>
  .status {
    padding: 10px;
  }

  .icon {
    padding: 5px;
    margin-right: 10px;
  }

  .timer {
    font-family: "Roboto", monospace;
  }
</style>