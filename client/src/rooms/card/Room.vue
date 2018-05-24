<template>
  <div class="card">
    <div 
      :class="cardColor"
      class="hero"
    >
      <room-status :room="room"/>
      <figure class="image is-96x96">
        <img-placeholder
          :string="room.name"
          :url="room.logoUrl"
        />
      </figure>
    </div>

    <div class="card-content">
      <div class="info">
        <p class="name title is-6">{{ room.name }}</p>
        <p class="date">
          <i class="fa fa-calendar"/>
          {{ startAt }}
        </p>
        <p class="organiser">
          <i class="fa fa-user"/>
          {{ room.organiser }}
        </p>
      </div>
      <div class="content">
        <p class="description">{{ truncateText(room.description) }}</p>
      </div>
      <div class="tags">
        <span
          v-for="tag in visibleTags"
          :key="tag"
          class="tag is-info"
        >
          {{ tag }}
        </span>
        <span 
          v-if="hiddenTags.length > 0"
          class="show-more-tags tag is-primary is-unselectable"
          @click="showMoreTags"
        >
          +{{ hiddenTags.length }} other
        </span>
      </div>
    </div>


    <footer class="card-footer">
      <a
        :class="cardColor"
        class="card-footer-item has-text-white"
        @click="joinRoom"
      >
        <i class="details-icon fa fa-info-circle fa-lg"/>
        <span class="is-unselectable">More details</span>
      </a>
    </footer>
  </div>
</template>

<script>
import { truncate } from 'lodash';
import dateFormat from 'dateformat';
import ImgPlaceholder from '@/common/ImgPlaceholder';
import RoomStatus from './RoomStatus';

const STATUSES = {
  PLANNED: {
    cardColors: ['has-background-info', 'has-text-white'],
  },
  LIVE: {
    cardColors: ['has-background-success', 'has-text-white'],
  },
  COMPLETED: {
    cardColors: ['has-background-primary', 'has-text-white'],
  },
  WASTED: {
    cardColors: ['has-background-light', 'has-text-dark'],
  }
};

export default {
  name: 'Room',
  components: {
    RoomStatus,
    ImgPlaceholder
  },
  props: {
    room: {
      type: Object,
      default: () => ({
        tags: [],
        status: 'wasted'
      })
    }
  },
  data: () => ({
    numberOfVisibleTags: 3,
  }),
  computed: {
    visibleTags() {
      return this.room.tags.slice(0, this.numberOfVisibleTags);
    },
    hiddenTags() {
      return this.room.tags.slice(this.numberOfVisibleTags);
    },
    startAt() {
      return dateFormat(this.room.startAt, 'mmmm dS, yyyy, h:MM TT');
    },
    cardColor() {
      return STATUSES[this.room.status].cardColors;
    }
  },
  methods: {
    joinRoom() {
      this.$router.push(`/rooms/${this.room.id}`);
    },
    truncateText(text) {
      return truncate(text, {
        'length': 150,
        'separator': ' '
      });
    },
    showMoreTags() {
      this.numberOfVisibleTags += 3;
    }
  }
};
</script>

<style scoped>
  .card {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }

  .hero {
    height: 100px;
    position: relative;
    margin-bottom: 40px;
  }

  .image {
    position: absolute;
    left: 0;
    right: 0;
    margin: 0 auto;
    bottom: -50%;
  }

  .info {
    margin-bottom: 30px;
  }

  .name {
    text-align: center;
    margin-bottom: 0;
  }

  .date {
    text-align: center;
    font-style: italic;
    font-size: 0.9rem;
    margin: 4px 0;
  }

  .organiser {
    text-align: center;
    font-style: italic;
    font-size: 0.9rem;
    margin: 4px 0;
  }

  .description {
    text-align: justify;
    text-justify: inter-word;
    border-top: 1px solid #eee;
    border-bottom: 1px solid #eee;
    padding: 20px 0;
  }

  .show-more-tags {
    cursor: pointer;
  }

  .card-footer {
    margin-top: auto;
  }

  .details-icon {
    margin-right: 10px;
  }
</style>
