import Vue from 'vue';
import Router from 'vue-router';
import LandingPage from '@/common/LandingPage';
import Register from '@/registration/Register';
import Login from '@/login/Login';
import Hub from '@/Hub';
import CreateRoom from '@/meetings/CreateRoom';
import RoomPanel from '@/room/RoomPanel';
import MyMeetings from '@/meetings/MyMeetings';
import store from '@/store';
import Activate from '@/activation/Activate';
import {FETCH_LOGGED_USER} from './store/actions.type';

Vue.use(Router);

const ifNotAuthenticatedOnly = (to, from, next) => {
  if (!store.getters.isAuthenticated) {
    next();
    return
  }
  next('/')
};

const ifAuthenticated = (to, from, next) => {
  store.dispatch(FETCH_LOGGED_USER)
    .then(next)
    .catch(() => next('/login'));
};

export default new Router({
  routes: [
    {
      path: '/',
      component: Hub,
      children: [{
        path: '/',
        name: 'landingPage',
        component: LandingPage,
      }, {
        path: '/create-room',
        name: 'createRoom',
        component: CreateRoom,
        beforeEnter: ifAuthenticated
      }, {
        path: '/rooms/:id',
        name: 'room',
        component: RoomPanel,
        beforeEnter: ifAuthenticated
      }, {
        path: '/my',
        name: 'myMeetings',
        component: MyMeetings,
        beforeEnter: ifAuthenticated
      }]
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      beforeEnter: ifNotAuthenticatedOnly
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      beforeEnter: ifNotAuthenticatedOnly
    },
    {
      path: '/activate',
      name: 'activate',
      component: Activate,
      props: (route) => ({ token: route.query.token })
    }
  ]
});
