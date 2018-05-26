<template>
  <div class="is-fullwidth has-navbar mb-5">
    <div class="is-fullwidth room-header">
      <div class="container room-header-content">
        <div
          v-if="status.status === 'Planned' || status.status === 'Live'"
          class="is-size-6 room-header-section"
        >
          <invite-users :room-id="selectedRoom.id"/>
        </div>
        <div
          v-tippy
          v-if="status.status !== 'Live'"
          :title="status.tooltip"
          class="is-size-6 room-header-section"
        >
          <i
            :class="status.icon"
            class="fa mr-1"
          />
          {{ status.status }}
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
          <i class="fa fa-calendar-o mr-1"/> {{ formattedStartAt }}
        </div>
        <div class="is-size-6 room-header-section has-text-centered">
          <i class="fa fa-user mr-1"/> {{ selectedRoom.organiser }}
        </div>
        <div class="is-size-6 room-header-section">
          <select-stream-source
            v-if="isOrganiser && isPlaying"
            v-model="streamSource"
          />
          <a
            v-tippy
            v-if="isPlaying && streamSource === 'screen'"
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
        <div class="is-size-6 room-header-section has-text-right">
          <div v-if="isOrganiser">
            <a
              v-if="isPlaying"
              class="button is-danger is-size-6"
              @click="destroyStream">Stop stream</a>
          </div>
          <div v-else>
            <a
              v-if="isPlaying"
              class="button is-danger is-size-6"
              @click="disconnect">Leave stream</a>
          </div>
        </div>
      </div>
    </div>
    <div
      :class="isPlaying && 'video-background--playing'"
      class="is-fullwidth video-background"
    >
      <div class="container">
        <div
          id="main-video"
          ref="mainVideo"
          class="video-placeholder">
          <div
            v-if="!isPlaying"
            class="stream-initializer" >
            <organiser-controls
              v-if="isOrganiser"
              :start="startStream"
              :status="selectedRoom.status"
              :start-at="selectedRoom.startAt"/>
            <spectator-controls
              v-else
              :start="joinStream"
              :status="selectedRoom.status"
              :start-at="selectedRoom.startAt"/>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <h2 class="is-size-1 mt-2">{{ selectedRoom.name }}</h2>
      <div class="tags">
        <span
          v-for="tag in selectedRoom.tags"
          class="tag is-primary is-size-6 mr-1">
          {{ tag }}
        </span>
      </div>
      <p class="has-text-grey-dark is-max-width-6 is-whitespace-preserve">{{ selectedRoom.description }}</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import _ from 'lodash';
import {
  CREATE_TRANSMISSION,
  DESTROY_TRANSMISSION,
  FETCH_SELECTED_ROOM,
  JOIN_TRANSMISSION
} from '../store/actions.type';
import store from '@/store'
import { showErrorToasts } from '@/ToastHandler';
import * as dateformat from 'dateformat';
import { OpenVidu } from 'openvidu-browser';
import { defaultStreamProps } from './StreamQuality';
import OrganiserControls from '@/room/OrganiserControls';
import SpectatorControls from '@/room/SpectatorControls';
import { PURGE_TRANSMISSION } from '../store/mutations.type';
import InviteUsers from './InviteUsers';
import SelectStreamSource from './SelectStreamSource';

