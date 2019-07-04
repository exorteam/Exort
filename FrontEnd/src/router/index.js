import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
import UserList from '@/components/user_management/UserList'
import PermList from '@/components/user_management/PermList'

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
      path: '/user_list/',
      name: 'UserList',
      component: UserList
    },
    {
      path: '/perm_list/',
      name: 'PermList',
      component: PermList
    }
  ]
})
