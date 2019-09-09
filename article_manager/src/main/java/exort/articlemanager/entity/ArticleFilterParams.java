package exort.articlemanager.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ArticleFilterParams {

	private String keyword;
	private List<String> authorIds;
	private Date startTime;
	private Date endTime;
	private Integer state;
	private Integer createMethod;

}
