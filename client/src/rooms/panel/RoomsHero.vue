<template>
  <div
    :class="{'hero--shrink': shrink}"
    class="hero has-background-primary"
  >
    <h1 
      :class="{'title--shrink': shrink}"
      class="title"
    >
      Let's explore <strong class="has-text-info">rooms</strong>.
    </h1>
    <search-bar
      class="search-bar"
      placeholder="I'm looking for room about..."
      @search="search"
      @clear="clear"
    />
  </div>
</template>

<script>
import SearchBar from '@/rooms/panel/SearchBar';

export default {
  name: 'RoomHero',
  components: {
    SearchBar,
  },
  data: () => ({
    shrink: false,
  }),
  methods: {
    search(query) {
      this.shrink = true;
      this.$emit('search', query);
    },
    clear() {
      this.shrink = false;
      this.$emit('search');
    }
  }
}
</script>

<style scoped>
  .hero {
    min-height: 60vh;
    padding-top: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: 0.4s;
  }

  .hero--shrink {
    min-height: 30vh;
  }

  .search-bar {
    width: 60%;
  }

  .title {
    color: white;
    font-size: 100px;
    transition: 0.4s;
    margin: 0;
  }

  .title--shrink {
    font-size: 0;
    transition: 0.4s;
    opacity: 0;
  }
</style>