<template>
  <div class="landing-page-wrap">
    <div class="landing-page container">
      <meetings-landing></meetings-landing>
    </div>
  </div>
</template>

<script>
import {FETCH_LOGGED_USER} from "../store/actions.type";
import MeetingsLanding from '@/meetings/MeetingsLanding';

export default {
  name: 'landing-page',
  components: {
    MeetingsLanding
  },
  mounted: function() {
    this.$nextTick(function() {
      this.$store.dispatch(FETCH_LOGGED_USER);
    })
  }
};
</script>

<style scoped>
.landing-page-wrap {
  height: 100vh;
}

.landing-page-wrap::after {
  content: '';
  background-image: url('../assets/landingPage.jpg');
  opacity: 0.5;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  position: absolute;
  z-index: -1;
}

.landing-page {
  display: flex;
  padding-top: 52px;
  height: 100vh;
  justify-content: center;
}

.l {
  height: 100vh;
}
</style>