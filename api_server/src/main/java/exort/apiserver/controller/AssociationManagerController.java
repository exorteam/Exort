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
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.assomgr.entity.Association;
import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.assomgr.entity.AssociationInfo;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.member.service.AssoMemService;
import exort.api.http.perm.service.PermService;
import exort.apiserver.config.SysAdminInitConfig.SystemAdministratorInfo;
import exort.apiserver.service.AssoMgrSvc;

@RestController
@RequestMapping(path="/associations")
public class AssociationManagerController{
    // Get operations are open
    // Update operations need specific permission
    public static final String PERM_UPDATE = "update-asso";
    // Delete, create operations need system admin

    @Autowired
    private AssoMgrSvc service;
    @Autowired
    private PermService permSvc;
    @Autowired
    private AssoMemService assoMemService;
    @Autowired
    private SystemAdministratorInfo sysAdmin;

	@PostMapping("/batch")
	public ApiResponse<PagedData<Association>> getAssociationsInBatch(
			@RequestBody List<String> ids,
			PageQuery pq){
		return service.getAssociationsInBatch(ids,pq.getPageNum(),pq.getPageSize());
	}

    @PostMapping("/list")
    //public ApiResponse<PagedData<Association>> listAssociations(@RequestParam int state, @RequestParam String keyword,
    //                                                            @RequestParam String tags, @RequestParam int pageNum,
    //                                                            @RequestParam int pageSize){
	public ApiResponse<PagedData<Association>> listAssociations(
			@RequestBody AssociationFilterParams filter,
			PageQuery pq){
        return service.listAssociations(filter,pq);
    }

    //ok
    @GetMapping("/{assoId}")
    public ApiResponse<Association> getAssociation(@PathVariable(value="assoId") String assoId){
        return service.getAssociation(assoId);
    }

    @PostMapping
    public ApiResponse createAssociation(@RequestAttribute(name="id", required=false) Integer operatorId,@RequestBody AssociationInfo body){
        if(permSvc.hasRole(Long.valueOf(operatorId),sysAdmin.SCOPE_NAME,sysAdmin.ROLE_NAME) == null){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have create permission on association");
        }

        Association association = service.createAssociation(body).getData();
        InitAssociationInfo associationInfo = new InitAssociationInfo(operatorId,association.getId());
        assoMemService.initDepartment(associationInfo);

        return new ApiResponse(association);
    }

    @DeleteMapping("/{assoId}")
    public ApiResponse deleteAssociation(@RequestAttribute(name="id", required=false) Integer operatorId,@PathVariable(value="assoId") String assoId ){
        if(permSvc.hasRole(Long.valueOf(operatorId),sysAdmin.SCOPE_NAME,sysAdmin.ROLE_NAME) == null){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have delete permission on association");
        }
        assoMemService.deleteAllDepartments(assoId);
        return service.deleteAssociation(assoId);
    }

    @PutMapping("/{assoId}")
    public ApiResponse editAssociation(@RequestAttribute(name="id", required=false) Integer operatorId,@RequestBody AssociationInfo body, @PathVariable(value="assoId") String assoId ){
        if(!checkPermissionOnAssociationById(operatorId,assoId,PERM_UPDATE)){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have update permission on association["+String.valueOf(assoId)+"]");
        }
        return service.editAssociation(assoId, body);
    }


    @PutMapping("/{assoId}/state")
    public ApiResponse<Object> patchAssociation(@RequestAttribute(name="id", required=false) Integer operatorId, @RequestBody Operation<String> body, @PathVariable(value="assoId") String assoId ){
        //if(!checkPermissionOnAssociationById(operatorId,assoId,PERM_UPDATE)){
        //    throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have update permission on association["+String.valueOf(assoId)+"]");
        //}
        return service.patchAssociation(assoId,body);
    }

    private boolean checkPermissionOnAssociationById(int operatorId,String assoId,String perm){
        final String permScope = assoMemService.scope(assoId);
        if(permSvc.hasPermission(Long.valueOf(operatorId),permScope,perm) == null){
            return false;
        }
        return true;
    }
}
