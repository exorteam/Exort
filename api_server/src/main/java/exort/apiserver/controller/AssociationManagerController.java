package exort.apiserver.controller;


import java.util.Arrays;

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
import exort.apiserver.service.AssociationManagerService;


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

    @GetMapping
    public AssociationManagerService.Response<AssociationManagerService.AssociationList> listAssociations(@RequestParam int state,@RequestParam String keyword,
                                                                                                          @RequestParam String tags,@RequestParam int pageNum,
                                                                                                          @RequestParam int pageSize){
        AssociationManagerService.AssociationFilterParams body = new AssociationManagerService.AssociationFilterParams(keyword, Arrays.asList(tags),state);
        return service.listAssociations(body,pageNum,pageSize);
    }

    //ok
    @GetMapping("/{assoId}")
    public AssociationManagerService.Response<AssociationManagerService.Association> getAssociation(@PathVariable(value="assoId") String assoId){
        return service.getAssociation(assoId);
    }

    @PostMapping
    public AssociationManagerService.Response createAssociation(@RequestAttribute("id") int operatorId,@RequestBody AssociationManagerService.AssociationInfo body){
		if(permSvc.hasRole(Long.valueOf(operatorId),SystemAdminConstants.SCOPE_NAME,SystemAdminConstants.ROLE_NAME) == null){
			return new AssociationManagerService.Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have create permission on association");
		}
		return service.createAssociation(body);
    }

    @DeleteMapping("/{assoId}")
    public AssociationManagerService.Response deleteAssociation(@RequestAttribute("id") int operatorId,@PathVariable(value="assoId") String assoId ){
		if(permSvc.hasRole(Long.valueOf(operatorId),SystemAdminConstants.SCOPE_NAME,SystemAdminConstants.ROLE_NAME) == null){
			return new AssociationManagerService.Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have delete permission on association");
		}
        return service.deleteAssociation(assoId);
    }

    @PutMapping("/{assoId}")
    public AssociationManagerService.Response editAssociation(@RequestAttribute("id") int operatorId,@RequestBody AssociationManagerService.AssociationInfo body, @PathVariable(value="assoId") String assoId ){
		if(!checkPermissionOnAssociationById(operatorId,assoId,PERM_UPDATE)){
			return new AssociationManagerService.Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have update permission on association["+String.valueOf(assoId)+"]");
		}
        return service.editAssociation(assoId, body);
    }


    @PutMapping("/{assoId}/state")
    public AssociationManagerService.Response<Object> patchAssociation(@RequestAttribute("id") int operatorId,@RequestBody AssociationManagerService.PatchAssociationInfo body, @PathVariable(value="assoId") String assoId ){
		if(!checkPermissionOnAssociationById(operatorId,assoId,PERM_UPDATE)){
			return new AssociationManagerService.Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have update permission on association["+String.valueOf(assoId)+"]");
		}
        return service.patchAssociation(assoId,body);
    }

	private boolean checkPermissionOnAssociationById(int operatorId,String assoId,String perm){
		final String permScope = "asso-" + assoId;
		if(permSvc.hasPermission(Long.valueOf(operatorId),permScope,perm) == null){
			return false;
		}
		return true;
	}
}
