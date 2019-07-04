package exort.articlemanager.service;

import java.util.List;

import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;

public interface ArticleService {

	public Article createArticle(Article article);
	public boolean deleteArticle(int articleId);
	public boolean updateArticle(int articleId,String title,String content);
	public Article getArticle(int articleId);
	public List<Article> listArticle(ArticleFilterParams params);
	public boolean publishArticle(int articleId);
	public boolean withdrawArticle(int articleId);
}

