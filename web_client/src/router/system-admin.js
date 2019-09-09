import SystemAdminIndex from '@/views/system-admin/SystemAdminIndex'
import PermList from '@/views/system-admin/permission/PermList'
import RoleList from '@/views/system-admin/permission/RoleList'
import UserList from '@/views/system-admin/user/UserList'
import AssociationList from '@/views/system-admin/association/AssociationList'
import ApplicationList from '@/views/system-admin/association/ApplicationList'

export default {
    path: '/system-admin',
    name: 'SystemAdminIndex',
    component: SystemAdminIndex,
    children: [
        {
            path: 'associations',
            name: 'SystemAdminAssociationList',
            component: AssociationList
        },
        {
            path: 'applications',
            name: 'SystemAdminApplicationList',
            component: ApplicationList
        },
        {
            path: 'users',
            name: 'SystemAdminUserList',
            component: UserList
        },
        {
            path: 'permissions',
            name: 'SystemAdminPermList',
            component: PermList
        },
        {
            path: 'roles',
            name: 'SystemAdminRoleList',
            component: RoleList
        }
    ]
}
