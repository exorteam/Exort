import Vue from 'vue'
import Router from 'vue-router'

import Index from '../components/index'
import ManaAsso from '../components/association_management/sys_manage_association'
import CreateAsso from '../components/association_management/create_association'
import EditAsso from '../components/association_management/edit_association'
import AssoList from '../components/association_management/association_list'
import Test from '../components/association_management/create_Test'

import AssociaMemManage from '../components/association_member_management/member_management._table'
import AppliManagement from '../components/association_member_management/application_management_table'


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
      path:'/test',
      name:'Test',
      component:Test
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
      path: '/association_member_management',
      name: 'AssociaMemManage',
      component: AssociaMemManage
    },
    {
      path:'/application_management',
      name:'AppliManagement',
      component:AppliManagement
    }
  ]
})
