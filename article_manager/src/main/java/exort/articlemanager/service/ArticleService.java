package exort.articlemanager.service;

import java.util.List;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;

public interface ArticleService {

	public class ArticleStatus {

		private ArticleStatus(){}

		public final static int UNPUBLISHED 	= 0;
		public final static int PUBLISHED 		= 1;
		public final static int ERROR			= -1;

	}

	public Article 			createArticle(Article article);
	public boolean 			deleteArticle(int articleId);
	public boolean 			updateArticle(int articleId,String title,String content);
	public Article 			getArticle(int articleId);
	public boolean 			publishArticle(int articleId);
	public boolean 			withdrawArticle(int articleId);

	public PagedData<Article>	listArticleOfAssociationIds(List<String> ids,PageQuery pq);
	public PagedData<Article> 	listArticle(ArticleFilterParams params,PageQuery pq);
}

