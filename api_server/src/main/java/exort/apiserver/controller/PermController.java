package exort.apiserver.controller;

import java.util.List;

import exort.api.http.perm.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.apiserver.config.SysAdminInitConfig.SystemAdministratorInfo;

@RestController
@RequestMapping(path="/permission")
public class PermController {

	@Autowired
	private PermService service;
	@Autowired
	private SystemAdministratorInfo sysAdmin;

	@GetMapping("/list_perm")
	public ApiResponse listPermission(@RequestAttribute("id") Long id) {
		return service.getPermissions();
	}

	@GetMapping("/list_perm/{roleName}")
	public ApiResponse listPermission(@RequestAttribute("id") Long id, @PathVariable("roleName") String roleName) {
		return service.getPermissions(roleName);
	}

	@GetMapping("/list_role")
	public ApiResponse listRole(@RequestAttribute("id") Long id,
								@RequestParam(value = "category", required = false, defaultValue = "") String category) {
		return service.getRolesByCategory(category);
	}

	@PostMapping("/create_perm")
	public ApiResponse createPermission(@RequestAttribute("id") Long id, @RequestBody Permission permArg){
		return service.createPermission(permArg);
	}

	@PostMapping("/update_perm")
	public ApiResponse updatePermission(@RequestAttribute("id") Long id, @RequestBody Permission permArg) {
		return service.updatePermission(permArg);
	}

	@PostMapping("/delete_perm")
	public ApiResponse deletePermission(@RequestAttribute("id") Long id, @RequestBody Permission permArg) {
		return service.deletePermission(permArg.getName());
	}

	@PostMapping("/create_role")
	public ApiResponse createRole(@RequestAttribute("id") Long id, @RequestBody Role roleArg){
		return service.createRole(roleArg);
	}

	@PostMapping("/update_role")
	public ApiResponse updateRole(@RequestAttribute("id") Long id, @RequestBody Role roleArg) {
		System.out.println(roleArg);
		ApiResponse r = service.updateRole(roleArg);
		System.out.println(r);
		return r;
	}

	@PostMapping("/delete_role")
	public ApiResponse deleteRole(@RequestAttribute("id") Long id, @RequestBody Role roleArg) {
		return service.deleteRole(roleArg.getName());
	}

	@PostMapping("/grant/{name}")
	public ApiResponse grantPermissionsOnRole(
			@RequestAttribute("id") Long id,
			@PathVariable("name") String roleName,
			@RequestBody List<String> permissions) {
		return service.grantPermissions(roleName,permissions);
	}

	@PostMapping("/revoke/{name}")
	public ApiResponse revokePermissionsOnRole(
			@RequestAttribute("id") Long id,
			@PathVariable("name") String roleName,
			@RequestBody List<String> permissions) {
		return service.revokePermissions(roleName,permissions);
	}

}
