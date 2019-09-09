import UserIndex from '@/views/user/UserIndex'
import UserHomePage from '@/views/user/UserHomePage'
import UserArticleReader from '@/views/user/UserArticleReader'
import UserInfoDetail from '@/views/user/UserInfoDetail'
import UserAssociationList from '@/views/user/UserAssociationList'
import UserAssociationDetail from '@/views/user/UserAssociationDetail'
import UserMessagePage from '@/views/user/UserMessagePage'
import UserApplicationList from '@/views/user/UserApplicationList'

export default {
    path: '/',
    component: UserIndex,
    children: [
		{
			path: '/',
			name: 'UserHomePage',
			component: UserHomePage
		},
		{
			path: 'messages',
			name: 'UserMessagePage',
			component: UserMessagePage
		},
		{
			path: 'associations',
			name: 'UserAssociationList',
			component: UserAssociationList
		},
		{
			path: 'applications',
			name: 'UserApplicationList',
			component: UserApplicationList
		},
		{
			path: 'association/:assoId',
			name: 'UserAssociationDetail',
			component: UserAssociationDetail,
			props: true
		},
		{
			path: 'users/:id',
			name: 'UserInfoDetail',
			component: UserInfoDetail,
			props: true
		},
        {
            path: 'articles/:id',
            name: 'UserArticleReader',
            component: UserArticleReader,
            props: true
        }

    ]
}
