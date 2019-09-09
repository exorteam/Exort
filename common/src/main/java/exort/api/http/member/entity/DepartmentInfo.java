package exort.api.http.member.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInfo {
    String associationId;
    int departmentId;
    String name;
    String description;
    int parentId;


    public DepartmentInfo(String associationId, String name, String description, int parentId) {
        this.associationId = associationId;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }
}
