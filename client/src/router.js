import Vue from 'vue';
import Router from 'vue-router';
import Hub from '@/Hub';
import Register from '@/registration/Register';
import Login from '@/login/Login';
import Navigation from '@/common/Navigation';
import CreateRoom from '@/rooms/CreateRoom';
import store from '@/store';

Vue.use(Router);

const ifNotAuthenticated = (to, from, next) => {
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
      components: {
        header: Navigation,
        content: Hub
      },
      props: { header: true, content: false }
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
    },
    {
      path: '/createRoom',
      name: 'createRoom',
      components: {
        header: Navigation,
        content: CreateRoom
      },
      props: { header: true, content: false }
    }
  ]
});
