<template>
  <nav 
    class="navbar is-fixed-top is-primary"
    role="navigation"
    aria-label="main navigation">
    <div class="container">
      <div class="navbar-brand">
        <router-link
          to="/"
          class="has-text-weight-semibold is-size-4 is-flex">
          <img
            src="../assets/logo_white.svg"
            width="150px"
          >
        </router-link>
        <div 
          class="navbar-burger burger" 
          data-target="navbar-contents">
          <span/>
          <span/>
          <span/>
        </div>
      </div>
      <div 
        class="navbar-menu" 
        id="navbar-contents">
        <div
          v-if="this.$store.getters.isAuthenticated"
          class="navbar-end" >
          <router-link 
            to="/create-room" 
            class="navbar-item button is-primary is-inverted is-outlined has-text-weight-semibold mr-1">
            <i class="fa fa-plus mr-1"/>Create room
          </router-link>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-active has-text-weight-semibold has-text-white stays-white">
              {{ userGreeter }}
            </a>
            <div class="navbar-dropdown">
              <router-link 
                class="navbar-item" 
                to="/my">
                <i class="fa fa-tv mr-1 fa-lg"/>My rooms
              </router-link>
              <hr class="navbar-divider">
              <a 
                class="navbar-item has-text-weight-semibold" 
                @click="handleLogout">
                <i class="fa fa-sign-out mr-1 fa-lg"/>Logout
              </a>
            </div>
          </div>
        </div>
        <div
          v-else
          class="navbar-end" >
          <router-link 
            class="navbar-item is-size-6"
            to="login">Login</router-link>
          <router-link 
            class="navbar-item is-size-6"
            to="register">Register</router-link>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import { LOGOUT } from '../store/actions.type';

export default {
  name: 'Navigation',
  computed: {
    userGreeter: function() {
      return `Hi, ${this.$store.getters.loggedUser.username}!`;
    }
  },
  methods: {
    handleLogout: function() {
      this.$store.dispatch(LOGOUT)
        .then(() => this.$router.push({ name: 'login' }));
    }
  },
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
