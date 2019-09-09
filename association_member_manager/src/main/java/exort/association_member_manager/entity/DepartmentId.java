package exort.association_member_manager.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentId implements Serializable {
    String associationId;
    int departmentId;
}
