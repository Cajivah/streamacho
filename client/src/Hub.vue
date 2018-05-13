<template>
  <div id='app' class="has-navbar-fixed-top">
    <navigation></navigation>
    <div id="top"/>
    <router-view></router-view>
    <app-footer></app-footer>
  </div>
</template>

<script>
import Navigation from '@/common/Navigation';
import AppFooter from '@/common/AppFooter';

export default {
  name: 'app',
  components: {
    Navigation,
    AppFooter,
  }
};
</script>
