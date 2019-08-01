import Vue from 'vue'
import Router from 'vue-router'

// import Index from '@/components/index'
import Activity from '../components/assoadmin/activity/activity.vue'
import About from '../components/assoadmin/activity/about.vue'

// import EditAsso from '../components/assoadmin/association_manager/edit_association'
import SysAssoList from '../components/sysadmin/association_manager/sys_association_list'

import AdminIndex from '../components/admin_index'

import AssociaMemManage from '../components/assoadmin/association_member_management/association_member_table'
import AppliManagement from '../components/assoadmin/association_member_management/application_management_table'

import UserList from '../components/user_management/UserList.vue'
import PermList from '../components/user_management/PermList.vue'

import SignIn from '../components/auth/signin/signin'
import SignUp from '../components/auth/signup/signup'

import ArticleReader from '../components/articles/article-reader.vue'
import ArticleList from '../components/articles/article-list.vue'
import ArticleEditor from '../components/articles/article-editor.vue'

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
                    path: 'sys_asso_list',
                    name: 'SysAssoList',
                    component: SysAssoList
                },
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
                },
				{
					path: 'articles',
					name: 'Articles',
					component: ArticleList,
				},
				{
					path: 'articles/:id',
					name: 'ArticleReader',
					component: ArticleReader,
					props: true
				},				
				{
					path: 'articles/edit/:id',
					name: 'ArticleEditor',
					component: ArticleEditor,
					props: true
				}

            ]
        }
    ]
})
