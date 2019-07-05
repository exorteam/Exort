package exort.associationmanager.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleFilterParams {

	private String keyword;
	private Integer authorId;
	private Date startTime;
	private Date endTime;
	private Integer state;
	private Integer createMethod;

}
