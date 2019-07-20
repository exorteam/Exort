package exort.api.http.perm.entity;

import lombok.Data;

@Data
public class Permission {
    private String name;
    private String category;
    private String description;
}
