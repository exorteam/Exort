import Vue from 'vue'
import Router from 'vue-router'
import ManaAsso from '../components/association_management/sys_manage_association'
import CreateAsso from '../components/association_management/create_association'
import EditAsso from '../components/association_management/edit_association'
import AssoList from '../components/association_management/association_list'

import Activity from '../components/activity/Activity.vue'
import About from '../components/activity/about.vue'

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [

    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/association_member_management',
      name: 'AssociaMemManage',
      component: AssociaMemManage
    },
    {
      path: '/asso_list',
      name: 'AssoList',
      component: AssoList
    },
    {
      path:'/mana_asso',
      name:'ManaAsso',
      component:ManaAsso
    },
    {
      path:'/edit_asso',
      name:'EditAsso',
      component:EditAsso
    },
    {
      path:'/create_asso',
      name:'CreateAsso',
      component:CreateAsso
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
