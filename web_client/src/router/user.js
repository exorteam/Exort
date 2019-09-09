import UserIndex from '../components/user/UserIndex'
import GlobalNotFound from '../components/GlobalNotFound'

export default {
    path: '/',
    name: 'Home',
    component: UserIndex,
    children: [
        {
            path: '*',
            name: 'GlobalNotFound',
            component: GlobalNotFound
        }
    ]
}
