package com.exort.association_member_management.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@ApiModel(value = "Department", description = "部门信息实体对象")
public class Department {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "部门ID",name="id")
    int id;
    @ApiModelProperty(value = "部门名称",name="name")
    String name;
    @ApiModelProperty(value = "部门描述",name="description")
    String description;
    @ApiModelProperty(value = "父节点ID",name="parentId")
    int parentId;
}
