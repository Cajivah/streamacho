<template>
  <div id='app'>
    <navigation></navigation>
    <div class='container'>
      <meetings-hub></meetings-hub>
    </div>
  </div>
</template>

<script>
import MeetingsHub from '@/meetings/MeetingsHub';
import Navigation from '@/common/Navigation';

export default {
  name: 'app',
  components: {
    MeetingsHub,
    Navigation
  }
}
</script>
