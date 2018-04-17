<template>
  <form class='container create-room-form-container' @submit.prevent="onSubmit()">
    <h1 class='is-large has-text-weight-semibold has-text-grey-darker create-room-header'>New Room</h1>
    <div class="field create-room-name">
      <label class="label">Name</label>
      <div class="control">
        <input 
        class="input" name="name" placeholder="room name" v-model="room.name" v-validate="'required|min:3'">
      </div>
      <p class="help is-danger">{{ errors.first('name') }}</p>
    </div>
    <div class="field create-room-description">
      <label class="label">Description</label>
      <div class="control">
        <textarea 
        class="textarea create-room-textarea" name="description" placeholder="description" v-model="room.description" v-validate="'required'"></textarea>
      </div>
      <p class="help is-danger">{{ errors.first('description') }}</p>
    </div>
    <div class="field create-room-startAt">
      <label class="label">Starts at</label>
      <div class="control has-icons-left has-icons-left">
        <input type="date" class="input" name="startAt" v-model="room.startAt" v-validate="'required'">
        <span class="icon is-small is-left">
          <i class="fa fa-user"></i>
        </span>
      </div>
      <p class="help is-danger">{{ errors.first('startAt') }}</p>
    </div>
       <div class="field create-room-logo">
        <label class="file-label">
          <div class="control">
            <input class="file-input" type="file" name="logo" accept="image/*" @change="processFile($event)">
            <span class="file-cta">
              <span class="file-icon">
                <i class="fa fa-upload"></i>
              </span>
              <span class="file-label">
                Choose a logo
              </span>
            </span>
            <img class="create-room-image" :src="image" />
          </div>
        </label>
      </div>
        <div class="field create-room-tags">
      <label class="label">Tags</label>
      <div class="control has-icons-left has-icons-left">
        <input class="input" name="tags" placeholder="tags" v-model="room.tags">
        <span class="icon is-small is-left">
          <i class="fa fa-user"></i>
        </span>
      </div>
    </div>
    <button class="button is-primary create-room-submit">Create room</button>
  </form>
</template>

<script>
  import { CREATE_ROOM } from '../store/actions.type';
  export default {
    name: "AddMeeting",
    data() {
      return {
        room: {
          name: "",
          description: "",
          startAt: null,
          tags: []
        },
        image: null
      };
    },
    methods: {
      onSubmit() {
        this.$validator.validateAll().then(result => {
          if (!result) {
            return;
          }
          this.room.startAt = this.parseDate(this.room.startAt);
          this.room.tags = this.room.tags.split(','); // todo temporary workaround
          this.$store
            .dispatch(CREATE_ROOM, { ...this.room })
            .then(() => this.$router.push({ name: 'landingPage' }));
          this.resetForm();
        });
      },
      resetForm() {
        this.room.name = '';
        this.room.description = '';
        this.room.startAt = '';
        this.room.tags = [];
        this.$validator.reset();
      },
      parseDate(dateString) {
        const chunk = dateString.split(/\D/);
        console.log(chunk);
        return new Date(chunk[0], --chunk[1], chunk[2]);
      },
      processFile(event) {
        this.image = URL.createObjectURL(event.target.files[0]);
      }
    }
  };
</script>
<style scoped>
  .create-room-form-container {
    margin-top: 60px;
    display: grid;
    grid-column-gap: 20px;
    grid-row-gap: 10px;
    grid-template-areas:
        "header . . ."
        "name name name name"
        "description description description description"
        "description description description description"
        "description description description description"
        "startAt startAt startAt logo"
        ". . . logo"
        "tags tags tags tags"
        ". . . submit";
  }

  .create-room-textarea {
    resize: none;
  }

  .create-room-header {
    grid-area: header;
  }

  .create-room-name {
    grid-area: name;
  }

  .create-room-description {
    grid-area: description;
  }

  .create-room-startAt {
    grid-area: startAt;
  }

  .create-room-time {
    grid-area: time;
  }

  .create-room-logo {
    grid-area: logo;
    justify-self: center;
  }

  .create-room-tags {
    grid-area: tags;
  }

  .create-room-submit {
    grid-area: submit;
  }

  .create-room-image {
    display: block;
    max-height: 200px;
    max-width: 200px;
  }
</style>
