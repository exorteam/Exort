import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/index'
import AddAsso from '../components/association_management/add_association'
import AssoDetail from '../components/association_management/association_detail'
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
      path:'/add_asso',
      name:'AddAsso',
      component:AddAsso
    },
    {
      path:'/edit_asso',
      name:'EditAsso',
      component:EditAsso
    },
    {
      path:'/asso_detail',
      name:'AssoDetail',
      component:AssoDetail
    }
  ]
})
