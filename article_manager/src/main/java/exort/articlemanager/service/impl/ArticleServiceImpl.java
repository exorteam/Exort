package exort.articlemanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.repository.ArticleRepository;
import exort.articlemanager.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleRepository repository;

	public Article createArticle(Article article){
		if(article.getTitle() == null || article.getContent() == null || article.getAuthors() == null){
			return null;
		}

		Date currentTime = new Date();
		article.setCreateTime(currentTime);
		article.setPublishTime(null);
		article.setLastPublishTime(currentTime);
		article.setLastModifyTime(currentTime);
		article.setState(0);
		article.setCreateMethod(0);

		Integer articleId = 1;
		while(repository.existsById(articleId)){++articleId;}
		article.setId(articleId);

		return repository.save(article);

	}

	public boolean deleteArticle(int articleId){
		if(!repository.existsById(articleId))return false;

		repository.deleteById(articleId);
		return true;
	}

	public boolean updateArticle(int articleId,String title,String content){
		if(!repository.existsById(articleId))return false;

		Article article = repository.findById(articleId).get();
		article.setTitle(title);
		article.setContent(content);

		return true;
	}

	public Article getArticle(int articleId){
		return repository.findById(articleId).get();
	}

	public List<Article> listArticle(ArticleFilterParams params){
		List<Article> articles = repository.findAll();

		Integer state = params.getState();
		if(state != null){
			articles.removeIf(article -> !state.equals(article.getState()));
			if(articles.isEmpty())return articles;
		}

		Integer createMethod = params.getCreateMethod();
		if(createMethod != null){
			articles.removeIf(article -> !createMethod.equals(article.getCreateMethod()));
			if(articles.isEmpty())return articles;
		}

		Date startTime = params.getStartTime();
		if(startTime != null){
			articles.removeIf(article -> startTime.after(article.getLastPublishTime()));
			if(articles.isEmpty())return articles;
		}

		Date endTime = params.getEndTime();
		if(endTime != null){
			articles.removeIf(article -> endTime.before(article.getLastPublishTime()));
			if(articles.isEmpty())return articles;
		}

		String keyword = params.getKeyword();
		if(keyword != null){
			articles.removeIf(article -> !article.getTitle().contains(keyword)||!article.getContent().contains(keyword));
			if(articles.isEmpty())return articles;
		}

		Integer authorId = params.getAuthorId();
		if(authorId != null){
			articles.removeIf(article -> !article.getAuthors().contains(authorId));
			if(articles.isEmpty())return articles;
		}

		return articles;
	}

	public boolean publishArticle(int articleId){
		if(!repository.existsById(articleId))return false;

		Article article = repository.findById(articleId).get();
		if(article.getState() != 0)return false;

		article.setState(1);
		repository.save(article);

		return true;
	}

	public boolean withdrawArticle(int articleId){
		if(!repository.existsById(articleId))return false;

		Article article = repository.findById(articleId).get();
		if(article.getState() != 1)return false;

		article.setState(0);
		repository.save(article);

		return true;
	}
}

