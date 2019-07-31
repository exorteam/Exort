
package exort.apiserver.controller;


import java.util.Arrays;

import exort.api.http.assomgr.entity.Association;
import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.assomgr.entity.AssociationInfo;
import exort.api.http.assomgr.service.AssociationManagerService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.member.service.AssoMemService;

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
import exort.apiserver.entity.SystemAdminConstants;


@RestController
@RequestMapping(path="/associations")
public class AssociationManagerController{
    // Get operations are open
    // Update operations need specific permission
    public static final String PERM_UPDATE = "update-asso";
    // Delete, create operations need system admin

    @Autowired
    private AssociationManagerService service;
    @Autowired
    private PermService permSvc;
    @Autowired
    private AssoMemService amSvc;

    @GetMapping
    public ApiResponse<PagedData<Association>> listAssociations(@RequestParam int state, @RequestParam String keyword,
                                                                @RequestParam String tags, @RequestParam int pageNum,
                                                                @RequestParam int pageSize){
        AssociationFilterParams body = new AssociationFilterParams();
        body.setKeyword(keyword);
        body.setState(state);
        body.setTags(Arrays.asList(tags));
        PageQuery pageQuery = new PageQuery(pageNum,pageSize);
        return service.listAssociations(body,pageQuery);
    }

    //ok
    @GetMapping("/{assoId}")
    public ApiResponse<Association> getAssociation(@PathVariable(value="assoId") String assoId){
        return service.getAssociation(assoId);
    }

    @PostMapping
    public ApiResponse createAssociation(@RequestAttribute("id") int operatorId,@RequestBody AssociationInfo body){
        if(permSvc.hasRole(Long.valueOf(operatorId),SystemAdminConstants.SCOPE_NAME,SystemAdminConstants.ROLE_NAME) == null){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have create permission on association");
        }
        return service.createAssociation(body);
    }

    @DeleteMapping("/{assoId}")
    public ApiResponse deleteAssociation(@RequestAttribute("id") int operatorId,@PathVariable(value="assoId") String assoId ){
        if(permSvc.hasRole(Long.valueOf(operatorId),SystemAdminConstants.SCOPE_NAME,SystemAdminConstants.ROLE_NAME) == null){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have delete permission on association");
        }
        return service.deleteAssociation(assoId);
    }

    @PutMapping("/{assoId}")
    public ApiResponse editAssociation(@RequestAttribute("id") int operatorId,@RequestBody AssociationInfo body, @PathVariable(value="assoId") String assoId ){
        if(!checkPermissionOnAssociationById(operatorId,assoId,PERM_UPDATE)){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have update permission on association["+String.valueOf(assoId)+"]");
        }
        return service.editAssociation(assoId, body);
    }


    @PutMapping("/{assoId}/state")
    public ApiResponse<Object> patchAssociation(@RequestAttribute("id") int operatorId, @RequestBody Operation<String> body, @PathVariable(value="assoId") String assoId ){
        if(!checkPermissionOnAssociationById(operatorId,assoId,PERM_UPDATE)){
            throw new ApiError(400,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have update permission on association["+String.valueOf(assoId)+"]");
        }
        return service.patchAssociation(assoId,body);
    }

    private boolean checkPermissionOnAssociationById(int operatorId,String assoId,String perm){
        final String permScope = amSvc.scope(assoId);
        if(permSvc.hasPermission(Long.valueOf(operatorId),permScope,perm) == null){
            return false;
        }
        return true;
    }
}


