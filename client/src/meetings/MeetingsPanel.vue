<template>
  <div>
    <div class="search field has-addons">
      <p class="control is-expanded">
        <input
          v-model="searchQuery"
          type="text"
          class="input"
          placeholder="Find stream"
        >
      </p>
      <p class="control">
        <button 
          class="button" 
          @click="searchMeetings">Search</button>
      </p>
    </div>
    <meetings-list :meetings="meetings" />
  </div>
</template>

<script>
import MeetingsList from '@/meetings/MeetingsList';
import { FETCH_ROOMS } from '../store/actions.type';

export default {
  name: 'MeetingsPanel',
  components: {
    MeetingsList,
  },
  props: {
    organisedOnly: {
      default: false,
      type: Boolean
    }
  },
  data() {
    return {
      searchQuery: '',
    }
  },
  computed: {
    headerText() {
      return this.organisedOnly ? 'My rooms' : 'Rooms';
    },
    meetings() {
      return this.$store.getters.meetings;
    }
  },
  mounted() {
    this.searchMeetings();
  },
  methods: {
    searchMeetings() {
      this.$store.dispatch(FETCH_ROOMS, this.searchQuery);
    }
  }
}
</script>

<style scoped>
  .search {
    margin: 40px 0;
  }
</style>