export default {
  name: 'RoomPanel',
  components: {
    SelectStreamSource,
    InviteUsers,
    OrganiserControls,
    SpectatorControls,
  },
  data() {
    return {
      isPlaying: false,
      session: null,
      streamProps: _.clone(defaultStreamProps),
      publisher: null,
      streamSource: 'camera',
    }
  },
  beforeRouteEnter(to, from, next) {
    store.dispatch(FETCH_SELECTED_ROOM, { roomId: to.params.id })
      .then(() => next())
      .catch(showErrorToasts);

    this.streamSource = this.streamProps.screen ? 'screen' : 'camera';
  },
  computed: {
    ...mapGetters([
      'selectedRoom',
      'loggedUser',
      'transmission',
    ]),
    isOrganiser() {
      return this.loggedUser.username === this.selectedRoom.organiser;
    },
    formattedStartAt() {
      return dateformat(new Date(this.selectedRoom.startAt), 'ddd mmm dd yyyy HH:MM');
    },
    status() {
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
      }[this.selectedRoom.status];
    }
  },
  watch: {
    streamSource(val) {
      this.changeSource(val);
    }
  },
  beforeDestroy() {
    this.disconnect();
  },
  methods: {
    startStream() {
      this.$store.dispatch(CREATE_TRANSMISSION, { roomId: this.selectedRoom.id })
        .then(() => this.createPublisherSession())
        .then(() => this.$store.dispatch(FETCH_SELECTED_ROOM, { roomId: this.selectedRoom.id }))
        .catch(showErrorToasts);
    },
    joinStream() {
      this.$store.dispatch(JOIN_TRANSMISSION, { roomId: this.selectedRoom.id })
        .then(() => this.createSpectatorSession())
        .catch(() => showErrorToasts({ messages: ['Stream has not been started yet'] }));
    },
    destroyStream() {
      this.$store.dispatch(DESTROY_TRANSMISSION, { roomId: this.selectedRoom.id })
        .then(this.disconnect)
        .catch(showErrorToasts);
    },
    disconnect() {
      this.$store.commit(PURGE_TRANSMISSION);
      if(this.session) {
        this.session.disconnect();
      }
      this.resetView();
      store.dispatch(FETCH_SELECTED_ROOM, { roomId: this.selectedRoom.id })
        .catch(showErrorToasts);
    },
    createPublisherSession: function() {
      const OV = new OpenVidu();
      this.session = OV.initSession(this.transmission.sessionId);
      this.session.connect(this.transmission.token,
        (error) => {
          if (!error) {
            if (!this.publishStream(OV)) {
              showErrorToasts(({ messages: ['Could not initialize stream, check webcam access!'] }));
            }
            this.isPlaying = true;
          } else {
            showErrorToasts({ messages: [`There was an error connecting to the session: ${error.code} ${error.message}`] })
          }
        }
      );
    },
    createSpectatorSession: function() {
      const OV = new OpenVidu();
      this.session = OV.initSession(this.transmission.sessionId);
      this.session.on('streamDestroyed', () => setTimeout(this.handleStreamDestroyed, 1000));
      this.session.on('streamCreated', event => this.subscribeToRemoteStream(event));
      this.session.connect(this.transmission.token,
        error => error
          ? showErrorToasts({ messages: [`There was an error connecting to the session: ${error.code} ${error.message}`] })
          : this.isPlaying = true
      );
    },
    handleStreamDestroyed: function() {
      this.$store.dispatch(FETCH_SELECTED_ROOM, { roomId: this.selectedRoom.id })
        .then(this.handlePublisherDisconnection)
        .catch(showErrorToasts);
    },
    handlePublisherDisconnection(room) {
      if(room.status === 'LIVE') {
        showErrorToasts({ messages: ['Organiser disconnected. Waiting for him to continue...'] });
      } else {
        showErrorToasts({ messages: ['Organiser closed transmission, disconnecting...'] });
        this.disconnect();
      }
    },
    publishStream: function() {
      this.publisher = this.session.openVidu.initPublisher('main-video', this.streamProps);
      this.publisher.on('videoElementCreated', () => {
        const video = this.$refs.mainVideo.querySelector('video');
        video.controls = 'controls';
      });
      return this.session.publish(this.publisher);
    },
    unpublishStream: function() {
      this.session.unpublish(this.publisher);
      this.publisher = null;
    },
    changeSource(source) {
      if(this.isPlaying) {
        this.unpublishStream();
        this.streamProps.screen = source === 'screen';
        this.publishStream();
      }
    },
    changeStreamedApplication() {
      this.changeSource('screen');
    },
    subscribeToRemoteStream: function(streamCreatedEvent) {
      const  subscriber = this.session.subscribe(streamCreatedEvent.stream, 'main-video');
      subscriber.on('videoElementCreated', () => {
        const video = this.$refs.mainVideo.querySelector('video');
        video.controls = 'controls';
      });
    },
    resetView: function() {
      this.session = null;
      this.isPlaying = false;
      this.streamProps = defaultStreamProps;
    },
  },
}
</script>

<style scoped>
  .room-header {
    display: flex;
    flex-direction: row;
    background-color: #eee;
    height: 50px;
  }
  .room-header-content {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
  }
  .room-header-section {
    width: 33%;
    text-align: center;
  }
  .has-navbar {
    padding-top: 52px;
  }
  .video-placeholder {
    width: 100%;
    min-height: 90vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .icon {
    margin: 0 10px;
  }

  .blinking {
    animation: blinker 2s ease-in-out infinite;
  }

  .video-background {
    transition: 1s;
  }

  .video-background--playing {
    transition: 1s;
    background: black;
  }

  .change-streamed-application-button {
    margin-left: 10px;
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
