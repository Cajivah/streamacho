<template>
    <form class='content form-view' @submit.prevent='onSubmit()'>
        <div class='form-container auth-form'>
            <h1 class='is-large has-text-weight-semibold has-text-grey-darker'>Login</h1>
            <div class="subsection">
                <div class='field'>
                    <div class='control has-icons-left'>
                        <input
                            class='input'
                            name='username'
                            placeholder='Username'
                            v-model='loginForm.username'
                        >
                        <span class='icon is-small is-left'>
                            <i class='fa fa-user'></i>
                        </span>
                    </div>
                    <p class='help is-danger'>{{ errors.first('username') }}</p>
                </div>
                <div class='field'>
                    <div class='control has-icons-left'>
                        <input
                            class='input'
                            name='password'
                            placeholder='Password'
                            type='password'
                            v-model='loginForm.password'
                        >
                        <span class='icon is-small is-left'>
                            <i class='fa fa-key'></i>
                        </span>
                    </div>
                    <p class='help is-danger'>{{ errors.first('password') }}</p>
                </div>
                <div class='field auth-control'>
                    <button class='button is-primary is-fullwidth'>Login</button>
                </div>
            </div>
            <p class="has-text-grey-darker is-marginless">or</p>
            <div class="subsection">
                <div class='field third-party-auth' @click="authenticate('google')">
                    <label class='button is-primary has-icons-left google third-party-auth__label'>
                        <span class='icon is-small is-left'>
                            <i class='fa fa-google social-icon'></i>
                        </span>
                        Login with Google
                    </label>
                </div>
            </div>
            <p class="has-text-grey-darker has-text-weight-semibold options">
                <router-link to='recover-password' class="has-text-grey-darker">Forgot Password</router-link>&nbsp;&nbsp;&bull;&nbsp;
                <router-link to='register' class="has-text-grey-darker">Sign up</router-link>
            </p>
        </div>
    </form>
</template>

<script>
  import VuePassword from 'vue-password'

  export default {
    name: "Login",
    components: {
      VuePassword,
    },
    data() {
      return {
      loginForm: {
        username: "",
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
        this.$store
          .dispatch("login", { ...this.loginForm })
          .then(response => response)
          .catch(error => error);
        this.loginForm.password = "";
        this.$validator.reset();
      });
    },
    authenticate(provider) {
      this.$auth
        .authenticate(provider)
        .then(data => console.log("Authorized! " + data))
        .catch(error => console.error(error));
    }
  }
};
</script>

<style scoped>
.auth-control {
  display: flex;
}

.subsection {
    padding: 30px 0;
    width: 100%;
}

.options {
    padding-top: 30px;
}

.third-party-auth {
    text-align: center;

}

.third-party-auth__label {
    text-align: center;
    width: 100%;
}

.google {
    background-color: #4285F4;
}

.google:hover {
    background-color: #3367D6;
}

.social-icon {
    position: absolute;
    left: 20px;
    font-size: 20px;
    margin-top: 0px;
}
</style>
