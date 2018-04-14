<template>
  <div id='app'>
    <navigation></navigation>
    <landing-page></landing-page>
    <create-room></create-room>
  </div>
</template>

<script>
import Navigation from '@/common/Navigation';
import LandingPage from '@/common/LandingPage';
import CreateRoom from '@/rooms/CreateRoom';

export default {
  name: 'app',
  components: {
    Navigation,
    LandingPage,
    CreateRoom
  }
};
</script>
