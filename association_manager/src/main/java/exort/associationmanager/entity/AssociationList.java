package exort.associationmanager.entity;

import lombok.Data;

import java.util.List;


@Data
public class AssociationList {
    List<Association> content;
    Integer totalSize;
    Integer pageSize;
    Integer pageNumber;
}
