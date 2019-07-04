package main.java.exort.associationmanager.entity;


import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Association {

	@Id
	private int id;
	private String name;
    private String description;
    private String logo;
	private List<String> tags;


}
