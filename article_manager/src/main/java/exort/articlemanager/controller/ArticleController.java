package exort.articlemanager.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.service.ArticleService;

@RestController
public class ArticleController {

	private static final String rootPath = "/articles";

	@Autowired
	private ArticleService service;

	@PostMapping(rootPath)
	public Article createArticle(@RequestBody Article article){
		return service.createArticle(article);
	}

	@DeleteMapping(rootPath+"/{articleId}")
	public boolean deleteArticle(@PathVariable("articleId") int articleId){
		return service.deleteArticle(articleId);
	}

	@PutMapping(rootPath+"/{articleId}")
	public boolean updateArticle(@PathVariable("articleId") int articleId, @RequestBody Article article){
		return service.updateArticle(articleId,article.getTitle(),article.getContent());
	}

	@GetMapping(rootPath+"/{articleId}")
	public Article getArticle(@PathVariable("articleId") int articleId){
		return service.getArticle(articleId);
	}

	@GetMapping(rootPath)
	public List<Article> listArticle(@RequestBody ArticleFilterParams params){
		return service.listArticle(params);
	}

	@PatchMapping(rootPath+"/{articleId}")
	public boolean patchArticle(@PathVariable("articleId") int articleId, @RequestBody HashMap<String,Boolean> body){
		if(body.get("publish")){
			return service.publishArticle(articleId);
		}
		else{
			return service.withdrawArticle(articleId);
		}
	}

}
