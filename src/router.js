import Vue from 'vue'
import Router from 'vue-router'
import App from './App.vue'
import About from './views/About.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: App
    },
    {
      path: '/about',
      name: 'about',
      component: About
    }
  ]
})
