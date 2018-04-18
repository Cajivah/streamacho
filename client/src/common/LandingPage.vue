<template>
  <div class="landing-page-wrap">
    <div class="landing-page container">
      <meetings-landing></meetings-landing>
    </div>
  </div>
</template>

<script>
import MeetingsLanding from '@/meetings/MeetingsLanding';

export default {
  name: 'landing-page',
  components: {
    MeetingsLanding
  }
};
</script>

<style scoped>
.landing-page-wrap {
  height: 100vh;
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