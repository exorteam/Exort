package exort.articlemanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.service.ArticleService;

@RestController
@RequestMapping(path="/articles")
public class ArticleController {
	@Autowired
	private ArticleService service;

	@PostMapping
	public Article createArticle(@RequestBody Article article){
		return service.createArticle(article);
	}

	@DeleteMapping("/{id}")
	public boolean deleteArticle(@PathVariable("id") int articleId){
		return service.deleteArticle(articleId);
	}

	@PutMapping("/{id}")
	public boolean updateArticle(@PathVariable("id") int articleId, @RequestBody Map<String,String> body){
		String title = body.get("title");
		String content = body.get("content");
		if(title == null || content == null)return false;
		return service.updateArticle(articleId,title,content);
	}

	@GetMapping("/{id}")
	public Article getArticle(@PathVariable("id") int articleId){
		return service.getArticle(articleId);
	}

	@GetMapping
	public List<Article> listArticle(@RequestBody ArticleFilterParams params){
		return service.listArticle(params);
	}

	@PatchMapping("/{id}")
	public boolean publishArticle(@PathVariable("id") int articleId,@RequestParam boolean publish){
		if(publish){
			return service.publishArticle(articleId);
		}
		else{
			return service.withdrawArticle(articleId);
		}
	}

}

