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

import exort.api.http.perm.service.PermService;
import exort.apiserver.entity.SystemAdminConstants;
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

    @GetMapping("/{id}/admin")
    public UserAdminResponse getUserAdmin(@RequestAttribute("id") long operatorId, @PathVariable("id") long userId) {
        if (operatorId != userId) {
            return null;
        }
        UserAdminResponse res = new UserAdminResponse();
        res.setIsSysAdmin(permSvc.hasRole(userId, SystemAdminConstants.SCOPE_NAME, SystemAdminConstants.ROLE_NAME).getData() != null);
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
        return res;
    }

    @GetMapping("/{id}")
    public UserInfo getUserInfoById(@RequestAttribute("id") int operatorId,@PathVariable("id") int userId){
        log.info("Operator("+String.valueOf(operatorId)+") get user info of user("+String.valueOf(userId)+").");
        return infoSvc.getUserInfo(userId);
    }

    @GetMapping("/self")
    public UserInfo getCurrentUserInfo(@RequestAttribute("id") int operatorId){
        log.info("Operator("+String.valueOf(operatorId)+") get current user info.");
        return infoSvc.getUserInfo(operatorId);
    }

    @PostMapping("/{id}")
    public UserInfo updateUserInfoById(@RequestAttribute("id") int operatorId,@PathVariable("id") int userId,@RequestBody UserInfo info){
        log.info("Operator("+String.valueOf(operatorId)+") update user info of user("+String.valueOf(userId)+").");
        if(operatorId != userId){
            log.warn("Updating operation from another user should be rejected");
            return null;
        }
        return infoSvc.updateUserInfo(userId,info);
    }

    @PutMapping("/{id}/state")
    public boolean disableUserById(@RequestAttribute("id") int operatorId,@PathVariable("id") int userId,@RequestParam boolean disabled){
        return infoSvc.disableUser(userId,disabled);
    }

    @PostMapping
    public List getUserInfoInBatch(@RequestBody List<Integer> ids){
        return infoSvc.getUserInfoInBatch(ids);
    }

    @GetMapping("/page")
    public List getUserInfoByPage(@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam String sortBy){
        return infoSvc.getUserInfoByPage(pageNum,pageSize,sortBy);
    }

    @GetMapping
    public PagedData<UserInfo> getUserInfoByScopeAndRole(GetUsersRequest req) {
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
        List<UserInfo> users = infoSvc.getUserInfoInBatch(uids);
        System.out.println(users);
        return new PagedData<>(res.getPageNum(), res.getPageSize(), res.getTotalSize(), users);
    }

    @PostMapping("/{id}/grant/{scope}")
    public List<Role> grantRolesToUser(@PathVariable("id") Long id, @PathVariable("scope") String scope, @RequestBody List<String> roles) {
        return permSvc.grantRoles(id, scope, roles).getData();
    }

    @PostMapping("/{id}/revoke/{scope}")
    public List<Role> revokeRolesToUser(@PathVariable("id") Long id, @PathVariable("scope") String scope, @RequestBody List<String> roles) {
        return permSvc.revokeRoles(id, scope, roles).getData();
    }

    @GetMapping("/{id}/roles/{scope}")
    public List<Role> getUserRoles(@PathVariable("id") Long id, @PathVariable("scope") String scope) {
        return permSvc.getRoles(id, scope).getData();
    }
}
