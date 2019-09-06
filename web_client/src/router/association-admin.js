import AssociationAdminIndex from '@/views/association-admin/AssociationAdminIndex'
import ArticleList from '@/views/association-admin/article/ArticleList'
import ArticleEditor from '@/views/association-admin/article/ArticleEditor'
import ArticleReader from '@/views/association-admin/article/ArticleReader'
import MemManager from '@/views/association-admin/member/AssociationMemberTable'
import Activity from '@/views/association-admin/activity/Activity'
import About from '@/views/association-admin/activity/About'
import Finance from "@/views/association-admin/finance/Finance"

export default {
	path: '/association-admin/:assoId',
    component: AssociationAdminIndex,
	props: true,
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
            name: 'AssociationAdminActivityList',
            component: Activity
        },
        {
            path: 'activity/:id',
            name: 'AssociationAdminActivityAbout',
            component: About
        },
        {
            path: 'finance',
            name: 'AssociationAdminFinanceList',
            component: Finance
        }
    ]
}
