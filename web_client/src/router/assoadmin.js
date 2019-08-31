import AssoAdminIndex from '../components/assoadmin/AssoAdminIndex'
import ArticleList from '../components/assoadmin/articles/article-list'
import ArticleEditor from '../components/assoadmin/articles/article-editor'
import ArticleReader from '../components/assoadmin/articles/article-reader'

export default {
    path: '/assoadmin',
    name: 'AssoAdminIndex',
    component: AssoAdminIndex,
    children: [
		{
			path: 'articlelist/',
			name: 'UsrArticleList',
			component: ArticleList
		},
        {
            path: 'articles/edit/:id',
            name: 'UsrArticleEditor',
            component: ArticleEditor,
            props: true
        },
        {
            path: 'articles/:id',
            name: 'UsrArticleReader',
            component: ArticleReader,
            props: true
        }
	]
}
