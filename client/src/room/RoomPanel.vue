<template>
  <div 
    v-if="loggedUser" 
    class="is-fullwidth has-navbar mb-5">
    <div class="is-fullwidth">
      <room-header
        :room-id="selectedRoom.id"
        :start-at="formattedStartAt"
        :organiser="selectedRoom.organiser"
        :is-organiser="isOrganiser"
        :is-playing="isPlaying"
        :destroy-stream="destroyStream"
        :disconnect="disconnect"
        :status="selectedRoom.status"
        :change-streamed-application="changeStreamedApplication"
        :change-stream-source="changeSource"
      />
    </div>
    <div
      :class="isPlaying && 'video-background--playing'"
      class="is-fullwidth main-section video-background">
      <div class="container stream">
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
      <chat
        :chat-id="selectedRoom.id"
        :organiser="selectedRoom.organiser"
      />
    </div>
    <room-details
      :room="selectedRoom"
    />
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
import store from '../store'
import { showErrorToasts } from '@/ToastHandler';
import * as dateformat from 'dateformat';
import { OpenVidu } from 'openvidu-browser';
import { defaultStreamProps } from './utils/StreamQuality';
import OrganiserControls from '../room/OrganiserControls';
import SpectatorControls from '../room/SpectatorControls';
import Chat from '../chat/Chat';
import RoomHeader from './RoomHeader';
import RoomDetails from './RoomDetails';
import { PURGE_TRANSMISSION } from '../store/mutations.type';

export default {
  name: 'RoomPanel',
  components: {
    OrganiserControls,
    SpectatorControls,
    Chat,
    RoomHeader,
    RoomDetails,
  },
  data() {
    return {
      isPlaying: false,
      session: null,
      streamProps: _.clone(defaultStreamProps),
      publisher: null,
    }
  },
  beforeRouteEnter(to, from, next) {
    store.dispatch(FETCH_SELECTED_ROOM, { roomId: to.params.id })
      .then(() => next())
      .catch(showErrorToasts);
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
  .stream {
    width: 77%;
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
  .video-background {
    transition: 1s;
  }
  .video-background--playing {
    transition: 1s;
    background: black;
  }
  .main-section {
    display: flex;
    justify-content: space-between;
  }
</style>
