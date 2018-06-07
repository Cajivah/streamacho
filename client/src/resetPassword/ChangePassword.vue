<template>
  <form
    class="content form-view"
    @submit.prevent="onSubmit()">
    <div class="form-container auth-form">
      <div class="header">
        <h2 class="is-large has-text-weight-semibold has-text-grey-darker has-text-centered">
          <a
            class="return has-text-dark is-size-4"
            @click="$router.go(-1)"
          ><i class="fa fa-arrow-left"/></a>
          Choose new password
        </h2>
      </div>
      <div class="subsection is-fullwidth">
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-validate="{ required: true, min:8, regex: /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&\-])[A-Za-z\d$@$!%*?&\-]{8,}/ }"
              v-model="passwordPair.password"
              class="input"
              name="password"
              placeholder="New password"
              type="password">
            <span class="icon is-small is-left">
              <i class="fa fa-key"/>
            </span>
          </div>
          <p class="help is-danger">{{ parsePasswordError(errors) }}</p>
        </div>
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-validate="'required|confirmed:password'"
              v-model="passwordPair.matchingPassword"
              class="input"
              name="matchingPassword"
              placeholder="Repeat new password"
              type="password">
            <span class="icon is-small is-left">
              <i class="fa fa-key"/>
            </span>
          </div>
          <span class="help is-danger">{{ errors.first("matchingPassword") }}</span>
        </div>
        <div class="field">
          <button class="button is-danger is-fullwidth">Reset</button>
        </div>
      </div>
      <img
        src="../assets/logo_green.svg"
        class="logo">
    </div>
  </form>
</template>

<script>
import { RESET_PASSWORD } from '../store/actions.type';
import { showErrorToasts, showSuccessToasts } from '../ToastHandler';

export default {
  name: 'ChanagePassword',
  props: {
    token: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      passwordPair: {
        password: '',
        matchingPassword: ''
      }
    };
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        this.$store
          .dispatch(RESET_PASSWORD, { token: this.token, ...this.passwordPair })
          .then(()=> showSuccessToasts({ message: 'Successfully reset password!'}))
          .then(() => this.$router.push('login'))
          .catch(() => showErrorToasts({ message: 'Could not reset password!' }));
        this.passwordPair.password = '';
        this.passwordPair.matchingPassword = '';
        this.$validator.reset();
      });
    },
    parsePasswordError(err) {
      return err && err.firstByRule('password', 'regex')
        ? 'Password is not secure enough'
        : err.first('password');
    }
  }
}
</script>

<style scoped>
  .header {
    margin-bottom: 10px;
    position: relative;
    width: 100%;
    text-align: center;
  }
  .return {
    position: absolute;
    left: 0;
  }
  .logo {
    margin-top: 40px;
    width: 120px;
  }
</style>