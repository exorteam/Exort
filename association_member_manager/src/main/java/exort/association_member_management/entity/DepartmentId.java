package exort.association_member_management.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DepartmentId implements Serializable {
    @NotNull
    int associationId;
    @NotNull
    long departmentId;
}
