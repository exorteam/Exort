package exort.api.http.review.entity.details;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
