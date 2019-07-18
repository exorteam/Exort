package exort.api.http.entity;

import lombok.Data;

@Data
public class Department {
    int associationId;
    int departmentId;
    String name;
    String description;
    int parentId;

}
