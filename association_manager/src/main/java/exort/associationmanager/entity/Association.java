package exort.associationmanager.entity;


import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
@AllArgsConstructor
public class Association {
	@Id
	private String id;
	@TextIndexed
	private String name;
    private String description;
    private String logo;
	private List<String> tags;
	private Integer state; //0 blocked,1 active
	private String reason;

	public Association(){}

	public boolean hasTags(List<String> ask_tags){
		int len = ask_tags.size();
		if(len==0){
			return true;
		}
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

	public exort.api.http.assomgr.entity.Association toCommon(){
		exort.api.http.assomgr.entity.Association association = new exort.api.http.assomgr.entity.Association();
		association.setDescription(this.getDescription());
		association.setId(this.getId());
		association.setLogo(this.getLogo());
		association.setName(this.getName());
		association.setReason(this.getReason());
		association.setState(this.getState());
		association.setTags(this.getTags());
		return association;
	}
}
