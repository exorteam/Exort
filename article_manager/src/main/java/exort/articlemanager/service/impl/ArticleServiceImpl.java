package exort.articlemanager.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.articlemanager.component.AutoIncIdGenerator;
import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.repository.ArticleRepository;
import exort.articlemanager.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleRepository repository;
	@Autowired
	private AutoIncIdGenerator autoId;

	private final String AUTO_ID_NAME = "article_auto_id";

	public Article createArticle(Article article){
		if(article.getTitle() == null || article.getContent() == null || article.getAssociationId() == null){
			return null;
		}

		Date currentTime = new Date();
		article.setId(autoId.getNextId(AUTO_ID_NAME));
		article.setCreateTime(currentTime);
		article.setPublishTime(null);
		article.setLastPublishTime(null);
		article.setLastModifyTime(currentTime);
		article.setState(ArticleStatus.UNPUBLISHED);
		article.setCreateMethod(0);

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
		
		repository.save(article);

		return true;
	}

	public Article getArticle(int articleId){
		return repository.findById(articleId).get();
	}

	public PagedData<Article> listArticle(ArticleFilterParams params,PageQuery pq){

		final String keyword = params.getKeyword();
		if(keyword == null || keyword.isEmpty()){
			final Page<Article> res = repository.findAll(PageRequest.of(pq.getPageNum(),pq.getPageSize()));
			return new PagedData<Article>(res.getNumber(),res.getSize(),repository.count(),res.getContent());
			//return repository.findAll();
		}
		else{
			TextCriteria criteria = TextCriteria
				.forDefaultLanguage()
				.matchingPhrase(params.getKeyword());

			final Page<Article> res = repository.findAllBy(
					criteria,
					PageRequest.of(pq.getPageNum(),pq.getPageSize())
					);

			return new PagedData<Article>(res.getNumber(),res.getSize(),repository.count(),res.getContent());

		}
	}

	public boolean publishArticle(int articleId){
		if(!repository.existsById(articleId))return false;

		Article article = repository.findById(articleId).get();
		if(article.getState() != 0)return false;

		if(article.getPublishTime() != null){
			article.setLastPublishTime(article.getPublishTime());
		}
		article.setPublishTime(new Date());
		article.setState(ArticleStatus.PUBLISHED);
		repository.save(article);

		return true;
	}

	public boolean withdrawArticle(int articleId){
		if(!repository.existsById(articleId))return false;

		Article article = repository.findById(articleId).get();
		if(article.getState() != 1)return false;

		article.setState(ArticleStatus.UNPUBLISHED);
		repository.save(article);

		return true;
	}
}

