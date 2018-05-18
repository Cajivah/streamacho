<template>
  <div class="is-fullwidth has-navbar mb-5">
    <div class="is-fullwidth room-header">
      <div class="container room-header-content">
        <div
          v-tippy
          v-if="status.status !== 'Live'"
          :title="status.tooltip"
          class="is-size-5 room-header-section"
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
          <div class="tag is-danger is-size-5 is-unselectable blinking">
            <i class="fa fa-circle mr-1"/>
            Live
          </div>
        </div>
        <div class="is-size-5 room-header-section">
          <i class="fa fa-calendar-o mr-1"/> {{ formattedStartAt }}
        </div>
        <div class="is-size-4 room-header-section has-text-centered">
          <i class="fa fa-user mr-1"/> {{ selectedRoom.organiser }}
        </div>
        <div class="is-size-5 room-header-section has-text-right">
          <div v-if="isOrganiser">
            <a 
              class="button is-danger is-size-5" 
              @click="destroyStream" 
              v-if="isPlaying">Stop stream</a>
          </div>
          <div v-else>
            <a 
              class="button is-danger is-size-5" 
              @click="disconnect" 
              v-if="isPlaying">Leave stream</a>
          </div>
        </div>
      </div>
    </div>
    <div class="container mt-2">
      <div 
        id="main-video" 
        ref="mainVideo" 
        class="video-placeholder">
        <div 
          class="stream-initializer" 
          v-if="!isPlaying">
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
    <div class="container">
      <h2 class="is-size-1 mt-2">{{ selectedRoom.name }}</h2>
      <div class="tags">
        <span 
          class="tag is-primary is-size-6 mr-1" 
          v-for="tag in this.selectedRoom.tags">{{ tag }}</span>
      </div>
      <p class="has-text-grey-dark is-max-width-6 is-whitespace-preserve">{{ selectedRoom.description }}</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { CREATE_TRANSMISSION, DESTROY_TRANSMISSION, JOIN_TRANSMISSION } from '@/store/actions.type';
import store from '@/store'
import { showErrorToasts } from '@/ToastHandler';
import { FETCH_SELECTED_ROOM } from '@/store/actions.type';
import * as dateformat from 'dateformat';
import { ERASE_TRANSMISSION } from '../store/actions.type';
import { OpenVidu } from 'openvidu-browser';
import { defaultStreamProps } from './StreamQuality';
import OrganiserControls from '@/room/OrganiserControls';
import SpectatorControls from '@/room/SpectatorControls';

export default {
  name: 'RoomPanel',
  components: {
    OrganiserControls,
    SpectatorControls,
  },
  data() {
    return {
      isPlaying: false,
      session: null,
      streamProps: defaultStreamProps,
    }
  },
  beforeRouteEnter(to, from, next) {
    store.dispatch(FETCH_SELECTED_ROOM, { roomId: to.params.id })
      .then(() => next())
      .catch(showErrorToasts);
  },
  beforeDestroy() {
    this.disconnect();
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
  methods: {
    startStream() {
      this.$store.dispatch(CREATE_TRANSMISSION, { roomId: this.selectedRoom.id })
        .then(() => this.createPublisherSession())
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
      this.$store.dispatch(ERASE_TRANSMISSION)
        .then(() => {
          this.session.disconnect();
          this.resetView();
        }
        )
        .then(() => store.dispatch(FETCH_SELECTED_ROOM, { roomId: this.selectedRoom.id }))
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
    publishStream: function(OV) {
      const publisher = OV.initPublisher('main-video', this.streamProps);
      publisher.on('videoElementCreated', () => {
        const video = this.$refs.mainVideo.querySelector('video');
        video.controls = 'controls';
      });
      return this.session.publish(publisher);
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
    height: 70px;
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
    height: 600px;
    display: flex;
    align-items: center;
    justify-content: center;
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