package exort.associationmanager.entity;

import java.util.Date;

import lombok.Data;

@Data
public class AssociationFilterParams {

	private String keyword;
	private String name;
	private String description;
	private String tags[];
	private Integer state;


}
