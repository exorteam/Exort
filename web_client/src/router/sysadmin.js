import SysAdminIndex from '../components/sysadmin/SysAdminIndex'
import PermList from '../components/sysadmin/permission_manager/PermList'
import RoleList from '../components/sysadmin/permission_manager/RoleList'
import UserList from '../components/sysadmin/user_manager/UserList'
import associationList from '../components/sysadmin/association_manager/sys_association_list'
import SysArticleList from '../components/sysadmin/articles/sys_article_list'
import SysArticleEditor from '../components/sysadmin/articles/sys_article_editor'
import SysArticleReader from '../components/sysadmin/articles/sys_article_reader'
import ApplicationList from '../components/sysadmin/association_manager/application.vue'

export default {
    path: '/sysadmin/',
    name: 'SysAdminIndex',
    component: SysAdminIndex,
    children: [
        {
            path: 'permlist/',
            name: 'SysAdminPermList',
            component: PermList
        },
        {
            path: 'rolelist/',
            name: 'SysAdminRoleList',
            component: RoleList
        },
        {
            path: 'userlist/',
            name: 'SysAdminUserList',
            component: UserList
        },
        {
            path: 'assolist/',
            name: 'associationList',
            component: associationList
        },
        {
            path: 'applicationList/',
            name: 'ApplicationList',
            component: ApplicationList
        },
        {
            path: 'articlelist/',
            name: 'ArticleList',
            component: SysArticleList
        },
        {
            path: 'articles/edit/:id',
            name: 'ArticleEditor',
            component: SysArticleEditor,
            props: true
        },
        {
            path: 'articles/:id',
            name: 'ArticleReader',
            component: SysArticleReader,
            props: true
        }
    ]
}
