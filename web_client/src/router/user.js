import UserIndex from '@/views/user/UserIndex'
import UserHomePage from '@/views/user/UserHomePage'
import UserArticleList from '@/views/user/UserArticleList'
import UserArticleReader from '@/views/user/UserArticleReader'

export default {
    path: '/',
    name: 'UserIndex',
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
    ]
}
