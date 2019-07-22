package exort.api.http.perm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private String name;
    private String category;
    private String description;

    public Permission(String name) {
        this.name = name;
    }

    public Permission(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
