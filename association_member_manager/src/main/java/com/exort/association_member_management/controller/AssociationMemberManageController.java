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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回申请信息"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getSpecApplication(@RequestParam(value = "applyId")int applyId) {
        return associationMemberManageService.getSpecApplication(applyId);
    }

    @GetMapping(value = "get-some-applis", produces = "application/json")
    @ApiOperation(value = "查找某些申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "申请人ID",dataType = "int"),
            @ApiImplicitParam(name = "associationId", value = "社团ID", dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "终止时间", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "第几页",dataType = "int"),
            @ApiImplicitParam(name = "size", value = "单页条数", dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回申请信息列表"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getSomeApplications(@RequestParam(value = "userId") Optional<Integer> userId, @RequestParam(value = "associationId")Optional<Integer> associationId, @RequestParam(value = "departmentId")Optional<Integer> departmentId, @RequestParam(value = "startTime")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Optional<Date> startTime, @RequestParam(value = "endTime")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Optional<Date> endTime,@RequestParam(value = "page",defaultValue = "0")int page,@RequestParam(value ="size",defaultValue = "20") int size) {
        return associationMemberManageService.getSomeApplications(userId, associationId, departmentId, startTime, endTime,page,size);
    }

    @PutMapping(value = "adopt-appli", produces = "application/json")
    @ApiOperation(value = "通过某个申请")
    @ApiImplicitParam(name = "applyId", value = "申请信息ID", required = true, dataType = "int")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回通过进程是否进行正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode adoptApplication(int applyId) {

        return associationMemberManageService.adoptApplication(applyId);
    }

    @PutMapping(value = "refuse-appli", produces = "application/json")
    @ApiOperation(value = "拒绝某个申请")
    @ApiImplicitParam(name = "applyId", value = "申请信息ID", required = true, dataType = "int")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回拒绝进程是否进行正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode refuseApplication(int applyId) {
        return associationMemberManageService.refuseApplication(applyId);
    }

    @GetMapping(value = "get-department-tree", produces = "application/json")
    @ApiOperation(value = "得到部门树")
    @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回部门树"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getDepartmentTree(@RequestParam(value = "associationId")int associationId) {
        return associationMemberManageService.getDepartmentTree(associationId);
    }


    @GetMapping(value = "get-spec-department-info", produces = "application/json")
    @ApiOperation(value = "得到某个部门的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回某个部门的具体信息"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getSpecDepartmentInfo(@RequestParam(value = "associationId")int associationId, @RequestParam(value = "departmentId")int departmentId) {
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回创建部门进程是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回删除部门进程是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回编辑部门进程是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode editDepartment(int departmentId, String departmentName, String departmentDesc, int parentId) {
        return associationMemberManageService.editDepartment(departmentId, departmentName, departmentDesc, parentId);
    }


    @GetMapping(value = "get-spec-memberlist", produces = "application/json")
    @ApiOperation(value = "得到某个部门的成员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "numInOnePage", value = "单页多少条信息", required = true, dataType = "int"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回部门成员ID列表"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getSpecMemberList(@RequestParam(value = "associationId")int associationId, @RequestParam(value = "departmentId")int departmentId, @RequestParam(value = "numInOnePage")int numInOnePage) {
        return associationMemberManageService.getSpecMemberList(associationId, departmentId, numInOnePage);
    }


    @DeleteMapping(value = "remove-one-from-department", produces = "application/json")
    @ApiOperation(value = "将成员从某个部门中移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回从某个部门移除成员进程是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回在某个部门中添加成员是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回变更某个成员部门是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回用户是否在此社团中有此权限"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode checkUserPermissionInAssociation(@RequestParam(value = "associationId")int associationId, @RequestParam(value = "userId")int userId, @RequestParam(value = "permission")int permission) {
        return associationMemberManageService.checkUserPermissionInAssociation(associationId, userId, permission);
    }


    @GetMapping(value = "get-user-association", produces = "application/json")
    @ApiOperation(value = "查询用户所属社团")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回用户所属社团ID列表"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getUserAssociation(@RequestParam(value = "userId")int userId) {
        return associationMemberManageService.getUserAssociation(userId);
    }


    @GetMapping(value = "get-user-department", produces = "application/json")
    @ApiOperation(value = "查询用户在指定社团中所属部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回用户所属部门列表"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode getUserDepartment(@RequestParam(value = "associationId")int associationId, @RequestParam(value = "userId")int userId) {
        return associationMemberManageService.getUserDepartment(associationId, userId);
    }


    @DeleteMapping(value = "delete-one-in-association", produces = "application/json")
    @ApiOperation(value = "在社团中删除某个成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "associationId", value = "社团ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回在某个社团中删除成员进程是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回向社团中添加成员进程是否正常"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "未进行身份认证"),
            @ApiResponse(code = 403, message = "用户没有权限这样做"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public ResponseCode addOneToAssociation(int associationId, int userId) {
        return associationMemberManageService.addOneToAssociation(associationId, userId);
    }


}
