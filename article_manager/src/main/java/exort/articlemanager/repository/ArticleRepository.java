package exort.articlemanager.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import exort.articlemanager.entity.Article;

public interface ArticleRepository extends MongoRepository<Article,Integer> {

	Page<Article> findAllBy(TextCriteria criteria,Pageable pageable);
	//List<Article> findAllBy(TextCriteria criteria);
	Page<Article> findAll(Pageable pageable);
}
