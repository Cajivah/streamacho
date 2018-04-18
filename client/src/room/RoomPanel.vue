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
            <a class="button is-danger" @click="disconnect" v-if="isPlaying">Leave stream</a>
          </div>
        </div>
      </div>
    </div>
    <div class="container mt-2">
      <div id="main-video" class="video-placeholder">
        <div class="stream-initializer" v-if="!isPlaying">
          <a class="button is-circle-300 is-primary" @click="startStream" v-if="isOrganiser">
            <div class="stream-initializer-prompt" >
              <h5 class="is-size-1 has-text-weight-bold">Start</h5>
              <h6 class="is-size-4">streaming</h6>
            </div>
          </a>
          <a class="button is-circle-300 is-primary" @click="joinStream" v-else>
            <div class="stream-initializer-prompt">
              <h5 class="is-size-1 has-text-weight-bold">Join</h5>
              <h6 class="is-size-4">stream</h6>
            </div>
          </a>
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
  import {CREATE_TRANSMISSION, DESTROY_TRANSMISSION, JOIN_TRANSMISSION} from "@/store/actions.type";
  import store from '@/store'
  import { showErrorToasts } from "@/ToastHandler";
  import { FETCH_SELECTED_ROOM } from "@/store/actions.type";
  import * as dateformat from "dateformat";
  import {ERASE_TRANSMISSION} from "../store/actions.type";

  export default {
    name: "RoomPanel",
    data() {
      return {
        isPlaying: false,
      }
    },
    beforeRouteEnter(to, from, next) {
      store.dispatch(FETCH_SELECTED_ROOM, to.params.id)
        .then(() => next())
    },
    beforeDestroy() {
      this.isOrganiser && this.isPlaying ? this.destroyStream() : this.disconnect();
    },
    computed: {
      ...mapGetters([
        'selectedRoom',
        'loggedUser',
      ]),
      isOrganiser() {
        return this.loggedUser.username === this.selectedRoom.organiser;
      },
      formattedStartAt() {
        return dateformat(new Date(this.selectedRoom.startAt), 'ddd mmm dd yyyy HH:MM');
      }
    },
    methods: {
      startStream() {
        this.$store.dispatch(CREATE_TRANSMISSION, { roomId: this.selectedRoom.id })
          .then(() => this.isPlaying = true)
          .catch(showErrorToasts);
      },
      joinStream() {
        this.$store.dispatch(JOIN_TRANSMISSION, { roomId: this.selectedRoom.id })
          .then(() => this.isPlaying = true)
          .catch(showErrorToasts);
      },
      destroyStream() {
        this.$store.dispatch(DESTROY_TRANSMISSION, { roomId: this.selectedRoom.id })
          .then(() => this.disconnect())
          .catch(showErrorToasts);
      },
      disconnect() {
        this.$store.dispatch(ERASE_TRANSMISSION)
          .then(() => {
              this.isPlaying = false;
            }
          );
      }
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
  }
  .stream-initializer {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .stream-initializer-prompt {
    display: flex;
    flex-direction: column;
    justify-content: center;
    line-height: 36px;
  }
  .is-circle-300 {
    display: flex;
    flex-direction: column;
    align-items: center;
    border: none;
    text-align: center;
    border-radius: 50%;
    height: 300px;
    line-height: 300px;
    width: 300px;
  }
</style>