package exort.articlemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.service.ArticleService;

@RestController
public class ArticleController {
	@Autowired
	private ArticleService service;

	@PostMapping("/create")
	public Article createArticle(@RequestBody Article article){
		return service.createArticle(article);
	}

	@GetMapping("/delete")
	public boolean deleteArticle(@RequestParam int articleId){
		return service.deleteArticle(articleId);
	}

	@PostMapping("/update")
	public boolean updateArticle(@RequestParam int articleId, @RequestParam String title, @RequestBody String content){
		return service.updateArticle(articleId,title,content);
	}

	@GetMapping("/get")
	public Article getArticle(@RequestParam int articleId){
		return service.getArticle(articleId);
	}

	@PostMapping("/list")
	public List<Article> listArticle(@RequestBody ArticleFilterParams params){
		return service.listArticle(params);
	}

	@GetMapping("/publish")
	public boolean publishArticle(@RequestParam int articleId){
		return service.publishArticle(articleId);
	}

	@GetMapping("/withdraw")
	public boolean withdrawArticle(@RequestParam int articleId){
		return service.withdrawArticle(articleId);
	}
}
