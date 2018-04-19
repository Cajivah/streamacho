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
<style>

.landing-page {
  padding-top: 52px;
}
</style>