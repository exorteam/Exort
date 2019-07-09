import Vue from 'vue'
import Router from 'vue-router'

import Index from '@/components/index'
import Activity from '../components/activity/Activity.vue'
import About from '../components/activity/about.vue'
import Build from '../components/activity/build.vue'

import SysManaAsso from '../components/association_management/sys_manage_association'
import SysCreateAsso from '../components/association_management/sys_create_association'
import EditAsso from '../components/association_management/edit_association'
import SysAssoList from '../components/association_management/sys_association_list'
import AssoList from '../components/association_management/association_list'
import CreateAsso from '../components/association_management/create_association'

import AssociaMemManage from '../components/association_member_management/member_management._table'
import AppliManagement from '../components/association_member_management/application_management_table'

import UserList from '../components/user_management/UserList.vue'
import PermList from '../components/user_management/PermList.vue'

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
      path: '/activity/build',
      name: 'build',
      component: Build
    },
    {
      path: '/asso_list',
      name: 'AssoList',
      component: AssoList
    },
    {
      path: '/sys_asso_list',
      name: 'SysAssoList',
      component: SysAssoList
    },
    {
      path: '/create_asso',
      name: 'CreateAsso',
      component: CreateAsso
    },

    {
      path:'/sys_mana_asso',
      name:'SysManaAsso',
      component:SysManaAsso
    },
    {
      path:'/edit_asso',
      name:'EditAsso',
      component:EditAsso
    },
    {
      path:'/sys_create_asso',
      name:'SysCreateAsso',
      component:SysCreateAsso
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
    },
    {
      path:'/application_management',
      name:'AppliManagement',
      component:AppliManagement
    },
    {
      path:'/user_list',
      name: 'UserList',
      component:UserList
    },
    {
      path:'/perm_list',
      name: 'PermList',
      component:PermList
    }
  ]
})