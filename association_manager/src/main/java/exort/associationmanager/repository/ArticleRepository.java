package exort.associationmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.associationmanager.entity.Article;

public interface ArticleRepository extends MongoRepository<Article,Integer> {

}
