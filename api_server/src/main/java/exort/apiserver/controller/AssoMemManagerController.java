package exort.apiserver.controller;

import java.util.List;

@RestController
@RequestMapping(path="/asso-mem")
public class AssoMemManagerController {
    @Autowired
	private AssoMemManagerService service;

    @RequestMapping(method = RequestMethod.POST, value = "/application/accept")
    public ApiResponse adoptApplication(@RequestBody CallbackParam<ApplicationDepartmentInfo> appli) {
        return apiServer.adoptApplication(appli);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments")
    public ApiResponse<List<Department>> getDepartmentTree(@PathVariable(value = "associationId") int associationId) {
        return apiServer.getDepartmentTree(associationId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return apiServer.getSpecDepartmentInfo(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/associations/{associationId}/departments")
    public ApiResponse<Department> createDepartment(@PathVariable int associationId, @RequestBody DepartmentInfo departmentInfo) {
        return apiServer.createDepartment(associationId, departmentInfo);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> deleteDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return apiServer.deleteDepartment(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/associations/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> editDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody DepartmentInfo departmentInfo) {

        return apiServer.editDepartment(associationId, departmentId,departmentInfo);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/associations/{associationId}/departments/{departmentId}/members")
    public ApiResponse<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return apiServer.getSpecMemberList(associationId, departmentId);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/associations/{associationId}/departments/{departmentId}/members/{userId}")
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
