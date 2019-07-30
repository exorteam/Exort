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

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.apiserver.entity.SystemAdminConstants;

@RestController
@RequestMapping(path="/permission")
public class PermController {

	@Autowired
	private PermService service;

	@GetMapping("/admin")
	public ApiResponse checkIfIsAdministrator(@RequestAttribute("id") Long id){
		return service.hasRole(id,SystemAdminConstants.SCOPE_NAME,SystemAdminConstants.ROLE_NAME);
	}

	@PostMapping
	public ApiResponse createRole(@RequestAttribute("id") Long id,@RequestBody Role roleArg){
		if(checkIfIsAdministrator(id).getData() == null){
			return new ApiResponse<Object>("PermErr","Operator["+String.valueOf(id)+"] is not system administrator");
		}
		return service.createRole(roleArg);
	}

	@PutMapping
	public ApiResponse updateRole(@RequestAttribute("id") Long id,@RequestBody Role roleArg){
		if(checkIfIsAdministrator(id).getData() == null){
			return new ApiResponse<Object>("PermErr","Operator["+String.valueOf(id)+"] is not system administrator");
		}
		return service.updateRole(roleArg);
	}

	@DeleteMapping("/{name}")
	public ApiResponse deleteRole(@RequestAttribute("id") Long id,@PathVariable("name") String roleName){
		if(checkIfIsAdministrator(id).getData() == null){
			return new ApiResponse<Object>("PermErr","Operator["+String.valueOf(id)+"] is not system administrator");
		}
		return service.deleteRole(roleName);
	}

	@PostMapping("/grant/{name}")
	public ApiResponse grantPermissionsOnRole(@RequestAttribute("id") Long id,@PathVariable("name") String roleName,@RequestBody List<String> permissions){
		if(checkIfIsAdministrator(id).getData() == null){
			return new ApiResponse<Object>("PermErr","Operator["+String.valueOf(id)+"] is not system administrator");
		}
		return service.grantPermissions(roleName,permissions);
	}

	@PostMapping("/revoke/{name}")
	public ApiResponse revokePermissionsOnRole(@RequestAttribute("id") Long id,@PathVariable("name") String roleName,@RequestBody List<String> permissions){
		if(checkIfIsAdministrator(id).getData() == null){
			return new ApiResponse<Object>("PermErr","Operator["+String.valueOf(id)+"] is not system administrator");
		}
		return service.revokePermissions(roleName,permissions);
	}
	
}
