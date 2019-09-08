package exort.apiserver.controller;

import java.util.ArrayList;
import java.util.List;

import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Role;
import exort.apiserver.entity.GetUsersRequest;
import exort.apiserver.entity.UserAdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.service.PermService;
import exort.apiserver.config.SysAdminInitConfig.SystemAdministratorInfo;
import exort.apiserver.service.UserInfoService;
import exort.apiserver.service.UserInfoService.UserInfo;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(path="/users")
public class UserInfoController {

	@Autowired
	private UserInfoService infoSvc;
	@Autowired
	private PermService permSvc;
	@Autowired
	private SystemAdministratorInfo sysAdmin;

	@GetMapping("/{id}")
	public ApiResponse<UserInfo> getUserInfoById(@RequestAttribute(name="id", required=false) Integer operatorId,@PathVariable("id") int userId){
		log.info("Operator("+String.valueOf(operatorId)+") get user info of user("+String.valueOf(userId)+").");
		return infoSvc.getUserInfo(userId);
	}

	@GetMapping("/self")
	public ApiResponse<UserInfo> getCurrentUserInfo(@RequestAttribute(name="id", required=false) Integer operatorId){
		log.info("Operator("+String.valueOf(operatorId)+") get current user info.");
		return infoSvc.getUserInfo(operatorId);
	}

	@PostMapping("/{id}")
	public ApiResponse<UserInfo> updateUserInfoById(@RequestAttribute(name="id", required=false) Integer operatorId,@PathVariable("id") int userId,@RequestBody UserInfo info){
		log.info("Operator("+String.valueOf(operatorId)+") update user info of user("+String.valueOf(userId)+").");
		if(operatorId != userId){
			throw new ApiError(403,"PermErr","Updating operation from another user should be rejected");
		}
		return infoSvc.updateUserInfo(userId,info);
	}

	@PatchMapping("/{id}")
	public ApiResponse<Boolean> disableUserById(@RequestAttribute(name="id", required=false) Integer operatorId,@PathVariable("id") int userId,@RequestParam boolean disabled){
		log.info("Operator("+String.valueOf(operatorId)+") toggle disability for user("+String.valueOf(userId)+").");
		if(permSvc.hasRole(Long.valueOf(operatorId),sysAdmin.SCOPE_NAME,sysAdmin.ROLE_NAME).getData() == null){
			throw new ApiError(403,"PermErr","Disabling by non-admin user should be rejected");
		}
		return infoSvc.disableUser(userId,disabled);
	}

	@PostMapping
	public ApiResponse<List<UserInfo>> getUserInfoInBatch(@RequestBody List<Integer> ids){
		return infoSvc.getUserInfoInBatch(ids);
	}

	@PostMapping("/page")
	public ApiResponse<List<UserInfo>> getUserInfoByPage(@RequestBody PageQuery pageQuery){
		return infoSvc.getUserInfoByPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getSortBy());
	}

    @GetMapping("/{id}/admin")
    public ApiResponse<UserAdminResponse> getUserAdmin(@RequestAttribute(name="id", required=false) Long operatorId, @PathVariable("id") long userId) {
        if (operatorId != userId) {
            throw new ApiError(401, "PermErr", "Only available for user-self");
        }
        UserAdminResponse res = new UserAdminResponse();
        res.setIsSysAdmin(permSvc.hasRole(userId, sysAdmin.SCOPE_NAME, sysAdmin.ROLE_NAME).getData() != null);
        List<String> scopes = permSvc.getScopes(userId).getData();
        List<String> assoAdmins = new ArrayList<>();
        if (scopes != null) {
            for (String scope: scopes) {
                // TODO: String id = getAssociationIdFromScope(scope);
                String prefix = "association_";
                if (scope.startsWith(prefix)) {
                    List roles = permSvc.getRoles(userId, scope).getData();
                    if (roles != null && roles.size() > 1) {
                        assoAdmins.add(scope.substring(prefix.length()));
                    }
                }
            }
        }
        res.setAssoAdmins(assoAdmins);
        return new ApiResponse<>(res);
    }

    @GetMapping
    public ApiResponse<PagedData<UserInfo>> getUserInfoByScopeAndRole(GetUsersRequest req) {
        PageQuery pq = PageQuery.relocate(new PageQuery(req.getPageNum(), req.getPageSize()), 20, 100);
        String scope = req.getScope() == null ? "sys" : req.getScope();
        PagedData<Long> res = req.getRole() == null
                ? permSvc.getUsers(scope, pq).getData()
                : permSvc.getUsers(scope, req.getRole(), pq).getData();
        System.out.println(res);
        List<Integer> uids = new ArrayList<>();
        for (Long i: res.getContent()) {
            uids.add(i.intValue());
        }
        ApiResponse<List<UserInfo>> users = infoSvc.getUserInfoInBatch(uids);
        if (users.getData() != null) {
            System.out.println(users);
            return new ApiResponse<>(new PagedData<>(res.getPageNum(), res.getPageSize(), res.getTotalSize(), users.getData()));
        } else {
            return (ApiResponse)users;
        }
    }

    @PostMapping("/{id}/grant/{scope}")
    public ApiResponse<List<Role>> grantRolesToUser(@PathVariable("id") Long id, @PathVariable("scope") String scope, @RequestBody List<String> roles) {
        return permSvc.grantRoles(id, scope, roles);
    }

    @PostMapping("/{id}/revoke/{scope}")
    public ApiResponse<List<Role>> revokeRolesToUser(@PathVariable("id") Long id, @PathVariable("scope") String scope, @RequestBody List<String> roles) {
        return permSvc.revokeRoles(id, scope, roles);
    }

    @GetMapping("/{id}/roles/{scope}")
    public ApiResponse<List<Role>> getUserRoles(@PathVariable("id") Long id, @PathVariable("scope") String scope) {
        return permSvc.getRoles(id, scope);
    }
}
