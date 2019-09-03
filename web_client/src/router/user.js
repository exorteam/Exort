import UserIndex from '@/views/user/UserIndex'
import UserHomePage from '@/views/user/UserHomePage'
import UserArticleList from '@/views/user/UserArticleList'
import UserArticleReader from '@/views/user/UserArticleReader'
import UserInfoDetail from '@/views/user/UserInfoDetail'
import UserInfoEditor from '@/views/user/UserInfoEditor'

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
			path: 'info',
			name: 'UserInfoDetail',
			component: UserInfoDetail
		},
		{
			path: 'info/edit',
			name: 'UserInfoEditor',
			component: UserInfoEditor
		}

    ]
}
