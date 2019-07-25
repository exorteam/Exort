package exort.articlemanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exort.articlemanager.component.AutoIncIdGenerator;
import exort.articlemanager.entity.Article;
import exort.articlemanager.repository.ArticleRepository;
import exort.articlemanager.service.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticlemanagerApplicationTests {

	@Autowired
	private ArticleRepository repository;
	@Autowired
	private ArticleService service;
	@Autowired
	private AutoIncIdGenerator idGenerator;

	private final int ARTICLE_NUM = 10;
	private List<Article> articles = new ArrayList<>();

	@Before
	public void createTestArticles(){
		for(int i=0;i<ARTICLE_NUM;i++){
			Article article = new Article();
			article.setTitle(UUID.randomUUID().toString());
			article.setContent(UUID.randomUUID().toString());
			article.setAuthors(new ArrayList<Integer>());
			articles.add(article);
		}
	}

	@After
	public void clearTestArticles(){
		repository.deleteAll(articles);
		articles.clear();
	}


	@Test
	public void testCreate() {
		//idGenerator.getNextId("test");
		//List<Integer> ids = new ArrayList<>();
		for(int i=0;i<ARTICLE_NUM;i++){
			//int id = service.createArticle(articles.get(i)).getId();
			//Article e = articles.get(i);
			//e.setId(id);
			//articles.set(i,e);
			//ids.add(id);
		}
		//for(int i=0;i<ARTICLE_NUM;i++){
		//    Article contrast = repository.findById(ids.get(i)).get();
		//    Assert.assertEquals(contrast,articles.get(i));
		//}

	}

}
