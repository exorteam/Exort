package exort.apiserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.service.PermService;
import exort.api.http.assomgr.service.AssociationManagerService;
import exort.apiserver.service.ArticleService;
import exort.apiserver.service.ArticleService.Article;
import exort.apiserver.service.ArticleService.ArticleFilterParam;
import exort.apiserver.service.CommunityService;
import exort.apiserver.service.CommunityService.CommunityMessage;

@RestController
@RequestMapping(path="/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleSvc;
	@Autowired
	private PermService permSvc;
	@Autowired
	private CommunityService cmSvc;
	@Autowired
	private AssociationManagerService assoSvc;

	@PostMapping
	public ApiResponse createArticle(
			@RequestAttribute(name="id", required=false) Long operatorId,
			@RequestBody Article e){
		return articleSvc.createArticle(e);
	}

	@GetMapping("/{id}")
	public ApiResponse getArticle(
			@PathVariable("id") int articleId){
		return articleSvc.getArticle(articleId);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteArticle(
			@RequestAttribute(name="id", required=false) Long operatorId,
			@PathVariable("id") int articleId){
		return articleSvc.deleteArticle(articleId);
	}

	@PutMapping("/{id}")
	public ApiResponse updateArticle(
			@RequestAttribute(name="id", required=false) Long operatorId,
			@PathVariable("id") int articleId,
			@RequestBody Article e){
		return articleSvc.updateArticle(articleId,e);
	}

	@PostMapping("/{id}/publish")
	public ApiResponse publishArticle(
			@RequestAttribute(name="id", required=false) Long operatorId,
			@PathVariable("id") int articleId,
			@RequestParam boolean publish){
		if(publish){
			//TODO: notify subscribed users
			final Article article = (Article)articleSvc.getArticle(articleId).getData();
			CommunityMessage msg = new CommunityMessage();
			final String assoId = article.getAssociationId();
			final String assoName = assoSvc.getAssociation(assoId).getData().getName();
			msg.setSenderAssociation(assoId);
			msg.setSenderName(assoName);
			msg.setContent("已发布新文章:\""+article.getTitle()+"\"");
			System.out.println(cmSvc.postNotifications(msg));
		}
		return articleSvc.publishArticle(articleId,publish);
	}

	@PostMapping("/list")
	public ApiResponse listArticle(
			@RequestBody ArticleFilterParam param,
			PageQuery pageQuery){
		return articleSvc.listArticle(param,pageQuery);
	}

	@PostMapping("/list/asso")
	public ApiResponse listArticle(
			@RequestBody List<String> assoIds,
			PageQuery pageQuery){
		return articleSvc.listArticleWithAssociation(assoIds,pageQuery);
	}

	@GetMapping("/list/user")
	public ApiResponse listArticle(@RequestAttribute(name="id", required=false) Integer operatorId, PageQuery pageQuery) {
		ApiResponse<List<String>> res = cmSvc.listSubscribed(operatorId);
		List<String> associationIds = res.getData();
		if (associationIds == null) {
			throw new ApiError(500, res.getError(), res.getMessage());
		}
		ArticleFilterParam filter = new ArticleFilterParam();
		filter.setAuthorIds(associationIds);
		filter.setState(1);
		pageQuery.setSortBy("publishTime");
		return articleSvc.listArticle(filter, pageQuery);
	}

}
