<template>
  <form class='container create-room-form-container' @submit.prevent="onSubmit()">
    <h1 class='is-large has-text-weight-semibold has-text-grey-darker create-room-header'>New Room</h1>
    <div class="field create-room-title">
      <label class="label">Title</label>
      <div class="control">
        <input 
        class="input" name="title" placeholder="room title" v-model="room.title" v-validate="'required|min:3'">
      </div>
      <p class="help is-danger">{{ errors.first('title') }}</p>
    </div>
    <div class="field create-room-description">
      <label class="label">Description</label>
      <div class="control">
        <textarea 
        class="textarea create-room-textarea" name="title" placeholder="description" v-model="room.description" v-validate="'required'"></textarea>
      </div>
      <p class="help is-danger">{{ errors.first('description') }}</p>
    </div>
    <div class="field create-room-date">
      <label class="label">Date</label>
      <div class="control has-icons-left has-icons-left">
        <input type="date" class="input" name="date" placeholder="date" v-model="room.date" v-validate="'required|date_format:dd-MM-yyyy'">
        <span class="icon is-small is-left">
          <i class="fa fa-user"></i>
        </span>
      </div>
      <p class="help is-danger">{{ errors.first('date') }}</p>
    </div>
        <div class="field create-room-time">
      <label class="label">Time</label>
      <div class="control has-icons-left has-icons-left">
        <input type="date" class="input" name="time" placeholder="time" v-model="room.time" v-validate="'required|date_format:dd-MM-yyyy'">
        <span class="icon is-small is-left">
          <i class="fa fa-user"></i>
        </span>
      </div>
      <p class="help is-danger">{{ errors.first('time') }}</p>
    </div>
        <div class="field create-room-logo">
      <label class="label">Logo</label>
      <div class="control has-icons-left has-icons-left">
        <input class="file-input" type="file" name="logo" placeholder="logo">
      </div>
    </div>
        <div class="field create-room-tags">
      <label class="label">Tags</label>
      <div class="control has-icons-left has-icons-left">
        <input class="input" name="tags" placeholder="tags" v-model="room.date">
        <span class="icon is-small is-left">
          <i class="fa fa-user"></i>
        </span>
      </div>
    </div>
    <button class="button is-primary create-room-submit">Add</button>
  </form>
</template>

<script>
export default {
  name: "AddMeeting",
  data() {
    return {
      room: {
        title: "",
        description: "",
        date: new Date(),
        time: new Date(),
        tags: []
      }
    };
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        this.$emit("onAddRoom", {
          ...this.room
        });
        this.resetForm();
      });
    },
    formatDate(date) {},
    resetForm() {
      this.room.title = "";
      this.room.description = "";
      this.room.date = new Date();
      this.room.time = new Date();
      this.room.tags = [];
      this.$validator.reset();
    }
  }
};
</script>
<style scoped>
.create-room-form-container {
  margin-top: 52px;
  display: grid;
  grid-column-gap: 20px;
  grid-row-gap: 10px;
  grid-template-areas:
    "header . . ."
    "title title title title"
    "description description description description"
    "description description description description"
    "description description description description"
    "date date date logo"
    "time time time logo"
    "tags tags tags tags"
    ". . . submit";
}

.create-room-textarea {
  resize: none;
}

.create-room-header {
  grid-area: header;
}

.create-room-title {
  grid-area: title;
}

.create-room-description {
  grid-area: description;
}

.create-room-date {
  grid-area: date;
}

.create-room-time {
  grid-area: time;
}

.create-room-logo {
  grid-area: logo;
}

.create-room-tags {
  grid-area: tags;
}

.create-room-submit {
  grid-area: submit;
}
</style>
