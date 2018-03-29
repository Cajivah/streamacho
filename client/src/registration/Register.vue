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
              name='firstName'
              placeholder='first name'
              v-model='registerForm.firstName'
              v-validate="'required'"
            >
          <span class='icon is-small is-left'>
            <i class='fa fa-user-plus'></i>
          </span>
          </div>
          <p class='help is-danger'>{{ errors.first('firstName') }}</p>
        </div>
        <div class='field'>
          <label class='label'>Last name</label>
          <div class='control has-icons-left'>
              <input
                class='input'
                name='lastName'
                placeholder='last name'
                v-model='registerForm.lastName'
                v-validate="'required'"
              >
              <span class='icon is-small is-left'>
              <i class='fa fa-user-plus'></i>
              </span>
          </div>
          <p class='help is-danger'>{{ errors.first('lastName') }}</p>
        </div>
        <div class='field'>
          <label class='label'>Password</label>
          <div class='control has-icons-left'>
              <input
                class='input'
                name='password'
                placeholder='password'
                type='password'
                v-model='registerForm.password'
                v-validate="'required|min:8'"
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
                name='repeatPassword'
                placeholder='repeat password'
                type='password'
                v-validate="'required|confirmed:password'"
              >
              <span class='icon is-small is-left'>
              <i class='fa fa-key'></i>
              </span>
          </div>
          <p class='help is-danger'>{{ errors.first('repeatPassword') }}</p>
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
