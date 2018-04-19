<template>
  <div
    class="content form-view">
    <div class="form-container">
      <h2 class="is-large">Account activation</h2>
      <p>{{ message }}</p>
      <a
        v-if="!isFailed"
        :class="{'is-loading': isPending}"
        :disabled="isPending"
        class="button is-primary full-width"
        @click.prevent="goToLoginPage"
      >
        Great! Take me to login page
      </a>
      <a
        v-else
        class="button is-danger full-width"
        @click.prevent="tryAgain"
      >
        Try again
      </a>
    </div>
  </div>
</template>

<script>
import { ACTIVATE_ACCOUNT } from '../store/actions.type';

const PENDING = 'pending';
const DONE = 'done';
const FAILED = 'failed';

export default {
  name: 'Activate',
  props: {
    token: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      activationState: PENDING,
    }
  },
  computed: {
    message() {
      switch(this.activationState) {
      case PENDING:
        return 'Please wait...';
      case DONE:
        return 'Your account has been activated.';
      default:
        return 'Oops! Something went wrong.';
      }
    },
    isPending() {
      return this.activationState === PENDING;
    },
    isFailed() {
      return this.activationState === FAILED;
    }
  },
  mounted() {
    this.activate();
  },
  methods: {
    goToLoginPage() {
      this.$router.push('/login');
    },
    tryAgain() {
      this.activationState = PENDING;
      this.activate();
    },
    activate() {
      this.$store.dispatch(ACTIVATE_ACCOUNT, this.token)
        .then(() => this.activationState = DONE)
        .catch(() => this.activationState = FAILED);
    }
  }
}
</script>

<style scoped>
  .full-width {
    width: 100%;
  }
</style>