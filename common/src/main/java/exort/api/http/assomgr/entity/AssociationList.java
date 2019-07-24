package exort.api.http.assomgr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationList {
    Integer totalSize;
    Integer pageSize;
    List<Association> content;
    Integer pageNumber;
}