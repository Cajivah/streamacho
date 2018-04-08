<template>
    <form class='content form-view' @submit.prevent='onSubmit()'>
        <div class='form-container auth-form'>
            <h1 class='is-large has-text-weight-semibold'>Sign up</h1>
            <div class="subsection">
                <div class='field'>
                    <div class='control has-icons-left'>
                        <input
                            class='input'
                            name='username'
                            placeholder='Username'
                            v-model='registerForm.username'
                            v-validate="'required'"
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
                            name='email'
                            placeholder='Email'
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
                    <div class='control has-icons-left'>
                        <input
                            class='input'
                            name='password'
                            placeholder='Password'
                            type='password'
                            v-model='registerForm.passwordPair.password'
                            v-validate="{ required: true, min:8, regex: /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&\-])[A-Za-z\d$@$!%*?&\-]{8,}/ }"
                        >
                        <span class='icon is-small is-left'>
                            <i class='fa fa-key'></i>
                        </span>
                    </div>
                    <p class='help is-danger'>{{ parsePasswordError(errors) }}</p>
                </div>
                <div class='field'>
                    <div class='control has-icons-left'>
                        <input
                            class='input'
                            name='matchingPassword'
                            placeholder='Repeat password'
                            type='password'
                            v-model='registerForm.passwordPair.matchingPassword'
                            v-validate="'required|confirmed:password'"
                        >
                        <span class='icon is-small is-left'>
                            <i class='fa fa-key'></i>
                        </span>
                    </div>
                    <span class='help is-danger'>{{ errors.first('matchingPassword') }}</span>
                </div>
                <div class='field auth-control'>
                    <button class='button is-primary is-fullwidth'>Register</button>
                </div>
            </div>
            <p class="has-text-grey-darker  options">
                Already have an account?
                <router-link to='login' class="has-text-grey-darker has-text-weight-semibold">Login</router-link>
            </p>
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
          username: "",
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
          const body = {
            ...this.registerForm,
            passwordPair: {
              password: this.registerForm.passwordPair.password,
              matchingPassword: this.registerForm.passwordPair.matchingPassword
            }
          };
          this.$store
            .dispatch("register", body)
            .then(response => response)
            .catch(error => error);
          this.registerForm.passwordPair.password = "";
          this.registerForm.passwordPair.matchingPassword = "";
          this.$validator.reset();
        });
      },
      parsePasswordError(err) {
        return err && err.firstByRule('password', 'regex')
          ? 'Password is not secure enough'
          : err.first('password');
      },
    }
  };
</script>

<style scoped>
.subsection {
    padding: 30px 0;
    width: 100%;
}
.options {
    padding-top: 30px;
}
.auth-control {
    display: flex;
}
</style>
