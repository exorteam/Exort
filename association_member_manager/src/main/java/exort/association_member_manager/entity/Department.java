package exort.association_member_manager.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@IdClass(DepartmentId.class)
@ApiModel(value = "Department", description = "部门信息实体对象")
public class Department extends exort.api.http.member.entity.DepartmentInfo {

    @Id
    String associationId;
    @Id
    int departmentId;
    @ApiModelProperty(value = "部门名称", name = "name")
    String name;
    @ApiModelProperty(value = "部门描述", name = "description")
    String description;
    @ApiModelProperty(value = "父节点ID", name = "parentId")
    int parentId;



    public Department(String associationId, String name, String description, int parentId) {
        this.associationId = associationId;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public Department(String associationId, int departmentId, String name, String description, int parentId) {
        this.associationId = associationId;
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public Department(String associationId) {
        this.associationId = associationId;
    }

    public Department() {
    }
}

