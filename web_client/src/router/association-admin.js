import AssociationAdminIndex from '@/views/association-admin/AssociationAdminIndex'
import ArticleList from '@/views/association-admin/article/ArticleList'
import ArticleEditor from '@/views/association-admin/article/ArticleEditor'
import ArticleReader from '@/views/association-admin/article/ArticleReader'
import MemManager from '@/views/association-admin/member/AssociationMemberTable'
import Activity from '@/views/association-admin/activity/Activity'
import About from '@/views/association-admin/activity/About'

export default {
    path: '/association-admin',
    name: 'AssociationAdminIndex',
    component: AssociationAdminIndex,
    children: [
		{
			path: '/',
			name: 'AssociationMemList',
			component: MemManager
		},
        {
            path: 'articles',
            name: 'AssociationAdminArticleList',
            component: ArticleList
        },
        {
            path: 'articles/edit/:id',
            name: 'AssociationAdminArticleEditor',
            component: ArticleEditor,
            props: true
        },
        {
            path: 'articles/:id',
            name: 'AssociationAdminArticleReader',
            component: ArticleReader,
            props: true
        },
        {
            path: 'activity',
            name: 'activity',
            component: Activity
        },
        {
            path: 'activity/about',
            name: 'about',
            component: About
        }
    ]
}
