import SysAdminIndex from '../components/sysadmin/SysAdminIndex'
import PermList from '../components/sysadmin/permission_manager/PermList'
import RoleList from '../components/sysadmin/permission_manager/RoleList'
import UserList from '../components/sysadmin/user_manager/UserList'
import associationList from '../components/sysadmin/association_manager/sys_association_list'
import ArticleList from '../components/assoadmin/articles/article-list'
import ArticleEditor from '../components/assoadmin/articles/article-editor'
import ArticleReader from '../components/assoadmin/articles/article-reader'


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
            path: 'articlelist/',
            name: 'ArticleList',
            component: ArticleList
        },
        {
            path: 'articles/edit/:id',
            name: 'ArticleEditor',
            component: ArticleEditor,
            props: true
        },
        {
            path: 'articles/:id',
            name: 'ArticleReader',
            component: ArticleReader,
            props: true
        }
    ]
}
