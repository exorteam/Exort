package exort.review_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationInfo {

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("logo")
    private String logo;

    @Field("tags")
    private List<String> tags;

    public AssociationInfo(String name, String description, String logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.tags = new ArrayList<>();
    }

}
