package exort.articlemanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.articlemanager.entity.Article;

public interface ArticleRepository extends MongoRepository<Article,Integer> {

}
