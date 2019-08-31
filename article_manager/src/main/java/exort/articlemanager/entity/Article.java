package exort.articlemanager.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;

import lombok.Data;

@Data
public class Article {

	@Id
	private int id;

	@TextIndexed
	private String title;
	@TextIndexed
	private String content;
	@TextIndexed
	private String associationId;

	private Date createTime;
	private Date publishTime;
	private Date lastPublishTime;
	private Date lastModifyTime;

	// 0: unpublished, 1: published, 2: error
	private int state;

	private int createMethod;
}
