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
