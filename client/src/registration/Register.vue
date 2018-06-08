<template>
  <form 
    class="content form-view" 
    @submit.prevent="onSubmit()">
    <div class="form-container auth-form">
      <div class="header">
        <h1 class="is-large has-text-weight-semibold has-text-grey-darker">
          <a
            class="return has-text-dark"
            @click="$router.go(-1)"
          ><i class="fa fa-arrow-left"/></a>
          Sign up
        </h1>
      </div>
      <div class="subsection is-fullwidth">
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-validate="'required|min:4'"
              v-model="registerForm.username"
              class="input"
              name="username"
              placeholder="Username">
            <span class="icon is-small is-left">
              <i class="fa fa-user"/>
            </span>
          </div>
          <p class="help is-danger">{{ errors.first("username") }}</p>
        </div>
        <div class="field">
          <div class="control has-icons-left">
            <input
              v-validate="'required|email'"
              v-model="registerForm.email"
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
          <div class="control has-icons-left">
            <input
              v-validate="{ required: true, min:8, regex: /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&\-])[A-Za-z\d$@$!%*?&\-]{8,}/ }"
              v-model="registerForm.passwordPair.password"
              class="input"
              name="password"
              placeholder="Password"
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
              v-model="registerForm.passwordPair.matchingPassword"
              class="input"
              name="matchingPassword"
              placeholder="Repeat password"
              type="password">
            <span class="icon is-small is-left">
              <i class="fa fa-key"/>
            </span>
          </div>
          <span class="help is-danger">{{ errors.first("matchingPassword") }}</span>
        </div>
        <div class="field">
          <button class="button is-primary is-fullwidth is-unselectable">Register</button>
        </div>
      </div>
      <p class="has-text-grey-darker  options">
        Already have an account?
        <router-link 
          to="login" 
          class="has-text-grey-darker has-text-weight-semibold">Login</router-link>
      </p>
      <img
        src="../assets/logo_green.svg"
        class="logo">
    </div>
  </form>
</template>

<script>
import { REGISTER } from '../store/actions.type';

export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        email: '',
        username: '',
        passwordPair: {
          password: '',
          matchingPassword: ''
        }
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
          ...this.registerForm,
          passwordPair: {
            password: this.registerForm.passwordPair.password,
            matchingPassword: this.registerForm.passwordPair.matchingPassword
          }
        };
        this.$store
          .dispatch(REGISTER, body)
          .then(() => this.$router.push('login'));
        this.registerForm.passwordPair.password = '';
        this.registerForm.passwordPair.matchingPassword = '';
        this.$validator.reset();
      });
    },
    parsePasswordError(err) {
      return err && err.firstByRule('password', 'regex')
        ? 'Password is not secure enough'
        : err.first('password');
    }
  }
};
</script>

<style scoped>
  .subsection {
    padding: 30px 0;
  }
  .options {
    padding-top: 30px;
  }
  .logo {
    margin-top: 10px;
    width: 120px;
  }
  .return {
    position: absolute;
    left: 0;
  }
  .header {
    position: relative;
    width: 100%;
    text-align: center;
  }
</style>
