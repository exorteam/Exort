package exort.apiserver.service;

import java.util.Date;
import java.util.List;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import lombok.Data;

public interface ArticleService {

	ApiResponse createArticle(Article e);
	ApiResponse deleteArticle(int id);
	ApiResponse updateArticle(int id,Article e);
	ApiResponse getArticle(int id);
	ApiResponse publishArticle(int id,boolean publish);
	ApiResponse listArticle(ArticleFilterParam param, PageQuery pageQuery);
	ApiResponse listArticleWithAssociation(List<String> assoIds, PageQuery pageQuery);

	@Data
	public class Article {
		private int id;
		private String title;
		private String content;
		private String associationId;
		private Date createTime;
		private Date publishTime;
		private Date lastPublishTime;
		private Date lastModifyTime;
		// 0: unpublished, 1: published, 2: error
		private int state;
		private int createMethod;
	}

	@Data
	public class ArticleFilterParam {
		private String keyword;
		private List<String> authorIds;
		private Date startTime;
		private Date endTime;
		private Integer state;
		private Integer createMethod;
	}

}
