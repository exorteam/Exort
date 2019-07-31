package exort.association_member_manager.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DepartmentId implements Serializable {
    @NotNull
    String associationId;
    @NotNull
    int departmentId;
}
