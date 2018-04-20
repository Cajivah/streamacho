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
<style>

.landing-page {
  padding-top: 52px;
}
</style>