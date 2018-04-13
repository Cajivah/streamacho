import Vue from 'vue';
import Router from 'vue-router';
import LandingPage from '@/common/LandingPage';
import Register from '@/registration/Register';
import Login from '@/login/Login';
import Hub from '@/Hub';
import CreateMeeting from '@/meetings/CreateMeeting';
import store from '@/store';
import Activate from '@/activation/Activate';

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
      component: Hub,
      children: [{
        path: '/',
        name: 'landingPage',
        component: LandingPage,
      }, {
        path: '/createMeeting',
        name: 'createMeeting',
        component: CreateMeeting,
        beforeEnter: ifAuthenticated
      }]
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
      path: '/activate',
      name: 'activate',
      component: Activate,
      props: (route) => ({ token: route.query.token })
    }
  ]
});
