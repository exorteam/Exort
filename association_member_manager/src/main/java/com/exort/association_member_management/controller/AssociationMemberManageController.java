package com.exort.association_member_management.controller;

import com.exort.association_member_management.dto.ResponseCode;
import com.exort.association_member_management.entity.Application;
import com.exort.association_member_management.entity.Department;
import com.exort.association_member_management.service.AssociationMemberManageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/association-member-management"})
@Api(value = "AssociationMemberManageController", tags = {"社团成员管理接口"})
public class AssociationMemberManageController {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    @GetMapping(value = "/get-spec-appli", produces = "application/json")
    @ApiOperation(value = "得到某个具体的申请信息")
    @ApiImplicitParam(name = "applyId", value = "申请信息ID", required = true, dataType = "int")
    public ResponseCode getSpecApplication(@RequestParam(value = "applyId") int applyId) {
        return associationMemberManageService.getSpecApplication(applyId);
    }

    @GetMapping(value = "get-some-applis", produces = "application/json")
    @ApiOperation(value = "查找某些申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "申请人ID", dataType = "int"),
            @ApiImplicitParam(name = "associationId", value = "社团ID", dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "终止时间", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "第几页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "单页条数", dataType = "int")
    })
    public ResponseCode getSomeApplications(@RequestParam(value = "userId") Optional<Integer> userId, @RequestParam(value = "associationId") Optional<Integer> associationId, @RequestParam(value = "departmentId") Optional<Integer> departmentId, @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Optional<Date> startTime, @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Optional<Date> endTime, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return associationMemberManageService.getSomeApplications(userId, associationId, departmentId, startTime, endTime, page, size);
    }

    @PutMapping(value = "adopt-appli", produces = "application/json")
    @ApiOperation(value = "通过某个申请")
    @ApiImplicitParam(name = "applyId", value = "申请信息ID", required = true, dataType = "int")
    public ResponseCode adoptApplication(int applyId) {

        return associationMemberManageService.adoptApplication(applyId);
    }

    @PutMapping(value = "refuse-appli", produces = "application/json")
    @ApiOperation(value = "拒绝某个申请")
    @ApiImplicitParam(name = "applyId", value = "申请信息ID", required = true, dataType = "int")
    public ResponseCode refuseApplication(int applyId) {
        return associationMemberManageService.refuseApplication(applyId);
    }

    @GetMapping(value = "get-department-tree", produces = "application/json")
    @ApiOperation(value = "得到部门树")
    @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int")
    public ResponseCode getDepartmentTree(@RequestParam(value = "associationId") int associationId) {
        return associationMemberManageService.getDepartmentTree(associationId);
    }


    @GetMapping(value = "get-spec-department-info", produces = "application/json")
    @ApiOperation(value = "得到某个部门的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int")
    })
    public ResponseCode getSpecDepartmentInfo(@RequestParam(value = "associationId") int associationId, @RequestParam(value = "departmentId") int departmentId) {
        return associationMemberManageService.getSpecDepartmentInfo(associationId, departmentId);
    }


    @PostMapping(value = "create-department", produces = "application/json")
    @ApiOperation(value = "创建部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentDesc", value = "部门描述", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentId", value = "部门父节点", required = true, dataType = "int")
    })
    public ResponseCode createDepartment(int associationId, String departmentName, String departmentDesc, int parentId) {
        return associationMemberManageService.createDepartment(associationId, departmentName, departmentDesc, parentId);
    }


    @DeleteMapping(value = "delete-department", produces = "application/json")
    @ApiOperation(value = "删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int")
    })
    public ResponseCode deleteDepartment(int associationId, int departmentId) {
        return associationMemberManageService.deleteDepartment(associationId, departmentId);
    }


    @PutMapping(value = "edit-department", produces = "application/json")
    @ApiOperation(value = "编辑部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentName", value = "部门名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentDesc", value = "部门描述", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentId", value = "部门父节点", required = true, dataType = "int")
    })
    public ResponseCode editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId) {
        return associationMemberManageService.editDepartment(associationId, departmentId, departmentName, departmentDesc, parentId);
    }


    @GetMapping(value = "get-spec-memberlist", produces = "application/json")
    @ApiOperation(value = "得到某个部门的成员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "单页几条", required = true, dataType = "int"),
    })
    public ResponseCode getSpecMemberList(@RequestParam(value = "associationId") int associationId, @RequestParam(value = "departmentId") int departmentId, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        return associationMemberManageService.getSpecMemberList(associationId, departmentId, page, size);
    }


    @DeleteMapping(value = "remove-one-from-department", produces = "application/json")
    @ApiOperation(value = "将成员从某个部门中移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int"),
    })
    public ResponseCode removeOneFromDepartment(int associationId, int departmentId, int userId) {
        return associationMemberManageService.removeOneFromDepartment(associationId, departmentId, userId);
    }


    @PostMapping(value = "add-one-to-department", produces = "application/json")
    @ApiOperation(value = "为某个部门添加成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int"),
    })
    public ResponseCode addOneToDepartment(int associationId, int departmentId, int userId) {
        return associationMemberManageService.addOneToDepartment(associationId, departmentId, userId);
    }


    @PutMapping(value = "change-one-to-department", produces = "application/json")
    @ApiOperation(value = "更改某个成员所属部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "directionDepartmentId", value = "目标部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int"),
    })
    public ResponseCode changeOneToDepartment(int associationId, int directionDepartmentId, int userId) {
        return associationMemberManageService.changeOneToDepartment(associationId, directionDepartmentId, userId);
    }


    @GetMapping(value = "check-user-permission-in-association", produces = "application/json")
    @ApiOperation(value = "判断该用户在某个社团是否有某个权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "permission", value = "用户权限", required = true, dataType = "int"),
    })
    public ResponseCode checkUserPermissionInAssociation(@RequestParam(value = "associationId") int associationId, @RequestParam(value = "userId") int userId, @RequestParam(value = "permission") int permission) {
        return associationMemberManageService.checkUserPermissionInAssociation(associationId, userId, permission);
    }


    @GetMapping(value = "get-user-association", produces = "application/json")
    @ApiOperation(value = "查询用户所属社团")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    public ResponseCode getUserAssociation(@RequestParam(value = "userId") int userId) {
        return associationMemberManageService.getUserAssociation(userId);
    }


    @GetMapping(value = "get-user-department", produces = "application/json")
    @ApiOperation(value = "查询用户在指定社团中所属部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    })
    public ResponseCode getUserDepartment(@RequestParam(value = "associationId") int associationId, @RequestParam(value = "userId") int userId) {
        return associationMemberManageService.getUserDepartment(associationId, userId);
    }


    @DeleteMapping(value = "delete-one-in-association", produces = "application/json")
    @ApiOperation(value = "在社团中删除某个成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    })
    public ResponseCode deleteOneInAssociation(int associationId, int userId) {
        return associationMemberManageService.deleteOneInAssociation(associationId, userId);
    }


    @PostMapping(value = "add-one-to-association", produces = "application/json")
    @ApiOperation(value = "为社团添加一个成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    })
    public ResponseCode addOneToAssociation(int associationId, int userId) {
        return associationMemberManageService.addOneToAssociation(associationId, userId);
    }


}
