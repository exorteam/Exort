import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
import Activity from '../components/activity/Activity.vue'
import About from '../components/activity/about.vue'

Vue.use(Router);

export default new Router({
  mode : 'history',
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/activity',
      name: 'activity',
      component: Activity
    },
    {
      path: '/activity/about',
      name: 'about',
      component: About
    }
  ]
})
