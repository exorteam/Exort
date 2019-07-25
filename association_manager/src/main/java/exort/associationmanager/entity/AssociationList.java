package exort.associationmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationList {
    List<Association> content;
    Integer totalSize;
    Integer pageSize;
    Integer pageNumber;
}
