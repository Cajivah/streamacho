<template>
  <form class="content register" @submit.prevent="onSubmit()">
    <div class="register-container">
        <h2 class="is-large">Please, register</h2>
        <div class="field">
        <label class="label">Email</label>
        <div class="control has-icons-left">
            <input
            class="input"
            name="email"
            placeholder="email"
            v-model="registerForm.email"
            v-validate="'required|email'"
            > 
            <span class="icon is-small is-left">
            <i class="fa fa-envelope"></i>
            </span>
        </div>
        <p class="help is-danger">{{ errors.first('email') }}</p>
        </div>
        <div class="field">
          <label class="label">First name</label>
          <div class="control has-icons-left">
            <input
              class="input"
              name="firstName"
              placeholder="first name"
              v-model="registerForm.firstName"
              v-validate="'required'"
            >
          <span class="icon is-small is-left">
            <i class="fa fa-key"></i>
          </span>
          </div>
          <p class="help is-danger">{{ errors.first('firstName') }}</p>
        </div>
        <div class="field">
          <label class="label">Last name</label>
          <div class="control has-icons-left">
              <input
              class="input"
              name="lastName"
              placeholder="last name"
              v-model="registerForm.lastName"
              v-validate="'required'"
              >
              <span class="icon is-small is-left">
              <i class="fa fa-key"></i>
              </span>
          </div>
          <p class="help is-danger">{{ errors.first('lastName') }}</p>
        </div>
        <div class="field">
          <label class="label">Password</label>
          <div class="control has-icons-left">
              <input
              class="input"
              name="password"
              placeholder="password"
              type="password"
              v-model="registerForm.password"
              v-validate="'required|min:5'"
              >
              <span class="icon is-small is-left">
              <i class="fa fa-key"></i>
              </span>
          </div>
          <p class="help is-danger">{{ errors.first('password') }}</p>
        </div>
        <div class="field">
          <label class="label">Repeat password</label>
          <div class="control has-icons-left">
              <input
              class="input"
              name="repeatPassword"
              placeholder="repeat password"
              type="password"
              v-validate="'required|confirmed:password'"
              >
              <span class="icon is-small is-left">
              <i class="fa fa-key"></i>
              </span>
          </div>
          <p class="help is-danger">{{ errors.first('repeatPassword') }}</p>
        </div>
        <div class="field">
          <button class="button is-primary">Register</button>
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
        firstName: "",
        lastName: "",
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
        this.$emit("onRegister", {
          ...this.registerForm
        });
        this.registerForm.email = "";
        this.registerForm.password = "";
        this.registerForm.firstName = "";
        this.registerForm.lastName = "";
        this.$validator.reset();
      });
    }
  }
};
</script>

<style scoped>
.register {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/login.jpg");
  background-repeat: no-repeat;
  background-size: cover;
}

.register-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
  background-color: rgba(240, 240, 240, 0.6);
}
</style>
