import SysAdminIndex from '../components/sysadmin/SysAdminIndex'
import PermList from '../components/sysadmin/permission_manager/PermList'
import RoleList from '../components/sysadmin/permission_manager/RoleList'
import UserList from '../components/sysadmin/user_manager/UserList'

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
        }
    ]
}
