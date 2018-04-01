import Vue from 'vue';
import Router from 'vue-router';
import Hub from '@/Hub';
import Register from '@/registration/Register';
import Login from '@/login/Login';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Hub
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    }
  ]
});
