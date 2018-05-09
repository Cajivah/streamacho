<template>
  <nav class="navbar is-fixed-top is-primary" role="navigation" aria-label="main navigation">
    <div class="container">
      <div class="navbar-brand">
        <a role="button" class="navbar-burger" data-target="navMenu" aria-label="menu" aria-expanded="false">
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>
      <div class="navbar-menu">
        <div class="navbar-brand">
          <router-link to="/" class="has-text-weight-semibold is-size-4 stays-white">Streamacho</router-link>
        </div>
        <div class="navbar-end" v-if="this.$store.getters.isAuthenticated" >
          <router-link to="/create-room" class="button is-primary is-inverted is-outlined has-text-weight-semibold mr-1">
            <i class="fa fa-plus mr-1"></i>Create room
          </router-link>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-active has-text-weight-semibold has-text-white stays-white">
              {{ userGreeter }}
            </a>
            <div class="navbar-dropdown">
              <router-link class="navbar-item" to="/my">
                <i class="fa fa-tv mr-1 fa-lg"></i>My rooms
              </router-link>
              <hr class="navbar-divider">
              <a class="navbar-item has-text-weight-semibold" v-on:click="handleLogout">
                <i class="fa fa-sign-out mr-1 fa-lg"></i>Logout
              </a>
            </div>
          </div>
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
  import { LOGOUT } from "../store/actions.type";

  export default {
    name: "navigation",
    methods: {
      toggleBurger() {
        const target = $el.dataset.target;
        const $target = document.getElementById(target);
        $el.classList.toggle('is-active');
        $target.classList.toggle('is-active');
      },
      handleLogout: function() {
        this.$store.dispatch(LOGOUT)
          .then(() => this.$router.push({ name: 'login' }));
      }
    },
    computed: {
      userGreeter: function() {
        return `Hi, ${this.$store.getters.loggedUser.username}!`;
      }
    }
  };
</script>

<style scoped>
  .navbar-brand {
    align-items: center;
  }
  .navbar-end {
    align-items: center;
  }
  .navbar-link {
    background-color: transparent !important;
  }

  .stays-white:hover,
  .stays-white:checked,
  .stays-white {
    color: white!important;
  }
</style>
