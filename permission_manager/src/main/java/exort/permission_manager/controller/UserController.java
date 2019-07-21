package exort.permission_manager.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.OperationBatch;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Role;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/users/{userId}/scopes")
    public ApiResponse<List<String>> getScopes(
            @PathVariable("userId") Long userId) {
        return new ApiResponse<>(us.listScopes(userId));
    }

    @GetMapping("/users/{userId}/scopes/{scope}/roles")
    public ApiResponse<List<Role>> getRoles(
            @PathVariable("userId") Long userId,
            @PathVariable("scope") String scope) {
        List<ExortRole> roles = us.listRoles(userId, scope);
        List<Role> res = new ArrayList<>();
        for (ExortRole role: roles) {
            res.add(new Role(role.getId(), role.getDescription()));
        }
        return new ApiResponse<>(res);
    }

    @GetMapping("/users/{userId}/scopes/{scope}/roles/{roleName}")
    public ApiResponse getRole(
            @PathVariable("userId") Long userId,
            @PathVariable("scope") String scope,
            @PathVariable("roleName") String roleName) {
        if (us.hasRole(userId, scope, roleName)) {
            return ApiResponse.emptyResponse();
        } else {
            throw new ApiError(404, "roleNotFound", "User doesn't own the role in the scope.");
        }
    }

    @GetMapping("/users/{userId}/scopes/{scope}/permissions/{permissionName}")
    public ApiResponse getPermission(
            @PathVariable("userId") Long userId,
            @PathVariable("scope") String scope,
            @PathVariable("permissionName") String permissionName) {
        if (us.hasPerm(userId, scope, permissionName)) {
            return ApiResponse.emptyResponse();
        } else {
            throw new ApiError(404, "permissionNotFound", "User doesn't own the permission in the scope.");
        }
    }

    @PutMapping("/users/{userId}/scopes/{scope}/roles")
    public ApiResponse<List<Role>> updateRoles(
            @PathVariable("userId") Long userId,
            @PathVariable("scope") String scope,
            @RequestBody OperationBatch<String> ops) {
        if (ops.getOperation() == null) {
            ops.setOperation("add");
        }
        List<ExortRole> roles;
        if (ops.getOperation().toLowerCase().equals("add")) {
            roles = us.grantRoles(userId, scope, ops.getArgs());
        } else if (ops.getOperation().toLowerCase().equals("remove")) {
            roles = us.revokeRoles(userId, scope, ops.getArgs());
        } else {
            throw new ApiError(400, "invalidOperation", "Invalid operation, should be \"add\" or \"remove\".");
        }
        List<Role> res = new ArrayList<>();
        for (ExortRole role: roles) {
            res.add(new Role(role.getId(), role.getDescription()));
        }
        return new ApiResponse<>(res);
    }

    @GetMapping("/scopes/{scope}/roles/{roleName}/users")
    public ApiResponse<PagedData<Long>> getUsers(
            @PathVariable("scope") String scope,
            @PathVariable("roleName") String roleName,
            @RequestBody PageQuery page) {
        page = PageQuery.relocate(page, 50, 200);
        Page<Long> userIds = us.list(scope, roleName, page.getPageNum(), page.getPageSize());
        return new ApiResponse<>(new PagedData<>(
                page.getPageNum(),
                page.getPageSize(),
                userIds.getTotalElements(),
                userIds.getContent()));
    }


    @GetMapping("/scopes/{scope}/users")
    public ApiResponse<PagedData<Long>> getUsers(
            @PathVariable("scope") String scope,
            @RequestBody PageQuery page) {
        page = PageQuery.relocate(page, 50, 200);
        Page<Long> userIds = us.list(scope, page.getPageNum(), page.getPageSize());
        return new ApiResponse<>(new PagedData<>(
                page.getPageNum(),
                page.getPageSize(),
                userIds.getTotalElements(),
                userIds.getContent()));
    }

}
