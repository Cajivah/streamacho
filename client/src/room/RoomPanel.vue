<template>
  <div class="is-fullwidth has-navbar mb-5">
    <div class="is-fullwidth room-header">
      <div class="container room-header-content">
        <div class="is-size-5 room-header-section">
          <i class="fa fa-calendar-o mr-1"></i> {{formattedStartAt}}
        </div>
        <div class="is-size-4 room-header-section has-text-centered">
          <i class="fa fa-user mr-1"></i> {{selectedRoom.organiser}}
        </div>
        <div class="is-size-5 room-header-section has-text-right">
          <div v-if="isOrganiser">
            <a class="button is-danger is-size-5" @click="destroyStream" v-if="isPlaying">Stop stream</a>
          </div>
          <div v-else>
            <a class="button is-danger is-size-5" @click="disconnect" v-if="isPlaying">Leave stream</a>
          </div>
        </div>
      </div>
    </div>
    <div class="container mt-2">
      <div id="main-video" ref="mainVideo" class="video-placeholder">
        <div class="stream-initializer" v-if="!isPlaying">
          <organiser-controls v-if="isOrganiser" :start="startStream" :status="selectedRoom.status" :start-at="selectedRoom.startAt"/>
          <spectator-controls v-else :start="joinStream" :status="selectedRoom.status" :start-at="selectedRoom.startAt"/>
        </div>
      </div>
    </div>
    <div class="container">
      <h2 class="is-size-1 mt-2">{{selectedRoom.name}}</h2>
      <div class="tags">
        <span class="tag is-primary is-size-6 mr-1" v-for='tag in this.selectedRoom.tags'>{{tag}}</span>
      </div>
      <p class="has-text-grey-dark is-max-width-6 is-whitespace-preserve">{{selectedRoom.description}}</p>
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
  import { defaultStreamProps } from './streamQuality';
  import OrganiserControls from '@/room/OrganiserControls';
  import SpectatorControls from '@/room/SpectatorControls';

  export default {
    name: "RoomPanel",
    data() {
      return {
        isPlaying: false,
        session: null,
        streamProps: defaultStreamProps,
      }
    },
    components: {
      OrganiserControls,
      SpectatorControls,
    },
    beforeRouteEnter(to, from, next) {
      store.dispatch(FETCH_SELECTED_ROOM, { roomId: to.params.id })
        .then(() => next())
        .catch(showErrorToasts);
    },
    beforeDestroy() {
      this.isOrganiser && this.isPlaying ? this.destroyStream() : this.disconnect();
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
      canStartTransmission() {
        const dateStart = new Date(this.selectedRoom.startAt);
        return dateStart <= new Date() && this.selectedRoom.status === 'PLANNED';
      },
      canJoinTransmission() {
        return this.selectedRoom.status === 'LIVE';
      },
      formattedStartAt() {
        return dateformat(new Date(this.selectedRoom.startAt), 'ddd mmm dd yyyy HH:MM');
      },
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
          .catch(() => showErrorToasts({ messages: ['Stream has not been started yet']}));
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
              if (this.publishStream(OV)) {
              } else {
                showErrorToasts(({messages: ['Could not initialize stream, check webcam access!']}));
              }
              this.isPlaying = true;
            } else {
              showErrorToasts({messages: [`There was an error connecting to the session: ${error.code} ${error.message}`]})
            }
          }
        );
      },
      createSpectatorSession: function() {
        const OV = new OpenVidu();
        this.session = OV.initSession(this.transmission.sessionId);
        this.session.on('streamCreated', event => this.subscribeToRemoteStream(event));
        this.session.connect(this.transmission.token,
          error => error
            ? showErrorToasts({messages: [`There was an error connecting to the session: ${error.code} ${error.message}`]})
            : this.isPlaying = true
        );
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
</style>