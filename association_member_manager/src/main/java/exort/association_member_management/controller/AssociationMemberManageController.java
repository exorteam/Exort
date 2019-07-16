package exort.association_member_management.controller;

import exort.association_member_management.dto.ResponseCode;
import exort.association_member_management.service.AssociationMemberManageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@Api(value = "AssociationMemberManageController", tags = {"社团成员管理接口"})
public class AssociationMemberManageController {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    @GetMapping(value = "/get-spec-appli")
    @ApiOperation(value = "得到某个具体的申请信息")
    @ApiImplicitParam(name = "applyId", value = "申请信息ID", required = true, dataType = "int")
    public ResponseCode getSpecApplication(@RequestParam(value = "applyId") int applyId) {
        return associationMemberManageService.getSpecApplication(applyId);
    }

    @GetMapping(value = "get-some-applis")
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

    @PutMapping(value = "adopt-appli")
    @ApiOperation(value = "通过某个申请")
    public ResponseCode adoptApplication(int applyId) {

        return associationMemberManageService.adoptApplication(applyId);
    }

    @PutMapping(value = "refuse-appli")
    @ApiOperation(value = "拒绝某个申请")
    public ResponseCode refuseApplication(int applyId) {
        return associationMemberManageService.refuseApplication(applyId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "得到部门树")
    public ResponseCode getDepartmentTree(@PathVariable(value = "associationId") int associationId) {
        return associationMemberManageService.getDepartmentTree(associationId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "得到某个部门的信息")
    public ResponseCode getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return associationMemberManageService.getSpecDepartmentInfo(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "创建部门")
    public ResponseCode createDepartment(@PathVariable int associationId, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description, @RequestParam(value = "parentId") int parentId) {
        return associationMemberManageService.createDepartment(associationId, name, description, parentId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "删除部门")
    public ResponseCode deleteDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return associationMemberManageService.deleteDepartment(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "编辑部门")
    public ResponseCode editDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description, @RequestParam(value = "parentId") int parentId) {
        return associationMemberManageService.editDepartment(associationId, departmentId, name, description, parentId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/asssociations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "得到某个部门的成员列表")
    public ResponseCode getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return associationMemberManageService.getSpecMemberList(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}/members/{userId}")
    @ApiOperation(value = "将成员从某个部门中移除")
    public ResponseCode removeOneFromDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId) {
        return associationMemberManageService.removeOneFromDepartment(associationId, departmentId, userId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "为某个部门添加成员")
    public ResponseCode addOneToDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "userId") int userId) {
        return associationMemberManageService.addOneToDepartment(associationId, departmentId, userId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members/{userId}/permissions/{permission}")
    @ApiOperation(value = "判断该用户在某个社团是否有某个权限")
    public ResponseCode checkUserPermissionInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, @PathVariable(value = "permission") String permission) {
        return associationMemberManageService.checkUserPermissionInAssociation(associationId, userId, permission);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations")
    @ApiOperation(value = "查询用户所属社团")
    public ResponseCode getUserAssociation(@PathVariable(value = "userId") int userId) {
        return associationMemberManageService.getUserAssociation(userId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations/{associationId}/departments")
    @ApiOperation(value = "查询用户在指定社团中所属部门")
    public ResponseCode getUserDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return associationMemberManageService.getUserDepartment(associationId, userId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/members/{userId}")
    @ApiOperation(value = "在社团中删除某个成员")
    public ResponseCode deleteOneInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return associationMemberManageService.deleteOneInAssociation(associationId, userId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/members")
    @ApiOperation(value = "为社团添加一个成员")
    public ResponseCode addOneToAssociation(@PathVariable(value = "associationId") int associationId, @RequestParam(value = "userId") int userId) {
        return associationMemberManageService.addOneToAssociation(associationId, userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members")
    @ApiOperation(value = "得到社团成员列表")
    public ResponseCode getAssUserList(@PathVariable(value = "associationId") int associationId) {
        return associationMemberManageService.getAssoUserList(associationId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/department")
    @ApiOperation(value = "初始化部门")
    public ResponseCode initDepartment(@PathVariable(value = "associationId")int associationId, @RequestParam(value = "userId") int userId) {
        return associationMemberManageService.initDepartment(associationId,userId);
    }

}
