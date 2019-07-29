package exort.apiserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.perm.service.PermService;
import exort.apiserver.service.AssoMemManagerService;
import exort.apiserver.service.AssoMemManagerService.*;

@RestController
@RequestMapping(path="/associations")
public class AssoMemManagerController {
	// adopt and init should not be called by api server
	// get operations are open
	// update, create, delete operations need specific permission
	//
	public static final String PERM_CREATE = "create-department";
	public static final String PERM_UPDATE = "update-department";
	public static final String PERM_DELETE = "delete-department";
	
    @Autowired
	private AssoMemManagerService amSvc;
	@Autowired
	private PermService permSvc;

	//@PostMapping("/accept")
    //public ApiResponse adoptApplication(@RequestBody CallbackParam<ApplicationDepartmentInfo> appli) {
    //    return amSvc.adoptApplication(appli);
    //}

	@GetMapping("/{id}/departments")
    public ApiResponse<List<Department>> getDepartmentTree(@PathVariable(value = "id") int associationId) {
        return amSvc.getDepartmentTree(associationId);
    }


	@GetMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return amSvc.getSpecDepartmentInfo(associationId, departmentId);
    }


	@PostMapping("/{associationId}/departments")
    public ApiResponse<Department> createDepartment(@RequestAttribute("id") int operatorId,@PathVariable int associationId, @RequestBody Department departmentInfo) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_CREATE)){
			return new ApiResponse<Department>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to create department on association["+String.valueOf(associationId)+"]");
		}
        return amSvc.createDepartment(associationId, departmentInfo);
    }


	@DeleteMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> deleteDepartment(@RequestAttribute("id") int operatorId,@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_DELETE)){
			return new ApiResponse<Department>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to delete department["+String.valueOf(departmentId)+"] on association["+String.valueOf(associationId)+"]");
		}
        return amSvc.deleteDepartment(associationId, departmentId);
    }


	@PutMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> editDepartment(@RequestAttribute("id") int operatorId,@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody Department departmentInfo) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_UPDATE)){
			return new ApiResponse<Department>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to delete department["+String.valueOf(departmentId)+"] on association["+String.valueOf(associationId)+"]");
		}
        return amSvc.editDepartment(associationId, departmentId,departmentInfo);
    }


	@GetMapping("/{associationId}/departments/{departmentId}/members")
    public ApiResponse<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return amSvc.getSpecMemberList(associationId, departmentId);
    }


	@DeleteMapping("/{associationId}/departments/{departmentId}/members/{userId}")
    public ApiResponse<Boolean> removeOneFromDepartment(@RequestAttribute("id") int operatorId,@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_UPDATE)){
			return new ApiResponse<Boolean>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to modify association["+String.valueOf(associationId)+"]");
		}
        return amSvc.removeOneFromDepartment(associationId, departmentId, userId);
    }


	@PostMapping("/{associationId}/departments/{departmentId}/members")
    public ApiResponse<Boolean> addOneToDepartment(@RequestAttribute("id") int operatorId,@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "userId") int userId) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_UPDATE)){
			return new ApiResponse<Boolean>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to modify association["+String.valueOf(associationId)+"]");
		}
        return amSvc.addOneToDepartment(associationId, departmentId, userId);
    }


	@GetMapping("/{associationId}/members/{userId}/permissions/{permission}")
    public ApiResponse<Boolean> checkUserPermissionInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, @PathVariable(value = "permission") String permission) {
        return amSvc.checkUserPermissionInAssociation(associationId, userId, permission);
    }


	@GetMapping("/users/{userId}/associations")
    public ApiResponse<List<Integer>> getUserAssociation(@PathVariable(value = "userId") int userId) {
        return amSvc.getUserAssociation(userId);
    }


    @GetMapping("/users/{userId}/associations/{associationId}/departments")
    public ApiResponse<List<Department>> getUserDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return amSvc.getUserDepartment(associationId, userId);
    }


    @DeleteMapping("/associations/{associationId}/members/{userId}")
    public ApiResponse<Boolean> deleteOneInAssociation(@RequestAttribute("id") int operatorId,@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_UPDATE)){
			return new ApiResponse<Boolean>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to modify association["+String.valueOf(associationId)+"]");
		}
        return amSvc.deleteOneInAssociation(associationId, userId);
    }


    @PostMapping("/{associationId}/members")
    public ApiResponse<Boolean> addOneToAssociation(@RequestAttribute("id") int operatorId,@PathVariable(value = "associationId") int associationId, @RequestParam(value = "userId") int userId) {
		if(!checkPermissionOnAssociationById(operatorId,associationId,PERM_UPDATE)){
			return new ApiResponse<Boolean>("PermErr","Operator["+String.valueOf(operatorId)+"] does not have permission to modify association["+String.valueOf(associationId)+"]");
		}
        return amSvc.addOneToAssociation(associationId, userId);
    }

    @GetMapping("/{associationId}/members")
    public ApiResponse<List<Integer>> getAssUserList(@PathVariable(value = "associationId") int associationId) {

        return amSvc.getAssoUserList(associationId);
    }

    //@PostMapping("/")
    //public ApiResponse<Boolean> initDepartment(@RequestBody InitAssociationInfo initAssociationInfo) {

    //    return amSvc.initDepartment(initAssociationInfo);
    //}

	private boolean checkPermissionOnAssociationById(int operatorId,int associationId,String perm){
		final String permScope = "asso-mem" + String.valueOf(associationId);
		if(permSvc.hasPermission(Long.valueOf(operatorId),permScope,PERM_CREATE) == null){
			return false;
		}
		return true;
	}
}
