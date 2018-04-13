import Vue from 'vue';
import Router from 'vue-router';
import Hub from '@/Hub';
import Register from '@/registration/Register';
import Login from '@/login/Login';
import store from '@/store';

Vue.use(Router);

const ifNotAuthenticated = (to, from, next) => {
  console.log(!store.getters.isAuthenticated);
  console.log(store);
  if (!store.getters.isAuthenticated) {
    next();
    return
  }
  next('/')
};

const ifAuthenticated = (to, from, next) => {
  if (store.getters.isAuthenticated) {
    next();
    return
  }
  next('/login')
};

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
      component: Register,
      beforeEnter: ifNotAuthenticated
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      beforeEnter: ifNotAuthenticated
    }
  ]
});
