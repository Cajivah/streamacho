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
          Reset Password
        </h2>
      </div>
      <div class="subsection is-fullwidth">
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-validate="'required|email'"
              v-model="resetPasswordForm.email"
              class="input"
              name="email"
              placeholder="Email">
            <span class="icon is-small is-left">
              <i class="fa fa-envelope"/>
            </span>
          </div>
          <p class="help is-danger">{{ errors.first("email") }}</p>
        </div>
        <div class="field">
          <button class="button is-primary is-fullwidth">Reset</button>
        </div>
      </div>
      <img
        src="../assets/logo_green.svg"
        class="logo">
    </div>
  </form>
</template>

<script>
import { REQUEST_RESET_PASSWORD } from '../store/actions.type';
import { showErrorToasts, showSuccessToasts } from '../ToastHandler';

export default {
  name: 'ResetPassword',
  props: {
    token: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      resetPasswordForm: {
        email: '',
      }
    };
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        const body = {
          ...this.resetPasswordForm,
        };
        const toast = () => showSuccessToasts({ message: 'Password reset token was sent to the provided email!' });
        this.$store.dispatch(REQUEST_RESET_PASSWORD, body)
          .then(toast)
          .catch(toast);
        this.resetPasswordForm.email = '';
        this.$validator.reset();
      });
    },
  }
}
</script>

<style scoped>
  .header {
    margin-bottom: 20px;
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