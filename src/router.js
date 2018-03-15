import Vue from 'vue';
import Router from 'vue-router';
import App from '@/App.vue';
import Register from '@/components/Register.vue';
import Login from '@/components/Login.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: App
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    }
  ]
});
