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
	private Integer state; //0 blocked,1 active
	private String reason;
	public boolean hasTags(List<String> ask_tags){
		int len = ask_tags.size();
		for (int i = 0; i < len; i++) {
			if (tags.contains(ask_tags.get(i))){
				return true;
			}
		}
		return false;
	}
}
