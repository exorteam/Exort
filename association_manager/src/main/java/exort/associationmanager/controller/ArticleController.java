package exort.associationmanager.controller;

import exort.associationmanager.entity.Article;
import exort.associationmanager.entity.ArticleFilterParams;
import exort.associationmanager.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

