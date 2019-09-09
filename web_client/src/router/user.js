import UserIndex from '@/views/user/UserIndex'
import UserHomePage from '@/views/user/UserHomePage'
import UserArticleList from '@/views/user/UserArticleList'
import UserArticleReader from '@/views/user/UserArticleReader'
import UserSearchDetail from '@/views/user/UserSearchDetail'
import UserInfoDetail from '@/views/user/UserInfoDetail'
import UserInfoEditor from '@/views/user/UserInfoEditor'
import UserAssociationList from '@/views/user/UserAssociationList'
import UserAssociationDetail from '@/views/user/UserAssociationDetail'

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
			path: 'articles',
			name: 'UserArticleList',
			component: UserArticleList
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
			path: 'info',
			name: 'UserInfoDetail',
			component: UserInfoDetail
		},
		{
			path: 'info/edit',
			name: 'UserInfoEditor',
			component: UserInfoEditor
		},
		{
			path: 'associations',
			name: 'UserAssociationList',
			component: UserAssociationList
		},
		{
			path: 'association/:assoId',
			name: 'UserAssociationDetail',
			component: UserAssociationDetail,
			props: true
		}

    ]
}
