package exort.apiserver.controller;

import java.util.ArrayList;
import java.util.List;

import exort.api.http.assomgr.service.AssociationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.assomgr.entity.Association;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.UserId;
import exort.api.http.member.service.AssoMemService;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.service.PermService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path="/associations")
public class AssoMemManagerController {
    // adopt and init should not be called by api server
    // get operations are open
    // update, create, delete operations need specific permission
    public static final String PERM_CREATE = "create_department";
    public static final String PERM_UPDATE = "update_department";
    public static final String PERM_DELETE = "delete_department";

    @Autowired
    private AssoMemService amSvc;
    @Autowired
    private PermService permSvc;
    @Autowired
    private AssociationManagerService assoService;

	@PostMapping("/accept")
    public ApiResponse adoptApplication(@RequestBody CallbackParam<ApplicationDepartmentInfo> appli) {
        return service.adoptApplication(appli);
    }

    @GetMapping("/{id}/departments")
    public ApiResponse<List<DepartmentInfo>> getDepartmentTree(@PathVariable(value = "id") String associationId) {
        return amSvc.getDepartmentTree(associationId);
    }


    @GetMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<DepartmentInfo> getSpecDepartmentInfo(@PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId) {
        return amSvc.getSpecDepartmentInfo(associationId, departmentId);
    }


    @PostMapping("/{associationId}/departments")
    public ApiResponse<DepartmentInfo> createDepartment(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable String associationId, @RequestBody DepartmentInfo departmentInfo) {
//        log.info("[createDepartment] Operator: " + operatorId);
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_CREATE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to create department on association[" + associationId + "]");
        }
        return amSvc.createDepartment(associationId, departmentInfo);
    }


    @DeleteMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<DepartmentInfo> deleteDepartment(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId) {
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_DELETE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to delete department[" + departmentId + "] on association[" + associationId + "]");
        }
        return amSvc.deleteDepartment(associationId, departmentId);
    }


    @PutMapping("/{associationId}/departments/{departmentId}")
    public ApiResponse<DepartmentInfo> editDepartment(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody DepartmentInfo departmentInfo) {
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_UPDATE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to delete department[" + departmentId + "] on association[" + associationId + "]");
        }
        return amSvc.editDepartment(associationId, departmentId, departmentInfo);
    }


    @GetMapping("/{associationId}/departments/{departmentId}/members")
    public ApiResponse<List<Integer>> getSpecMemberList(@PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId) {
        return amSvc.getSpecMemberList(associationId, departmentId);
    }


    @DeleteMapping("/{associationId}/departments/{departmentId}/members/{userId}")
    public ApiResponse<Boolean> removeOneFromDepartment(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "userId") int userId) {
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_UPDATE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to modify association[" + associationId + "]");
        }
        return amSvc.removeOneFromDepartment(associationId, departmentId, userId);
    }


    @PostMapping("/{associationId}/departments/{departmentId}/members")
    public ApiResponse<Boolean> addOneToDepartment(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody UserId userId) {
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_UPDATE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to modify association[" + associationId + "]");
        }
        return amSvc.addOneToDepartment(associationId, departmentId, userId);
    }

    @GetMapping("/users/{userId}/associations")
    public ApiResponse<List<Association>> getUserAssociation(@PathVariable(value = "userId") int userId) {
        List<String> userIds = amSvc.getUserAssociation(userId).getData();
        List<Association> associations = new ArrayList<>();

        for (String id : userIds) {
            associations.add(assoService.getAssociation(id).getData());
        }

        return new ApiResponse<>(associations);
    }


    @GetMapping("/users/{userId}/associations/{associationId}/departments")
    public ApiResponse<List<DepartmentInfo>> getUserDepartment(@PathVariable(value = "associationId") String associationId, @PathVariable(value = "userId") int userId) {
        return amSvc.getUserDepartment(associationId, userId);
    }


    @DeleteMapping("/associations/{associationId}/members/{userId}")
    public ApiResponse<Boolean> deleteOneInAssociation(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable(value = "associationId") String associationId, @PathVariable(value = "userId") int userId) {
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_UPDATE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to modify association[" + associationId + "]");
        }
        return amSvc.deleteOneInAssociation(associationId, userId);
    }


    @PostMapping("/{associationId}/members")
    public ApiResponse<Boolean> addOneToAssociation(@RequestAttribute(name="id", required=false) Integer operatorId, @PathVariable(value = "associationId") String associationId, @RequestBody UserId userId) {
        if (!checkPermissionOnAssociationById(operatorId, associationId, PERM_UPDATE)) {
            return new ApiResponse<>("PermErr", "Operator[" + operatorId + "] does not have permission to modify association[" + associationId + "]");
        }
        return amSvc.addOneToAssociation(associationId, userId);
    }

    @GetMapping("/{associationId}/members")
    public ApiResponse<List<Integer>> getAssUserList(@PathVariable(value = "associationId") String associationId) {

        return service.getAssoUserList(associationId);
    }

    //@PostMapping("/")
    //public ApiResponse<Boolean> initDepartment(@RequestBody InitAssociationInfo initAssociationInfo) {
    //    return amSvc.initDepartment(initAssociationInfo);
    //}

    private boolean checkPermissionOnAssociationById(int operatorId, String associationId, String perm) {
//        log.info("[checkPerm] operator: " + operatorId + ", scope: " + amSvc.scope(associationId) + ", perm: " + perm);
        return permSvc.hasPermission((long) operatorId, amSvc.scope(associationId), perm).getData() != null;
    }

    @GetMapping("/{associationId}/departments/{departmentId}/permissions")
    public ApiResponse<List<Permission>> getPermList(@PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId) {
        return permSvc.getPermissions(amSvc.roleName(associationId, departmentId));
    }

    @PostMapping("/{associationId}/departments/{departmentId}/permissions")
    public ApiResponse<List<Permission>> grantPermList(@PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody List<String> permissionList) {
        return permSvc.grantPermissions(amSvc.roleName(associationId, departmentId), permissionList);
    }

    @DeleteMapping("/{associationId}/departments/{departmentId}/permissions")
    public ApiResponse<List<Permission>> deletePerm(@PathVariable(value = "associationId") String associationId, @PathVariable(value = "departmentId") int departmentId, @RequestBody List<String> permissionList) {
        return permSvc.revokePermissions(amSvc.roleName(associationId, departmentId), permissionList);
    }

    @DeleteMapping("/{associationId}/departments")
    public ApiResponse<Boolean> deletePerm(@PathVariable(value = "associationId") String associationId) {
        return amSvc.deleteAllDepartments(associationId);
    }

}
