package exort.articlemanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import exort.articlemanager.component.AutoIncIdGenerator;
import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.repository.ArticleRepository;
import exort.articlemanager.service.ArticleService;
import exort.articlemanager.service.ArticleService.ArticleStatus;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ArticlemanagerApplicationTests {

	@Autowired
	private ArticleRepository repository;
	@Autowired
	private ArticleService service;
	@Autowired
	private AutoIncIdGenerator autoIncId;

	private final int 		ARTICLE_NUM 	= 10;
	private final String 	AUTO_ID_NAME 	= "article_test";
	private List<Article> 	articles 		= new ArrayList<>();

	@Before
	public void createTestArticles(){
		for(int i=0;i<ARTICLE_NUM;i++){
			Article article = new Article();
			article.setId(autoIncId.getNextId(AUTO_ID_NAME));
			article.setTitle(UUID.randomUUID().toString());
			article.setContent(UUID.randomUUID().toString());
			article.setAuthors(new ArrayList<Integer>());
			article.setState(ArticleStatus.UNPUBLISHED);
			articles.add(article);
		}
	}

	@After
	public void clearTestArticles(){
		repository.deleteAll(articles);
		articles.clear();
		autoIncId.removeName(AUTO_ID_NAME);
	}


	@Test
	public void testCreate() {
		List<Integer> ids = new ArrayList<>();
		for(int i=0;i<ARTICLE_NUM;i++){
			final int id = service.createArticle(articles.get(i)).getId();
			final Article e = articles.get(i);
			e.setId(id);
			articles.set(i,e);
			ids.add(id);
		}
		for(int i=0;i<ARTICLE_NUM;i++){
			final Article contrast = repository.findById(ids.get(i)).get();
			Assert.assertEquals(contrast,articles.get(i));
		}

	}

	@Test
	public void testGet() {
		repository.saveAll(articles);
		for(int i=0;i<ARTICLE_NUM;i++){
			final Article contrast = service.getArticle(articles.get(i).getId());
			Assert.assertEquals(articles.get(i),contrast);
		}
	}

	@Test
	public void testUpdate() {
		repository.saveAll(articles);
		for(int i=0;i<ARTICLE_NUM;i++){
			final Article e = articles.get(i);
			final String title = UUID.randomUUID().toString();
			final String content = UUID.randomUUID().toString();
			e.setTitle(title);
			e.setContent(content);
			articles.set(i,e);
			Assert.assertTrue(service.updateArticle(e.getId(),title,content));
		}
		for(int i=0;i<ARTICLE_NUM;i++){
			Article e = articles.get(i);
			Article contrast = repository.findById(e.getId()).get();
			Assert.assertEquals(e,contrast);
		}
	}

	@Test
	public void testPublishAndWithdraw() {
		repository.saveAll(articles);
		for(int i=0;i<ARTICLE_NUM;i++){
			final int id = articles.get(i).getId();
			final Article e1 = repository.findById(id).get();
			Assert.assertEquals(e1.getState(),ArticleStatus.UNPUBLISHED);

			service.publishArticle(id);
			final Article e2 = repository.findById(id).get();
			Assert.assertEquals(e2.getState(),ArticleStatus.PUBLISHED);

			service.withdrawArticle(id);
			final Article e3 = repository.findById(id).get();
			Assert.assertEquals(e3.getState(),ArticleStatus.UNPUBLISHED);
		}
	}

	@Test
	public void testListWithFilter() {
		final Date rightNow = new Date();
		Random rand = new Random(rightNow.getTime());
		final int authorId = rand.nextInt();
		ArrayList<Integer> authors = new ArrayList<>();
		authors.add(authorId);
		for(int i=0;i<5;i++){
			authors.add(rand.nextInt());
		}
		Article e = articles.get(0);
		e.setCreateTime(rightNow);
		e.setAuthors(authors);
		repository.save(e);

		final ArticleFilterParams filter1 = new ArticleFilterParams();
		filter1.setKeyword(e.getTitle());
		Assert.assertTrue(service.listArticle(filter1).contains(e));

		final ArticleFilterParams filter2 = new ArticleFilterParams();
		filter2.setState(ArticleStatus.UNPUBLISHED);
		Assert.assertTrue(service.listArticle(filter2).contains(e));

		final ArticleFilterParams filter3 = new ArticleFilterParams();
		filter3.setStartTime(new Date(rightNow.getYear()-1,rightNow.getMonth(),rightNow.getDay()));
		filter3.setEndTime(new Date(rightNow.getYear()+1,rightNow.getMonth(),rightNow.getDay()));
		Assert.assertTrue(service.listArticle(filter3).contains(e));

		final ArticleFilterParams filter4 = new ArticleFilterParams();
		filter4.setAuthorId(authorId);
		Assert.assertTrue(service.listArticle(filter4).contains(e));

	}

}
