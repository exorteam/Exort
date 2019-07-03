import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
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
