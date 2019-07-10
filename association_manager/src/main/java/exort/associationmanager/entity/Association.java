package exort.associationmanager.entity;


import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Association {
	@Id
	private Integer id;
	private String name;
    private String description;
    private String logo;
	private List<String> tags;
	private Integer state; //blocked,active

	public boolean hasTags(List<String> in_tags){

	};


}
