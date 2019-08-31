import AssociationAdminIndex from '@/views/association-admin/AssociationAdminIndex'
import ArticleList from '@/views/association-admin/article/ArticleList'
import ArticleEditor from '@/views/association-admin/article/ArticleEditor'
import ArticleReader from '@/views/association-admin/article/ArticleReader'

export default {
    path: '/association-admin',
    name: 'AssociationAdminIndex',
    component: AssociationAdminIndex,
    children: [
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
        }
	]
}
