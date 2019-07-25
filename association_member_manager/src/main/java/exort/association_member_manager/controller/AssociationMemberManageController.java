package exort.association_member_manager.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
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
@Api(value = "AssociationMemberManageController", tags = {"社团成员管理接口"})
public class AssociationMemberManageController {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    @Autowired
    private PermService ps;

    @RequestMapping(method = RequestMethod.POST, value = "/application/accept")
    @ApiOperation(value = "通过某个申请")
    public ApiResponse adoptApplication(@RequestBody CallbackParam<ApplicationDepartmentInfo> appli) {
        Application<ApplicationDepartmentInfo> application = appli.getApplication();

        if (associationMemberManageService.checkUserInAsso(application.getApplicantId(), application.getObject().getAssociationId())) {

            associationMemberManageService.adoptApplication(appli.getUserId().intValue(), appli.getEvent(), application);

            return new ApiResponse();

        } else {
            throw new ApiError(400, "InvalidUser", "用户已存在");
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "得到部门树")
    public ApiResponse<List<Department>> getDepartmentTree(@PathVariable(value = "associationId") int associationId) {

        List<Department> departments = associationMemberManageService.getDepartmentTree(associationId);

        if (departments.size() == 0) {
            throw new ApiError(404, "AssociationNotFound", "该社团不存在");
        }

        return new ApiResponse<List<Department>>(departments);

    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "得到某个部门的信息")
    public ApiResponse<Department> getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        Department department = associationMemberManageService.getSpecDepartmentInfo(associationId, departmentId);

        if (department == null) {
            throw new ApiError(404, "DepartmentNotFound", "该部门不存在");
        } else {
            return new ApiResponse<Department>(department);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments")
    @ApiOperation(value = "创建部门")
    public ApiResponse<Department> createDepartment(@PathVariable int associationId, @RequestBody DepartmentInfo departmentInfo) {
        if (!associationMemberManageService.checkAsso(associationId)) {
            throw new ApiError(404, "AssociationNotFound", "该社团不存在");
        }

        if (associationMemberManageService.checkDepartment(associationId, departmentInfo.getParentId()) || departmentInfo.getParentId() == 0) {
            return new ApiResponse<Department>(associationMemberManageService.createDepartment(associationId, departmentInfo.getName(), departmentInfo.getDescription(), departmentInfo.getParentId()));

        } else {
            throw new ApiError(400, "InvalidDepartment", "部门创建信息不合法");
        }

    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "删除部门")
    public ApiResponse<Department> deleteDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {

        if (!associationMemberManageService.checkDepartment(associationId, departmentId)) {
            throw new ApiError(404, "DepartmentNotFound", "不存在该部门");
        }

        return new ApiResponse<Department>(associationMemberManageService.deleteDepartment(associationId, departmentId));

    }


    @RequestMapping(method = RequestMethod.PUT, value = "/associations/{associationId}/departments/{departmentId}")
    @ApiOperation(value = "编辑部门")
    public ApiResponse<Department> editDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody DepartmentInfo departmentInfo) {
        if (!associationMemberManageService.checkDepartment(associationId, departmentId)) {
            throw new ApiError(404, "DepartmentNotFound", "不存在该部门");
        }

        if (associationMemberManageService.checkDepartment(associationId, departmentInfo.getParentId())) {
            throw new ApiError(400, "InvalidParentId", "无效父节点");
        }

        return new ApiResponse<>(associationMemberManageService.editDepartment(associationId, departmentId, departmentInfo.getName(), departmentInfo.getDescription(), departmentInfo.getParentId()));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "得到某个部门的成员列表")
    public ApiResponse<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {

        if (!associationMemberManageService.checkDepartment(associationId, departmentId)) {
            throw new ApiError(404, "DepartmentNotFound", "不存在该部门");
        }

        return new ApiResponse<>(associationMemberManageService.getSpecMemberList(associationId, departmentId));
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}/members/{userId}")
    @ApiOperation(value = "将成员从某个部门中移除")
    public ApiResponse<Boolean> removeOneFromDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId) {
        if (!associationMemberManageService.checkDepartment(associationId, departmentId)) {
            throw new ApiError(401, "DepartmentNotFound", "不存在该部门");
        }

        if (!associationMemberManageService.checkUserInAsso(userId, associationId)) {
            throw new ApiError(404, "UserNotFound", "社团中不存在该用户");
        }

        return new ApiResponse<>(associationMemberManageService.removeOneFromDepartment(associationId, departmentId, userId));
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments/{departmentId}/members")
    @ApiOperation(value = "为某个部门添加成员")
    public ApiResponse<Boolean> addOneToDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "userId") int userId) {
        if (!associationMemberManageService.checkDepartment(associationId, departmentId)) {
            throw new ApiError(404, "DepartmentNotFound", "不存在该部门");
        }

        return new ApiResponse<>(associationMemberManageService.addOneToDepartment(associationId, departmentId, userId));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members/{userId}/permissions/{permission}")
    @ApiOperation(value = "判断该用户在某个社团是否有某个权限")
    public ApiResponse<Boolean> checkUserPermissionInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, @PathVariable(value = "permission") String permission) {

        if (!associationMemberManageService.checkAsso(associationId)) {
            throw new ApiError(401, "AssociationNotFound", "不存在该社团");
        }

        if (!associationMemberManageService.checkUserInAsso(userId, associationId)) {
            throw new ApiError(402, "UserNotFound", "用户不在该社团中");
        }

        if (!associationMemberManageService.checkUserPerm(userId, associationId, permission)) {
            throw new ApiError(404, "PermissionNotFound", "没有该权限");
        }

        return new ApiResponse<>(true);

    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations")
    @ApiOperation(value = "查询用户所属社团")
    public ApiResponse<List<Integer>> getUserAssociation(@PathVariable(value = "userId") int userId) {
        List<String> assos = ps.getScopes(Long.valueOf(userId)).getData();
        if (assos.size() == 0) {
            throw new ApiError(400, "UserNotFound", "用户为加入任何社团");
        }

        return new ApiResponse<>(associationMemberManageService.getUserAssociation(assos));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/associations/{associationId}/departments")
    @ApiOperation(value = "查询用户在指定社团中所属部门")
    public ApiResponse<List<Department>> getUserDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        if (!associationMemberManageService.checkAsso(associationId)) {
            throw new ApiError(401, "AssociationNotFound", "不存在该社团");
        }

        if (!associationMemberManageService.checkUserInAsso(userId, associationId)) {
            throw new ApiError(404, "UserNotFound", "用户没有参与该社团");
        }

        return new ApiResponse<>(associationMemberManageService.getUserDepartment(associationId, userId));
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/members/{userId}")
    @ApiOperation(value = "在社团中删除某个成员")
    public ApiResponse<Boolean> deleteOneInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        if (!associationMemberManageService.checkAsso(associationId)) {
            throw new ApiError(404, "AssociationNotFound", "不存在该社团");
        }

        if (!associationMemberManageService.checkUserInAsso(userId, associationId)) {
            throw new ApiError(401, "UserNotFound", "社团中不存在该用户");
        }

        return new ApiResponse<>(associationMemberManageService.deleteOneInAssociation(associationId, userId));
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/members")
    @ApiOperation(value = "为社团添加一个成员")
    public ApiResponse<Boolean> addOneToAssociation(@PathVariable(value = "associationId") int associationId, @RequestParam(value = "userId") int userId) {
        if (!associationMemberManageService.checkAsso(associationId)) {
            throw new ApiError(404, "AssociationNotFound", "不存在该社团");
        }
        return new ApiResponse<Boolean>(associationMemberManageService.addOneToAssociation(associationId, userId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/members")
    @ApiOperation(value = "得到社团成员列表")
    public ApiResponse<List<Integer>> getAssUserList(@PathVariable(value = "associationId") int associationId) {

        if (!associationMemberManageService.checkAsso(associationId)) {
            throw new ApiError(404, "AssociationNotFound", "不存在该社团");
        }

        return new ApiResponse<>(associationMemberManageService.getAssoUserList(associationId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associations")
    @ApiOperation(value = "初始化部门")
    public ApiResponse<Boolean> initDepartment(@RequestBody InitAssociationInfo initAssociationInfo) {

        return new ApiResponse<>(associationMemberManageService.initDepartment(initAssociationInfo.getAssociationId(), initAssociationInfo.getUserId()));
    }

}
