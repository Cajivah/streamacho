<template>
  <form class="content login" @submit.prevent="onSubmit()">
    <div class="login-container">
        <h2 class="is-large">Please, login</h2>
        <div class="field">
        <label class="label">Email</label>
        <div class="control has-icons-left">
            <input
            class="input"
            name="email"
            placeholder="email"
            v-model="loginForm.email"
            v-validate="'required|email'"
            > 
            <span class="icon is-small is-left">
            <i class="fa fa-envelope"></i>
            </span>
        </div>
        <p class="help is-danger">{{ errors.first('email') }}</p>
        </div>
        <div class="field">
        <label class="label">Password</label>
        <div class="control has-icons-left">
            <input
            class="input"
            name="password"
            placeholder="password"
            type="password"
            v-model="loginForm.password"
            v-validate="'required|min:5'"
            >
            <span class="icon is-small is-left">
            <i class="fa fa-key"></i>
            </span>
        </div>
        <p class="help is-danger">{{ errors.first('password') }}</p>
        </div>
        <div class="field">
            <button class="button is-primary">Login</button>
        </div>
        <router-link to='register'>Need to register? Click me</router-link>
    </div>
  </form>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        this.$emit("onLogin", {
          ...this.loginForm
        });
        this.loginForm.email = "";
        this.loginForm.password = "";
        this.$validator.reset();
      });
    }
  }
};
</script>

<style scoped>
.login {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;  
  background-image: url("../assets/login.jpg");
  background-repeat: no-repeat;
  background-size: cover;
}

.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
  background-color: rgba(240, 240, 240, 0.6);
}
</style>
