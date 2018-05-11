<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import { LOGOUT, SAVE_PATH } from './store/actions.type';
import { isExcluded } from './config/httpConfigurer';

export default {
  name: 'App',
  created() {
    this.axios.interceptors.response.use(
      response => response,
      error => {
        const { fullPath, name } = this.$route;
        const { status } = error.response;
        if ((status === 401 || status === 403) && !isExcluded(name)) {
          this.$store.dispatch(LOGOUT)
            .then(() => this.$store.dispatch(SAVE_PATH, { afterLoginRedirect: fullPath }));
          this.$router.push({ name: 'login' });
        }
        return Promise.reject(error);
      }
    );
  }
};
</script>
