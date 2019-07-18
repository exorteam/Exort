package exort.association_member_manager.controller;

import exort.api.http.entity.Application;
import exort.api.http.entity.ApplicationDepartmentInfo;
import exort.api.http.entity.ResponseCode;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.service.AssociationMemberManageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Api(value = "AssociationMemberManageController", tags = {"社团成员管理接口"})
public class AssociationMemberManageController {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    @RequestMapping(method = RequestMethod.POST,value = "/application/accept")
    @ApiOperation(value = "通过某个申请")
    public ResponseCode<Boolean> adoptApplication(@RequestParam(value = "userId") int userId, @RequestParam(value = "event") String event, @RequestParam(value = "application")Application<ApplicationDepartmentInfo> application, HttpServletResponse response) {

        return associationMemberManageService.adoptApplication(userId,event,application,response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "得到部门树")
    public ResponseCode<List<Department>> getDepartmentTree(@PathVariable(value = "associationId") int associationId, HttpServletResponse response) {
        return associationMemberManageService.getDepartmentTree(associationId, response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "得到某个部门的信息")
    public ResponseCode<Department> getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, HttpServletResponse response) {
        return associationMemberManageService.getSpecDepartmentInfo(associationId, departmentId, response);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "创建部门")
    public ResponseCode<Department> createDepartment(@PathVariable int associationId, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description, @RequestParam(value = "parentId") int parentId, HttpServletResponse response) {
        return associationMemberManageService.createDepartment(associationId, name, description, parentId, response);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "删除部门")
    public ResponseCode<Department> deleteDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, HttpServletResponse response) {
        return associationMemberManageService.deleteDepartment(associationId, departmentId, response);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "编辑部门")
    public ResponseCode<Department> editDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description, @RequestParam(value = "parentId") int parentId, HttpServletResponse response) {
        return associationMemberManageService.editDepartment(associationId, departmentId, name, description, parentId, response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/asssociations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "得到某个部门的成员列表")
    public ResponseCode<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, HttpServletResponse response) {
        return associationMemberManageService.getSpecMemberList(associationId, departmentId, response);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}/members/{userId}")
    @ApiOperation(value = "将成员从某个部门中移除")
    public ResponseCode<Boolean> removeOneFromDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId, HttpServletResponse response) {
        return associationMemberManageService.removeOneFromDepartment(associationId, departmentId, userId, response);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "为某个部门添加成员")
    public ResponseCode<Boolean> addOneToDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "userId") int userId, HttpServletResponse response) {
        return associationMemberManageService.addOneToDepartment(associationId, departmentId, userId, response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members/{userId}/permissions/{permission}")
    @ApiOperation(value = "判断该用户在某个社团是否有某个权限")
    public ResponseCode<Boolean> checkUserPermissionInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, @PathVariable(value = "permission") String permission, HttpServletResponse response) {
        return associationMemberManageService.checkUserPermissionInAssociation(associationId, userId, permission, response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations")
    @ApiOperation(value = "查询用户所属社团")
    public ResponseCode<List<Integer>> getUserAssociation(@PathVariable(value = "userId") int userId, HttpServletResponse response) {
        return associationMemberManageService.getUserAssociation(userId, response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations/{associationId}/departments")
    @ApiOperation(value = "查询用户在指定社团中所属部门")
    public ResponseCode<List<Department>> getUserDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, HttpServletResponse response) {
        return associationMemberManageService.getUserDepartment(associationId, userId, response);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/members/{userId}")
    @ApiOperation(value = "在社团中删除某个成员")
    public ResponseCode<Boolean> deleteOneInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, HttpServletResponse response) {
        return associationMemberManageService.deleteOneInAssociation(associationId, userId, response);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/members")
    @ApiOperation(value = "为社团添加一个成员")
    public ResponseCode<Boolean> addOneToAssociation(@PathVariable(value = "associationId") int associationId, @RequestParam(value = "userId") int userId, HttpServletResponse response) {
        return associationMemberManageService.addOneToAssociation(associationId, userId, response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members")
    @ApiOperation(value = "得到社团成员列表")
    public ResponseCode<List<Integer>> getAssUserList(@PathVariable(value = "associationId") int associationId, HttpServletResponse response) {

        return associationMemberManageService.getAssoUserList(associationId, response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associations")
    @ApiOperation(value = "初始化部门")
    public ResponseCode<Boolean> initDepartment(@RequestParam(value = "associationId") int associationId, @RequestParam(value = "userId") int userId, HttpServletResponse response) {

        return associationMemberManageService.initDepartment(associationId, userId, response);
    }

}
