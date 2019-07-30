package exort.apiserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.service.AssoMemManagerService;
import exort.apiserver.service.AssoMemManagerService.*;

@RestController
@RequestMapping(path="/associations")
public class AssoMemManagerController {
    @Autowired
	private AssoMemManagerService service;

	@PostMapping("/accept")
    public ApiResponse adoptApplication(@RequestBody CallbackParam<ApplicationDepartmentInfo> appli) {
        return service.adoptApplication(appli);
    }

	@GetMapping("/{id}/departments")
    public ApiResponse<List<Department>> getDepartmentTree(@PathVariable(value = "id") int associationId) {
        return service.getDepartmentTree(associationId);
    }


	@GetMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> getSpecDepartmentInfo(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return service.getSpecDepartmentInfo(associationId, departmentId);
    }


	@PostMapping("/{associationId}/departments")
    public ApiResponse<Department> createDepartment(@PathVariable int associationId, @RequestBody Department departmentInfo) {
        return service.createDepartment(associationId, departmentInfo);
    }


	@DeleteMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> deleteDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return service.deleteDepartment(associationId, departmentId);
    }


	@PutMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<Department> editDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody Department departmentInfo) {

        return service.editDepartment(associationId, departmentId,departmentInfo);
    }


	@GetMapping("/{associationId}/departments/{departmentId}/members")
    public ApiResponse<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId) {
        return service.getSpecMemberList(associationId, departmentId);
    }


	@DeleteMapping("/{associationId}/departments/{departmentId}/members/{userId}")
    public ApiResponse<Boolean> removeOneFromDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId) {
        return service.removeOneFromDepartment(associationId, departmentId, userId);
    }


	@PostMapping("/{associationId}/departments/{departmentId}/members")
    public ApiResponse<Boolean> addOneToDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "departmentId") int departmentId, @RequestParam(value = "userId") int userId) {
        return service.addOneToDepartment(associationId, departmentId, userId);
    }


	@GetMapping("/{associationId}/members/{userId}/permissions/{permission}")
    public ApiResponse<Boolean> checkUserPermissionInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId, @PathVariable(value = "permission") String permission) {
        return service.checkUserPermissionInAssociation(associationId, userId, permission);
    }


	@GetMapping("/users/{userId}/associations")
    public ApiResponse<List<Integer>> getUserAssociation(@PathVariable(value = "userId") int userId) {
        return service.getUserAssociation(userId);
    }


    @GetMapping("/users/{userId}/associations/{associationId}/departments")
    public ApiResponse<List<Department>> getUserDepartment(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return service.getUserDepartment(associationId, userId);
    }


    @DeleteMapping("/associations/{associationId}/members/{userId}")
    public ApiResponse<Boolean> deleteOneInAssociation(@PathVariable(value = "associationId") int associationId, @PathVariable(value = "userId") int userId) {
        return service.deleteOneInAssociation(associationId, userId);
    }


    @PostMapping("/{associationId}/members")
    public ApiResponse<Boolean> addOneToAssociation(@PathVariable(value = "associationId") int associationId, @RequestParam(value = "userId") int userId) {
        return service.addOneToAssociation(associationId, userId);
    }

    @GetMapping("/{associationId}/members")
    public ApiResponse<List<Integer>> getAssUserList(@PathVariable(value = "associationId") int associationId) {

        return service.getAssoUserList(associationId);
    }

    @PostMapping("/")
    public ApiResponse<Boolean> initDepartment(@RequestBody InitAssociationInfo initAssociationInfo) {

        return service.initDepartment(initAssociationInfo);
    }

}
