import UserIndex from '@/views/user/UserIndex'
import UserHomePage from '@/views/user/UserHomePage'
import UserArticleReader from '@/views/user/UserArticleReader'
import UserSearchDetail from '@/views/user/UserSearchDetail'
import UserInfoDetail from '@/views/user/UserInfoDetail'
import UserAssociationList from '@/views/user/UserAssociationList'
import UserAssociationDetail from '@/views/user/UserAssociationDetail'
import UserMessagePage from '@/views/user/UserMessagePage'
import UserApplicationList from '@/views/user/UserApplicationList'
import UserActivityList from '@/views/user/Activity'
import UserActivityDetail from '@/views/user/ActivityDetail'

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
            path: 'articles/:id',
            name: 'UserArticleReader',
            component: UserArticleReader,
            props: true
        },
		{
			path: 'search-info/:uid',
			name: 'UserSearchDetail',
			component: UserSearchDetail,
			props: true
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
            path: 'activities',
            name: 'UserActivityList',
            component: UserActivityList
        },
        {
            path: 'activities/:id',
            name: 'UserActivityDetail',
            component: UserActivityDetail
        }

    ]
}
