package exort.associationmanager.entity;


import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@AllArgsConstructor
public class Association {
	@Id
	private String id;
	private String name;
    private String description;
    private String logo;
	private List<String> tags;
	private Integer state; //0 blocked,1 active
	private String reason;

	public Association(){}

	public boolean hasTags(List<String> ask_tags){
		int len = ask_tags.size();
		for (int i = 0; i < len; i++) {
			if (tags.contains(ask_tags.get(i))){
				return true;
			}
		}
		return false;
	}
	public boolean hasKeyword(String keyword){
		if(this.name==null || this.description==null){
			return false;
		}
		if(this.name.contains(keyword)||this.description.contains(keyword)){
			return true;
		}
		return false;
	}
}
