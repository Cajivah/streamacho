<template>
  <form class='content form-view' @submit.prevent='onSubmit()'>
    <div class='form-container'>
        <h2 class='is-large'>Please, register</h2>
        <div class='field'>
        <label class='label'>Email</label>
        <div class='control has-icons-left'>
            <input
              class='input'
              name='email'
              placeholder='email'
              v-model='registerForm.email'
              v-validate="'required|email'"
            >
            <span class='icon is-small is-left'>
            <i class='fa fa-envelope'></i>
            </span>
        </div>
        <p class='help is-danger'>{{ errors.first('email') }}</p>
        </div>
        <div class='field'>
          <label class='label'>User name</label>
          <div class='control has-icons-left'>
            <input
              class='input'
              name='userName'
              placeholder='user name'
              v-model='registerForm.userName'
              v-validate="'required'"
            >
          <span class='icon is-small is-left'>
            <i class='fa fa-user-plus'></i>
          </span>
          </div>
          <p class='help is-danger'>{{ errors.first('userName') }}</p>
        </div>
        <div class='field'>
          <label class='label'>Password</label>
          <div class='control has-icons-left'>
              <input
                class='input'
                name='password'
                placeholder='password'
                type='password'
                v-model='registerForm.passwordPair.password'
                v-validate="{ required: true, regex: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/ }"
              >
              <span class='icon is-small is-left'>
              <i class='fa fa-key'></i>
              </span>
          </div>
          <p class='help is-danger'>{{ errors.first('password') }}</p>
        </div>
        <div class='field'>
          <label class='label'>Repeat password</label>
          <div class='control has-icons-left'>
              <input
                class='input'
                name='matchingPassword'
                placeholder='repeat password'
                type='password'
                v-model='registerForm.passwordPair.matchingPassword'
                v-validate="'required|confirmed:password'"
              >
              <span class='icon is-small is-left'>
              <i class='fa fa-key'></i>
              </span>
          </div>
          <p class='help is-danger'>{{ errors.first('matchingPassword') }}</p>
        </div>
        <div class='field'>
          <button class='button is-primary'>Register</button>
        </div>
        <router-link to='login'>Already registered? Login</router-link>
    </div>
  </form>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      registerForm: {
        email: "",
        userName: "",
        passwordPair: {
          password: "",
          matchingPassword: ""
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
        this.$emit("onRegister", {
          ...this.registerForm
        });
        // vuex action
        this.registerForm.email = "";
        this.registerForm.userName = "";
        this.registerForm.passwordPair.password = "";
        this.registerForm.passwordPair.matchingPassword = "";
        this.$validator.reset();
      });
    }
  }
};
</script>

<style scoped>
</style>
