<template>
  <form @submit.prevent="onSubmit()">
    <div class="create-room-container">
      <div class="hero has-background-primary" />

      <div class="card-wrapper">
        <h1 class="title">
          Create new room
        </h1>

        <div class="card">
          <div class="card-hero has-background-info">
            <room-image-dropzone class="room-image-dropzone"/>
          </div>
          <div class="card-content">
            <div class="content">
              <b-field
                :message="errors.first('name')"
                :type="errors.first('name') && 'is-danger'"
                label="Name"
              >
                <b-input
                  v-validate="'required|min:3'"
                  v-model="room.name"
                  name="name"
                  placeholder="Enter room name"
                />
              </b-field>
              <b-field
                :message="errors.first('description')"
                :type="errors.first('description') && 'is-danger'"
                label="Description"
              >
                <b-input
                  v-validate="'required'"
                  v-model="room.description"
                  name="description"
                  placeholder="Describe your room here"
                  maxlength="250"
                  type="textarea"
                />
              </b-field>
              <b-field
                :message="errors.first('date')"
                :type="errors.first('date') && 'is-danger'"
                label="Stream can start at"
              >
                <input
                  v-validate="'required'"
                  v-model="room.date"
                  :class="errors.first('date') && 'is-danger'"
                  type="date"
                  class="input"
                  name="date"
                  placeholder="Click to select..."
                >
              </b-field>
              <b-field
                :message="errors.first('time')"
                :type="errors.first('time') && 'is-danger'"
              >
                <input
                  v-validate="'required'"
                  v-model="room.time"
                  :class="{ 'is-danger': errors.first('time') }"
                  type="time"
                  name="time"
                  class="input"
                >
              </b-field>
              <b-field label="Tags">
                <b-taginput
                  v-model="room.tags"
                  name="tags"
                  type="is-primary"
                  placeholder="Web Development, Java 7, Hibernate, Spring Framework..."
                />
              </b-field>
            </div>
          </div>

          <footer class="card-footer">
            <a
              href="#"
              class="card-footer-item"
              @click.prevent="resetForm"
            >
              <i class="icon fa fa-trash"/>
              <span class="is-unselectable">Clear form</span>
            </a>
            <a
              href="#"
              class="card-footer-item has-background-info has-text-light"
              @click.prevent="onSubmit"
            >
              <i class="fa fa-plus icon"/>
              <span class="is-unselectable">Create room</span>
            </a>
          </footer>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import { CREATE_ROOM } from '../store/actions.type';
import RoomImageDropzone from '../common/RoomImageDropzone';

export default {
  name: 'AddRoom',
  components: {
    RoomImageDropzone,
  },
  data() {
    return {
      room: {
        name: '',
        description: '',
        date: null,
        time: null,
        tags: []
      },
      image: null,
    };
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }

        const room = {
          ...this.room,
          startAt: this.combineDateAndTime(this.room.date, this.room.time)
        };

        delete room.date;
        delete room.time;

        this.$store
          .dispatch(CREATE_ROOM, room)
          .then(() => this.$router.push({ name: 'landingPage' }));
        this.resetForm();
      });
    },
    resetForm() {
      this.room.name = '';
      this.room.description = '';
      this.room.date = '';
      this.room.time = '';
      this.room.tags = [];
      this.$validator.reset();
    },

    combineDateAndTime(dateString, timeString) {
      const date = this.parseDate(dateString);
      const time = this.parseTime(timeString);
      date.setHours(time.getHours());
      date.setMinutes(time.getMinutes());
      return date;
    },

    parseDate(dateString) {
      const chunk = dateString.split(/\D/);
      return new Date(chunk[0], --chunk[1], chunk[2]);
    },

    parseTime(timeString) {
      const chunk = timeString.split(/\D/);
      return new Date(1970, 0, 1, chunk[0], chunk[1]);
    },

    processFile(event) {
      this.image = URL.createObjectURL(event.target.files[0]);
    }
  }
};
</script>
<style scoped>
  .create-room-container {
    position: relative;
  }

  .title {
    color: white;
    font-size: 80px;
    transition: 0.4s;
    margin: 0;
    text-align: center;
  }

  .hero {
    position: absolute;
    width: 100%;
    height: 60vh;
  }

  .room-image-dropzone {
    position: absolute;
    left: 0;
    right: 0;
    margin: 0 auto;
    bottom: -50%;
  }

  .card-wrapper {
    position: relative;
    top: 80px;
    width: 50%;
    margin: 0 auto 150px;
  }

  .card-hero {
    position: relative;
    height: 260px;
    margin-bottom: 130px;
  }

  .icon {
    line-height: 1.5;
  }
</style>
