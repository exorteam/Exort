package exort.api.http.assomgr.entity;

import lombok.Data;

import java.util.List;

@Data
public class AssociationFilterParams {
    private String keyword;
    private List<String> tags;
    private Integer state;
}
