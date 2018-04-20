<template>
  <div class="is-fullwidth has-navbar">
    <div class="is-fullwidth room-header">
      <div class="container room-header-content">
        <div class="is-size-4 room-header-section">
          <i class="fa fa-calendar-o mr-1"></i> {{room.date}}
        </div>
        <div class="is-size-4 room-header-section ">
          <i class="fa fa-user mr-1"></i> {{room.organizer}}
        </div>
        <div class="is-size-4 room-header-section ">
          <div v-if="isOrganiser">
            <a class="button is-primary" @click="startStream" v-if="!isPlaying">Start stream</a>
            <a class="button is-danger" @click="destroyStream" v-else>Stop stream</a>
          </div>
          <div v-else>
            <a class="button is-primary" @click="startStream" v-if="!isPlaying">Join stream</a>
            <a class="button is-danger" @click="disconnect" v-else>Leave stream</a>
          </div>
        </div>
      </div>
    </div>
    <div class="container videos mt-2">
      <div id="main-video" class="video-placeholder">
        <video class="is-fullwidth is-fullheight" srcO></video>
      </div>
      <div id="video-container" class="webcam-lookup"></div>
    </div>
    <div class="container">
      <h2 class="is-size-1 mt-2">{{room.title}}</h2>
      <tags :tags="room.tags"></tags>
      <p class="has-text-grey-dark">{{room.description}}</p>
    </div>
  </div>
</template>

<script>
  import Tags from '@/room/Tags';
  import jQuery from 'jquery';
  import { OpenVidu, Session, Stream } from 'openvidu-browser';
  import Vue from 'vue';

  export default {
    name: "RoomPanel",
    props: ['id'],
    components: {
      Tags,
    },
    data() {
      return {
        sessionId: null,
        token: null,
        session: null,
        isPlaying: false,
      }
    },
    computed: {
      room: function() {
        return {
          id: '123',
          organizer: 'malyjasiak',
          description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla auctor eu quam ut fermentum. Mauris vel est porta justo rutrum tempor at in nibh. Aliquam ullamcorper elit sit amet mattis rutrum. Ut fermentum risus ut tincidunt pellentesque. Nam justo neque, maximus id quam sed, tincidunt dictum lacus. Vestibulum at velit felis. Nullam neque justo, tristique eu ultrices id, interdum quis nulla. Donec volutpat odio ut mollis convallis. Vestibulum ultricies urna velit, eget semper mauris iaculis at. Nam eget erat et ligula dapibus varius. In porta ut mauris ut condimentum. Mauris mollis aliquet felis, vel tempus leo dapibus tempus. Maecenas tincidunt, dolor a dictum congue, sem nunc pellentesque diam, vitae lacinia enim dolor eu arcu. Donec aliquam mi mi.\n' +
          '\n' +
          'Ut vel dolor ante. Donec finibus risus vel est malesuada, mollis fermentum lorem venenatis. Cras ut orci sit amet ante ultrices tempus quis et erat. Donec eget sapien porttitor, ornare nulla eget, tristique ligula. Donec facilisis tortor ex, vitae ultricies libero sodales id. Suspendisse quis mauris suscipit, viverra magna id, dictum lacus. Vivamus eu varius libero.',
          logoURL: 'someURL',
          title: 'Room title',
          date: '30.01.2020, 16:30',
          tags: ['a', 'b', 'c']
        }
      },
      isOrganiser: function() {
        return this.$store.getters.loggedUser.username === this.room.organizer;
      },
      roomId: function() {
        return this.$route.params.id;
      }
    },
    methods: {
      destroyStream: function() {
        this.disconnect();
        Vue.axios.delete(`/meetings/rooms/${this.roomId}/sessions`)
      },
      disconnect: function() {
        this.session.disconnect();
        this.session = null;
        jQuery('#main-video video').get(0).srcObject = null;
      },
      getSessionIdAndToken: function(callback) {
        if(this.isOrganiser) {
          Vue.axios.post(`/meetings/rooms/${this.roomId}/sessions`).then((response) => {
            const sessionDTO = response.data;
            this.sessionId = sessionDTO.sessionId;
            this.token = sessionDTO.token;
            callback(); // Continue the join operation
          });
        } else {
          Vue.axios.post(`/meetings/rooms/${this.roomId}/join`).then((response) => {
            const sessionDTO = response.data;
            this.sessionId = sessionDTO.sessionId;
            this.token = sessionDTO.token;
            callback(); // Continue the join operation
          });
        }
      },
      initMainVideo: function(videoElement) {
        jQuery('#main-video video').get(0).srcObject = videoElement.srcObject;
        jQuery('#main-video video').prop('muted', true);
      },
      addClickListener: function(videoElement) {
        videoElement.addEventListener('click', function () {
          const mainVideo = jQuery('#main-video video').get(0);
          if (mainVideo.srcObject !== videoElement.srcObject) {
            mainVideo.srcObject = videoElement.srcObject;
          }
        });
      },
      appendUserData: function(videoElement, connection) {
        const nodeId = connection.nickname ? 'main-videodata' : connection.connectionId;
        const dataNode = document.createElement('div');
        dataNode.className = "data-node";
        dataNode.id = "data-" + nodeId;
        videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
        this.addClickListener(videoElement);
      },
      startStream: function() {
        this.getSessionIdAndToken(() => {
          const OV = new OpenVidu();
          this.session = OV.initSession(this.sessionId);
          this.session.on('streamCreated', function (event) {
            const subscriber = this.session.subscribe(event.stream, 'video-container');
            subscriber.on('videoElementCreated', function (event) {
              this.appendUserData(event.element, subscriber.stream.connection);
            });
          });
          this.session.on('streamDestroyed', function (event) {
            // removeUserData(event.stream.connection);
          });
          this.session.connect(this.token, '{"clientData": "' + this.$store.getters.loggedUser.username + '"}', (error) => {
            if (!error) {
              if (this.isOrganiser) {
                const publisher = OV.initPublisher('video-container', {
                  audio: true,        // Whether you want to transmit audio or not
                  video: true,        // Whether you want to transmit video or not
                  audioActive: true,  // Whether you want to start the publishing with your audio unmuted or muted
                  videoActive: true,  // Whether you want to start the publishing with your video enabled or disabled
                  quality: 'MEDIUM',  // The quality of your video ('LOW', 'MEDIUM', 'HIGH')
                  screen: false       // true to get your screen as video source instead of your camera
                });
                publisher.on('videoElementCreated', (event) => {
                  this.initMainVideo(event.element);
                  jQuery(event.element).prop('muted', true); // Mute local video
                });
                this.session.publish(publisher);
              } else {
                console.warn('You don\'t have permissions to publish');
              }
              this.isPlaying = true;
            } else {
              console.warn('There was an error connecting to the session:', error.code, error.message);
            }
          });
          return false;
        });
      }
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
    /*width: 33%;*/
  }
  .has-navbar {
    padding-top: 52px;
  }
  .video-placeholder {
    background: url("../../public/webcam.png") no-repeat center center;
    width: 67%;
    height: auto;
  }
  .webcam-lookup {
    width: 33%;
  }
  .videos {
    display: flex;
    flex-direction: row;
    padding-top: 10px;
  }
</style>