<template>
  <div id='app'>
    <router-view></router-view>
  </div>
</template>

<script>
import {LOGOUT} from "./store/actions.type";
import {isExcluded} from "./config/httpConfigurer";

export default {
  name: 'app',
  created() {
    this.axios.interceptors.response.use(
      response => response,
      error => {
        const status = error.response.status;
        const url = error.config.url;
        if ((status === 401 || status === 403) && !isExcluded(url)) {
          this.$store.dispatch(LOGOUT)
            .then(() => this.$router.push({name: 'login'}));
        }
        return Promise.reject(error);
      }
    );
  }
};
</script>
