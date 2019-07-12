package com.exort.association_member_management.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@ApiModel(value = "Application", description = "申请信息实体对象")
public class Application {

    public static final int UNHANDLED=0;
    public static final int ACCEPTED=1;
    public static final int REFUSED=2;
    public static final int CANCELED=3;

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "申请信息ID",name="id")
    int id;
    @ApiModelProperty(value = "申请人的用户ID",name="userId'")
    int userId;
    @ApiModelProperty(value = "社团ID",name="associationId")
    int associationId;
    @ApiModelProperty(value = "部门ID",name="departmentId")
    int departmentId;
    @ApiModelProperty(value = "申请材料ID",name="materials")
    ArrayList<Integer> materials;
    @ApiModelProperty(value = "申请时间",name="applyTime")
    Date applyTime;
    @ApiModelProperty(value = "申请信息状态",name="state")
    int state;
}
