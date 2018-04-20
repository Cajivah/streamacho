<template>
  <div class="card">
    <div class="card-header">
      <div class="meeting-icons">
        <div><i class="fa fa-comments"/></div>
        <div><i class="fa fa-microphone unavailable"/></div>
        <div><i class="fa fa-camera unavailable"/></div>
        <div><i class="fa fa-users"/></div>
      </div>
    </div>

    <div class="card-content">
      <div class="media">
        <div class="media-left">
          <figure class="image is-48x48">
            <img
              src="https://bulma.io/images/placeholders/96x96.png"
              alt="Placeholder image">
          </figure>
        </div>
        <div class="media-content">
          <p class="title is-4">{{ meeting.name }}</p>
          <p class="subtitle is-6">{{ meeting.organiser }}</p>
        </div>
      </div>
      <div class="content">
        {{ meeting.description }}
        <div class="tags">
          <span
            v-for="tag in meeting.tags"
            :key="tag"
            class="tag is-rounded is-primary"
          >
            {{ tag }}
          </span>
          <!--<span class="tag is-rounded is-info">+3 other</span>-->
        </div>
      </div>
      <!--      <time
        class="datetime"
        datetime="2016-1-1"
      >
        {{ meeting.startAt }}
      </time>-->
    </div>

    <footer class="card-footer">
      <a
        class="card-footer-item"
        @click="joinRoom">Join</a>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'Meeting',
  props: {
    meeting: {
      type: Object,
      required: true,
    }
  },
  methods: {
    joinRoom() {
      this.$router.push(`/meeting/${this.meeting.id}`);
    }
  }
};
</script>

<style scoped>
  .card {
    width: 300px;
    display: flex;
    flex-direction: column;
    margin-bottom: 40px;
  }

  .tags {
    margin-top: 20px;
  }

  .meeting-icons {
    display: flex;
    justify-content: space-around;
    padding: 10px;
    width: 100%;
  }

  .card-content {
    position: relative;
  }

  .datetime {
    position: absolute;
    bottom: 15px;
    right: 15px;
    color: #aaa;
  }
  .card-footer {
    margin-top: auto
  }

  .unavailable {
    color: #ddd;
  }
</style>
