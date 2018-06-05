<template>
  <div 
    id="app" 
    class="has-navbar-fixed-top">
    <navigation/>
    <div id="top"/>
    <router-view/>
    <app-footer/>
  </div>
</template>

<script>
import Navigation from './common/Navigation';
import AppFooter from './common/AppFooter';

export default {
  name: 'App',
  components: {
    Navigation,
    AppFooter,
  }
};
</script>
