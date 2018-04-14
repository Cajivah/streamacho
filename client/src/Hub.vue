<template>
  <div id='app'>
    <navigation></navigation>
    <landing-page></landing-page>
  </div>
</template>

<script>
import Navigation from '@/common/Navigation';
import LandingPage from '@/common/LandingPage';

export default {
  name: 'app',
  components: {
    Navigation,
    LandingPage
  }
};
</script>
