<template>
  <div class="is-fullwidth room-header">
    <div class="is-fullwidth room-header-content">
      <div class="over-stream room-header-content">
        <div
          class="is-size-6 room-header-section"
        >
          <invite-users
            :disabled="!(statuses.status === 'Planned' || statuses.status === 'Live')"
            :room-id="roomId"
          />
        </div>
        <div
          v-tippy
          v-if="statuses.status !== 'Live'"
          :title="statuses.tooltip"
          class="is-size-6 room-header-section"
        >
          <i
            :class="statuses.icon"
            class="fa mr-1"
          />
          {{ statuses.status }}
        </div>
        <div
          v-tippy
          v-else
          title="The stream is currently live"
          class="room-header-section"
        >
          <div class="tag is-danger is-size-6 is-unselectable blinking">
            <i class="fa fa-circle mr-1"/>
            Live
          </div>
        </div>
        <div class="is-size-6 room-header-section">
          <i class="fa fa-calendar-o mr-1"/> {{ startAt }}
        </div>
        <div class="is-size-6 room-header-section has-text-centered">
          <i class="fa fa-user mr-1"/> {{ organiser }}
        </div>
      </div>
      <div class="over-chat is-size-6">
        <div>
          <select-stream-source
            v-if="isOrganiser"
            :disabled="!isPlaying"
            v-model="streamSource"
          />
          <a
            v-tippy
            v-if="isPlaying && isOrganiser && streamSource === 'screen'"
            class="change-streamed-application-button button is-success"
            title="Change streamed application"
            @click="changeStreamedApplication"
          >
            <b-icon
              icon="window-restore"
              icon-pack="fa"
            />
          </a>
        </div>
        <div v-if="isOrganiser">
          <a
            :disabled="!isPlaying"
            class="button is-danger is-size-6"
            @click="destroyStream">Stop stream</a>
        </div>
        <div v-else>
          <button
            :disabled="!isPlaying"
            class="button is-danger is-size-6"
            @click="disconnect">Leave stream
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InviteUsers from './InviteUsers';
import SelectStreamSource from './SelectStreamSource';

export default {
  name: 'RoomHeader',
  components: {
    InviteUsers,
    SelectStreamSource,
  },
  props: {
    roomId: {
      type: Number,
      required: true,
    },
    isOrganiser: {
      type: Boolean,
      required: true,
    },
    isPlaying: {
      type: Boolean,
      required: true,
    },
    status: {
      type: String,
      required: true,
    },
    organiser: {
      type: String,
      required: true,
    },
    startAt: {
      type: String,
      required: true,
    },
    destroyStream: {
      type: Function,
      required: true,
    },
    disconnect: {
      type: Function,
      required: true,
    },
    changeStreamedApplication: {
      type: Function,
      required: true,
    },
    changeStreamSource: {
      type: Function,
      required: true,
    },
  },
  data() {
    return {
      streamSource: 'camera',
    }
  },
  computed: {
    statuses() {
      return {
        PLANNED: {
          status: 'Planned',
          icon: 'fa-calendar',
          tooltip: 'The stream has been planned'
        },
        COMPLETED: {
          status: 'Completed',
          icon: 'fa-check',
          tooltip: 'The stream has already ended'
        },
        WASTED: {
          status: 'Wasted',
          icon: 'fa-trash',
          tooltip: 'The stream did not start at the planned time'
        },
        LIVE: {
          status: 'Live',
        }
      }[this.status];
    }
  },
  watch: {
    streamSource(val) {
      this.changeStreamSource(val);
    },
  },
}
</script>

<style scoped>
  .over-chat {
    display: flex;
    justify-content: space-evenly;
    width: 23%;
  }
  .over-stream {
    width: 77%;
    padding: 0 8%;
  }
  .room-header {
    display: flex;
    flex-direction: row;
    background-color: #eee;
    height: 70px;
  }
  .room-header-content {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
  }
  .change-streamed-application-button {
    margin-left: 10px;
  }
  .blinking {
    animation: blinker 2s ease-in-out infinite;
  }
  @keyframes blinker {
    0% {
      transform: scale(0.8);
      opacity: 0.6;
    }

    10% {
      transform: scale(1);
      opacity: 1;
    }

    80% {
      transform: scale(1);
      opacity: 1;
    }

    100% {
      transform: scale(0.8);
      opacity: 0.6;
    }
  }
</style>