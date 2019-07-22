package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.service.AssoMemManagerService;
import exort.apiserver.service.AssoMemManagerService.*;

@RestController
public class AssoMemManagerController {
	@Autowired
	private AssoMemManagerService service;

	public ApiResponse<Boolean> adoptApplication(CallbackParam<ApplicationDepartmentInfo> callbackParam){
		return service.adoptApplication(callbackParam);
	}

    public ApiResponse<List<Department>> getDepartmentTree(int associationId){
		return service.getDepartmentTree(associationId);
	}

    public ApiResponse<Department> getSpecDepartment(int associationId, int departmentId){
		return service.getSpecDepartment(associationId,departmentId);
	}

    public ApiResponse<Department> createDepartment(int associationId, Department departmentInfo){
		return service.createDepartment(associationId,departmentInfo);
	}

    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId){
		return service.deleteDepartment(associationId,departmentId);
	}

    public ApiResponse<Department> editDepartment(int associationId, int departmentId, Department departmentInfo){
		return service.editDepartment(associationId,departmentId,departmentInfo);
	}

    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId){
		return service.getSpecMemberList(associationId,departmentId);
	}

    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId){
		return service.removeOneFromDepartment(associationId,departmentId,userId);
	}

    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId){
		return service.addOneToDepartment(associationId,departmentId,userId);
	}

    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission){
		return service.checkUserPermissionInAssociation(associationId,userId,permission);
	}

    public ApiResponse<List<Integer>> getUserAssociation(int userId){
		return service.getUserAssociation(userId);
	}

    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId){
		return service.getUserDepartment(associationId,userId);
	}

    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId){
		return service.deleteOneInAssociation(associationId,userId);
	}

    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId){
		return service.addOneToAssociation(associationId,userId);
	}

    public ApiResponse<List<Integer>> getAssoUserList(int associationId){
		return service.getAssoUserList(associationId);
	}

    public ApiResponse<Boolean> initDepartment(InitAssociationInfo initAssociationInfo){
		return service.initDepartment(initAssociationInfo);
	}
}
