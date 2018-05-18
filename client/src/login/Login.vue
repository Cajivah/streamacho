<template>
  <form 
    class="content form-view" 
    @submit.prevent="onSubmit()">
    <div class="form-container auth-form">
      <h1 class="is-large has-text-weight-semibold has-text-grey-darker">Login</h1>
      <div class="subsection is-fullwidth">
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-model="loginForm.username"
              class="input"
              name="username"
              placeholder="Username">
            <span class="icon is-small is-left">
              <i class="fa fa-user"/>
            </span>
          </div>
          <p class="help is-danger">{{ errors.first('username') }}</p>
        </div>
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-model="loginForm.password"
              class="input"
              name="password"
              placeholder="Password"
              type="password">
            <span class="icon is-small is-left">
              <i class="fa fa-key"/>
            </span>
          </div>
          <p class="help is-danger">{{ errors.first("password") }}</p>
        </div>
        <div class="field">
          <button class="button is-primary is-fullwidth">Login</button>
        </div>
      </div>
      <p class="has-text-grey-darker is-marginless">or</p>
      <div class="subsection is-fullwidth">
        <div 
          class="field has-text-centered" 
          @click="authenticate('google')">
          <label class="button is-primary has-icons-left google is-fullwidth has-text-centered">
            <span class="icon is-small is-left">
              <i class="fa fa-google social-icon"/>
            </span>
            Login with Google
          </label>
        </div>
      </div>
      <p class="has-text-grey-darker has-text-weight-semibold options">
        <router-link 
          to="/recover-password" 
          class="has-text-grey-darker">Forgot Password</router-link>&nbsp;&nbsp;&bull;&nbsp;
        <router-link 
          to="/register" 
          class="has-text-grey-darker">Sign up</router-link>
      </p>
    </div>
  </form>
</template>

<script>
import { LOGIN } from '../store/actions.type';
import { mapGetters } from 'vuex';
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    };
  },
  computed: {
    ...mapGetters([
      'afterLoginRedirect',
    ]),
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        const redirect = this.afterLoginRedirect;
        this.$store
          .dispatch(LOGIN, { ...this.loginForm })
          .then(() => this.$router.push(redirect || '/'));
        this.resetForm();
      });
    },
    resetForm() {
      this.loginForm.username = '';
      this.loginForm.password = '';
      this.$validator.reset();
    },
    authenticate(provider) {
      this.$auth
        .authenticate(provider)
        .then(data => console.log(`Authorized! ${data}`))
        .catch(error => console.error(error));
    }
  },
};
</script>

<style scoped>
  .subsection {
    padding: 30px 0;
  }
  .options {
    padding-top: 30px;
  }
  .google {
    background-color: #4285f4;
  }
  .google:hover {
    background-color: #3367d6;
  }
  .social-icon {
    position: absolute;
    left: 20px;
    font-size: 20px;
  }
</style>
