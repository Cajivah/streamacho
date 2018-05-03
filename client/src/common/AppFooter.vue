<template>
  <footer class="footer">
    <div class="container">
        <div class="columns">
          <div class="column is-flex is-align-center is-justify-start">
            <p class="is-whitespace-preserve">Handcrafted with &#128170;️ in Wrocław | &copy;2018</p>
          </div>
          <div class="column is-flex is-align-center is-justify-center">
            <p class="has-text-primary is-size-2 has-text-weight-semibold">Streamacho</p>
          </div>
          <div class="column is-flex is-align-center is-justify-end">
            <div v-if="!isAuthenticated" class="is-flex is-align-center">
              <p class="mb-0 mr-1 is-size-5">Interested?</p>
              <a class=""></a>
              <router-link to="/register" class="button is-primary is-outlined is-large">
                Sign up!
              </router-link>
            </div>
            <div v-else class="is-flex is-content-center is-justify-end is-align-center">
              <i>We're glad to have you here!</i>
            </div>
          </div>
        </div>
      </div>
  </footer>
</template>

<script>
  import { mapGetters } from 'vuex';

  export default {
    name: "Footer",
    computed: {
      ...mapGetters([
        'isAuthenticated',
      ]),
    }
  }
</script>

<style scoped>
  .footer {
    padding-top: 2rem;
    padding-bottom: 3rem;
    height: 150px;
  }
</style>