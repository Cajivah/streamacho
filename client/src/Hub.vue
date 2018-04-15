<template>
  <div id='app' class="has-navbar-fixed-top">
    <navigation></navigation>
    <router-view></router-view>
  </div>
</template>

<script>
import Navigation from '@/common/Navigation';

export default {
  name: 'app',
  components: {
    Navigation
  }
};
</script>
