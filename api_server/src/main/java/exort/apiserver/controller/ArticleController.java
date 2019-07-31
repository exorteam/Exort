package exort.apiserver.controller;

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

import exort.api.http.common.entity.ApiResponse;
import exort.apiserver.service.ArticleService;
import exort.apiserver.service.ArticleService.Article;
import exort.apiserver.service.ArticleService.ArticleFilterParam;

@RestController
@RequestMapping(path="/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleSvc;

	@PostMapping
	public ApiResponse createArticle(@RequestBody Article e){
		return articleSvc.createArticle(e);
	}

	@GetMapping("/{id}")
	public ApiResponse getArticle(@PathVariable("id") int articleId){
		return articleSvc.getArticle(articleId);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteArticle(@PathVariable("id") int articleId){
		return articleSvc.deleteArticle(articleId);
	}

	@PutMapping("/{id}")
	public ApiResponse updateArticle(@PathVariable("id") int articleId,@RequestBody Article e){
		return articleSvc.updateArticle(articleId,e);
	}

	@PostMapping("/list")
	public ApiResponse listArticle(@RequestBody ArticleFilterParam param){
		return articleSvc.listArticle(param);
	}

	@PatchMapping("/{id}")
	public ApiResponse publishArticle(@PathVariable("id") int articleId,@RequestParam boolean publish){
		return articleSvc.publishArticle(articleId,publish);
	}

}
