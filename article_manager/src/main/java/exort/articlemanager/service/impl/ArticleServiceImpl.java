package exort.articlemanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

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

	public List<Article> listArticle(ArticleFilterParams params){

		TextCriteria criteria = TextCriteria
			.forDefaultLanguage()
			.matchingAny(params.getKeyword());

		List<Article> articles = repository.findAllBy(
				criteria,
				PageRequest.of(1,10)
				).getContent();

		//Integer state = params.getState();
		//if(state != null){
		//    articles.removeIf(article -> !state.equals(article.getState()));
		//    if(articles.isEmpty())return articles;
		//}

		//Integer createMethod = params.getCreateMethod();
		//if(createMethod != null){
		//    articles.removeIf(article -> !createMethod.equals(article.getCreateMethod()));
		//    if(articles.isEmpty())return articles;
		//}

		//Date startTime = params.getStartTime();
		//if(startTime != null){
		//    articles.removeIf(article -> startTime.after(article.getCreateTime()));
		//    if(articles.isEmpty())return articles;
		//}

		//Date endTime = params.getEndTime();
		//if(endTime != null){
		//    articles.removeIf(article -> endTime.before(article.getCreateTime()));
		//    if(articles.isEmpty())return articles;
		//}

		//String keyword = params.getKeyword();
		//if(keyword != null){
		//    articles.removeIf(article -> !article.getTitle().contains(keyword)&&!article.getContent().contains(keyword));
		//    if(articles.isEmpty())return articles;
		//}

		//String authorId = params.getAuthorId();
		//if(authorId != null){
		//    articles.removeIf(article -> !article.getAssociationId().equals(authorId));
		//    if(articles.isEmpty())return articles;
		//}

		return articles;
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

