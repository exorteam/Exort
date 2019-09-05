package exort.api.http.review.entity.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationInfo {

    private String name;

    private String description;

    private String logo;

    private List<String> tags;

    public AssociationInfo(String name, String description, String logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.tags = new ArrayList<>();
    }

}
