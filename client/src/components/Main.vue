<template>
  <div id="app">
    <navigation></navigation>
    <div class="container">
      <meetings-hub></meetings-hub>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import MeetingsHub from '@/components/meetings/MeetingsHub';
import Navigation from '@/components/Navigation';

export default {
  name: 'app',
  components: {
    MeetingsHub,
    Navigation
  }
}
</script>
