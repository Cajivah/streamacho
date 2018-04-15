<template>
  <nav class="navbar is-fixed-top transparent" role="navigation" aria-label="main navigation">
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
        <div class="navbar-start">
          <router-link class="navbar-item" to="/">My rooms</router-link>
          <router-link class="navbar-item" to="createRoom">Create room</router-link>
        </div>
        <div class="navbar-end">
          <router-link class="navbar-item" to="register">Register</router-link>
          <router-link class="navbar-item" to="login">Login</router-link>
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
.transparent {
  background-color: rgba(255, 255, 255, 0.5);
}
</style>
