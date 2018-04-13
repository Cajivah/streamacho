<template>
  <div id='app'>
    <router-view></router-view>
  </div>
</template>

<script>
import {LOGOUT} from "./store/actions.type";

export default {
  name: 'app',
  created() {
    this.axios.interceptors.response.use(
      response => response,
      error => {
        const status = error.response.status;
        if (status === 401 || status === 403) {
          this.$store.dispatch(LOGOUT)
            .then(() => this.$router.push({name: 'login'}));
        }
        return Promise.reject(error);
      }
    );
  }
};
</script>
