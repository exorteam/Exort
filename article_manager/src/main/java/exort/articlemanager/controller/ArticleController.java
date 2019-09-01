package exort.articlemanager.controller;

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

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.service.ArticleService;

@RestController
@RequestMapping(path="/articles")
public class ArticleController {
	@Autowired
	private ArticleService service;

	@PostMapping
	public ApiResponse createArticle(@RequestBody Article article){
		Article res = service.createArticle(article);
		if(res == null){
			throw new ApiError(404,"CreationErr","Error occured when creating article");
		}
		return new ApiResponse<Article>(res);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteArticle(@PathVariable("id") int articleId){
		boolean res = service.deleteArticle(articleId);
		if(!res){
			throw new ApiError(404,"DeletionErr","Error occured when deleting article["+String.valueOf(articleId)+"]");
		}
		return new ApiResponse<Boolean>(Boolean.valueOf(res));
	}

	@PutMapping("/{id}")
	public ApiResponse updateArticle(@PathVariable("id") int articleId, @RequestBody Map<String,String> body){
		String title = body.get("title");
		String content = body.get("content");
		if(title == null || content == null){
			return new ApiResponse<Boolean>(Boolean.FALSE);
		}
		boolean res = service.updateArticle(articleId,title,content);
		if(!res){
			throw new ApiError(404,"UpdateErr","Error occured when updating article["+String.valueOf(articleId)+"]");
		}
		return new ApiResponse<Boolean>(Boolean.valueOf(res));
	}

	@GetMapping("/{id}")
	public ApiResponse getArticle(@PathVariable("id") int articleId){
		Article res = service.getArticle(articleId);
		if(res == null){
			throw new ApiError(404,"GetErr","Error occured when getting article["+String.valueOf(articleId)+"]");
		}
		return new ApiResponse<Article>(res);
	}

	@GetMapping
	public ApiResponse listArticle(
			@RequestBody ArticleFilterParams params,
			@RequestParam int pageNum,
			@RequestParam int pageSize){
		PagedData<Article> res = service.listArticle(params,pageNum,pageSize);
		if(res == null){
			throw new ApiError(404,"GetErr","Error occured when listing article");
		}
		return new ApiResponse<PagedData>(res);
	}

	@PatchMapping("/{id}")
	public ApiResponse publishArticle(@PathVariable("id") int articleId,@RequestParam boolean publish){
		boolean res = false;
		if(publish){
			res = service.publishArticle(articleId);
		}
		else{
			res = service.withdrawArticle(articleId);
		}
		if(!res){
			throw new ApiError(404,"UpdateErr","Error occured when updating article["+String.valueOf(articleId)+"]");
		}
		return new ApiResponse<Boolean>(Boolean.valueOf(res));
	}

}
