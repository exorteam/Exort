package exort.associationmanager.entity;

import java.util.Date;

import lombok.Data;

@Data
public class AssociationFilterParams {

	private String keyword;
	private int authorId;
	private Date startTime;
	private Date endTime;
	private int state;
	private int createMethod;

}
