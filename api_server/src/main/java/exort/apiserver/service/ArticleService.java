package exort.apiserver.service;

import java.util.Date;
import java.util.List;

import exort.api.http.common.entity.ApiResponse;
import lombok.Data;

public interface ArticleService {

	ApiResponse createArticle(Article e);
	ApiResponse deleteArticle(int id);
	ApiResponse updateArticle(int id,Article e);
	ApiResponse getArticle(int id);
	ApiResponse listArticle(ArticleFilterParam param);
	ApiResponse publishArticle(int id,boolean publish);

	@Data
	public class Article {
		private int id;
		private String title;
		private String content;
		private List<Integer> authors;
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
		private Integer authorId;
		private Date startTime;
		private Date endTime;
		private Integer state;
		private Integer createMethod;
	}

}
