import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/index'
import ManaAsso from '../components/association_management/sys_manage_association'
import CreateAsso from '../components/association_management/create_association'
import EditAsso from '../components/association_management/edit_association'
import AssoList from '../components/association_management/association_list'


Vue.use(Router)

export default new Router({
  routes: [

    {
      path: '/',
      name: 'Index',
      component: Index
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
    }
  ]
})
