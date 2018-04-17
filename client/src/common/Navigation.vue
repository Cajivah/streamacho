<template>
  <nav class="navbar is-fixed-top background-primary" role="navigation" aria-label="main navigation">
    <div class="navbar-shadow"></div>
    <div class="container">
      <div class="navbar-brand">
        <a role="button" class="navbar-burger" data-target="navMenu" aria-label="menu" aria-expanded="false">
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>
      <div class="navbar-menu">
        <div class="navbar-end" v-if="this.$store.getters.isAuthenticated" >
          <router-link class="navbar-item" to="/">My meetings</router-link>
          <router-link class="navbar-item" to="createMeeting">Create meeting</router-link>
        </div>
        <div class="navbar-end" v-else >
          <router-link class="navbar-item" to="login">Login</router-link>
          <router-link class="navbar-item" to="register">Register</router-link>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
  export default {
    name: "navigation",
    methods: {
      toggleBurger() {
        const target = $el.dataset.target;
        const $target = document.getElementById(target);
        $el.classList.toggle('is-active');
        $target.classList.toggle('is-active');
      }
    }
  };
</script>

<style scoped>
</style>
