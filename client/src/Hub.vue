<template>
  <div id='app' class="has-navbar-fixed-top">
    <navigation></navigation>
    <router-view></router-view>
  </div>
</template>

<script>
import Navigation from '@/common/Navigation';
import {FETCH_LOGGED_USER} from "./store/actions.type";

export default {
  name: 'app',
  components: {
    Navigation
  },
};
</script>
