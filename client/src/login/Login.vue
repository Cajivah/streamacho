<template>
  <form class='content form-view' @submit.prevent='onSubmit()'>
    <div class='form-container'>
        <h2 class='is-large'>Please, login</h2>
        <div class='field auth-control' @click="authenticate('google')">
            <label class='button is-primary auth-button has-icons-left'>
                <span class='icon is-small is-left'>
                    <i class='fa fa-google'></i>
                </span>
                Login with google
            </label>
        </div>
        <div class='field'>
        <label class='label'>User name</label>
        <div class='control has-icons-left'>
            <input
              class='input'
              name='username'
              placeholder='User name'
              v-model='loginForm.username'
              v-validate="'required'"
            > 
            <span class='icon is-small is-left'>
            <i class='fa fa-envelope'></i>
            </span>
        </div>
        <p class='help is-danger'>{{ errors.first('username') }}</p>
        </div>
        <div class='field'>
        <label class='label'>Password</label>
        <div class='control has-icons-left'>
            <input
              class='input'
              name='password'
              placeholder='password'
              type='password'
              v-model='loginForm.password'
              v-validate="{ required: true, regex: /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/ }"
            >
            <span class='icon is-small is-left'>
            <i class='fa fa-key'></i>
            </span>
        </div>
        <p class='help is-danger'>{{ errors.first('password') }}</p>
        </div>
        <div class='field auth-control'>
            <button class='button is-primary login-button'>Login</button>
        </div>
        <router-link to='register'>Need to register? Click me</router-link>
    </div>
  </form>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        this.$emit('login', { ...this.loginForm });
        this.loginForm.username = '';
        this.loginForm.password = '';
        this.$validator.reset();
      });
    },
    authenticate(provider) {
      this.$auth
        .authenticate(provider)
        .then((data) => console.log('Authorized! ' + data))
        .catch((error) => console.error(error));
    }
  }
};
</script>

<style scoped>
.auth-control {
  display: flex;
  width: 100%;
}

.auth-button {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.login-button {
    width: 100%;
}
</style>
