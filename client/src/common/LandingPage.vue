<template>
  <div class="landing-page">
    <rooms-panel />
  </div>
</template>

<script>
import RoomsPanel from '@/rooms/panel/RoomsPanel';
import { FETCH_LOGGED_USER } from '@/store/actions.type';

export default {
  name: 'LandingPage',
  components: {
    RoomsPanel
  },
  mounted: function() {
    this.$nextTick(function() {
      this.$store.dispatch(FETCH_LOGGED_USER);
    })
  }
};
</script>

<style scoped>
  .landing-page {
    min-height: 100vh;
  }
</style>