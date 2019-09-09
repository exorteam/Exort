package exort.api.http.perm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String name;
    private String category;
    private String description;

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
