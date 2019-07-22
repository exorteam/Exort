package exort.association_member_manager.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.api.http.review.entity.CallbackParam;
import exort.association_member_manager.api_server.ApiServer;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.service.AssociationMemberManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Api(value = "AssociationMemberManageController", tags = {"社团成员管理接口"})
public class ApiController {
    @Autowired
    ApiServer apiServer;

    @RequestMapping(method = RequestMethod.POST, value = "/application/accept")
    @ApiOperation(value = "通过某个申请")
    public ApiResponse adoptApplication(@RequestBody CallbackParam<ApplicationDepartmentInfo> appli) {
        return apiServer.adoptApplication(appli);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "得到部门树")
    public ApiResponse<List<Department>> getDepartmentTree(@PathVariable(value = "associationId") int associationId) {
        return apiServer.getDepartmentTree(associationId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "得到某个部门的信息")
    public ApiResponse<Department> getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return apiServer.getSpecDepartmentInfo(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "创建部门")
    public ApiResponse<Department> createDepartment(@PathVariable int associationId, @RequestBody DepartmentInfo departmentInfo) {
        return apiServer.createDepartment(associationId, departmentInfo);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "删除部门")
    public ApiResponse<Department> deleteDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return apiServer.deleteDepartment(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "编辑部门")
    public ApiResponse<Department> editDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody DepartmentInfo departmentInfo) {

        return apiServer.editDepartment(associationId, departmentId,departmentInfo);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "得到某个部门的成员列表")
    public ApiResponse<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return apiServer.getSpecMemberList(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}/members/{userId}")
    @ApiOperation(value = "将成员从某个部门中移除")
    public ApiResponse<Boolean> removeOneFromDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId) {
        return apiServer.removeOneFromDepartment(associationId, departmentId, userId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "为某个部门添加成员")
    public ApiResponse<Boolean> addOneToDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "userId") int userId) {
        return apiServer.addOneToDepartment(associationId, departmentId, userId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members/{userId}/permissions/{permission}")
    @ApiOperation(value = "判断该用户在某个社团是否有某个权限")
    public ApiResponse<Boolean> checkUserPermissionInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, @PathVariable(value = "permission") String permission) {
        return apiServer.checkUserPermissionInAssociation(associationId, userId, permission);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations")
    @ApiOperation(value = "查询用户所属社团")
    public ApiResponse<List<Integer>> getUserAssociation(@PathVariable(value = "userId") int userId) {
        return apiServer.getUserAssociation(userId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations/{associationId}/departments")
    @ApiOperation(value = "查询用户在指定社团中所属部门")
    public ApiResponse<List<Department>> getUserDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return apiServer.getUserDepartment(associationId, userId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/members/{userId}")
    @ApiOperation(value = "在社团中删除某个成员")
    public ApiResponse<Boolean> deleteOneInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return apiServer.deleteOneInAssociation(associationId, userId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/members")
    @ApiOperation(value = "为社团添加一个成员")
    public ApiResponse<Boolean> addOneToAssociation(@PathVariable(value = "associationId") int associationId, @RequestParam(value = "userId") int userId) {
        return apiServer.addOneToAssociation(associationId, userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members")
    @ApiOperation(value = "得到社团成员列表")
    public ApiResponse<List<Integer>> getAssUserList(@PathVariable(value = "associationId") int associationId) {

        return apiServer.getAssoUserList(associationId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associations")
    @ApiOperation(value = "初始化部门")
    public ApiResponse<Boolean> initDepartment(@RequestBody InitAssociationInfo initAssociationInfo) {

        return apiServer.initDepartment(initAssociationInfo);
    }

}
