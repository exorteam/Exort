import Vue from 'vue'
import Router from 'vue-router'

// import Index from '@/components/index'
import Activity from '../components/activity/activity.vue'
import About from '../components/activity/about.vue'

import EditAsso from '../components/association_management/edit_association'
import SysAssoList from '../components/association_management/sys_association_list'
import AssoList from '../components/association_management/association_list'
import CreateAsso from '../components/association_management/create_association'

import AdminIndex from '../components/admin_index'

import AssociaMemManage from '../components/assoadmin/association_member_management/association_member_table'
import AppliManagement from '../components/assoadmin/association_member_management/application_management_table'

import UserList from '../components/user_management/UserList.vue'
import PermList from '../components/user_management/PermList.vue'

import SignIn from '../components/auth/signin/signin'
import SignUp from '../components/auth/signup/signup'

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Home',
            redirect: '/signIn'
        },
        {
            path: '/signIn',
            name: 'SignIn',
            component: SignIn
        },
        {
            path: '/signUp',
            name: 'SignUp',
            component: SignUp
        },
        {
            path: "/admin",
            name: 'Admin',
            component: AdminIndex,
            children: [
                {
                    path: 'activity',
                    name: 'activity',
                    component: Activity
                },
                {
                    path: 'activity/about',
                    name: 'about',
                    component: About
                },
                {
                    path: 'asso_list',
                    name: 'AssoList',
                    component: AssoList
                },
                {
                    path: 'sys_asso_list',
                    name: 'SysAssoList',
                    component: SysAssoList
                },
                {
                    path: 'create_asso',
                    name: 'CreateAsso',
                    component: CreateAsso
                },
                // {
                //     path: 'sys_mana_asso',
                //     name: 'SysManaAsso',
                //     component: SysManaAsso
                // },
                // {
                //     path: 'edit_asso',
                //     name: 'EditAsso',
                //     component: EditAsso
                // },
                // {
                //     path: 'sys_create_asso',
                //     name: 'SysCreateAsso',
                //     component: SysCreateAsso
                // },
                {
                    path: 'application_management',
                    name: 'AppliManagement',
                    component: AppliManagement
                },
                {
                    path: 'association_member_management',
                    name: 'AssociaMemManage',
                    component: AssociaMemManage
                },
                {
                    path: 'user_list',
                    name: 'UserList',
                    component: UserList
                },
                {
                    path: 'perm_list',
                    name: 'PermList',
                    component: PermList
                }

            ]
        }
    ]
})
