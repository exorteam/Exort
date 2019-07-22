package exort.api.http.perm.service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.entity.Role;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface PermService {

    /* Get methods */

    // [user] => [scopes]
    ApiResponse<List<String>> getScopes(@NotNull Long userId);

    // [scopes] (pagination)
    ApiResponse<PagedData<String>> getScopes();
    ApiResponse<PagedData<String>> getScopes(Integer pageSize);
    ApiResponse<PagedData<String>> getScopes(Integer pageNum, Integer pageSize);

    // [scope] => [users] (pagination)
    ApiResponse<PagedData<Long>> getUsers(@NotNull String scope);
    ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, Integer pageSize);
    ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, Integer pageNum, Integer pageSize);

    // [scope, role] => [users] (pagination)
    ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, @NotNull String roleName);
    ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, @NotNull String roleName, Integer pageSize);
    ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, @NotNull String roleName, Integer pageNum, Integer pageSize);

    // [user, role] => [roles]
    ApiResponse<List<Role>> getRoles(@NotNull Long userId, @NotNull String scope);

    // [role] => [permissions]
    ApiResponse<List<Permission>> getPermissions(@NotNull String roleName);

    // [permissions]
    ApiResponse<List<Permission>> getPermissions();

    // [user, scope, role] => [{} / null]
    ApiResponse hasRole(@NotNull Long userId, @NotNull String scope, @NotNull String roleName);
    // [user, scope, permission] => [{} / null]
    ApiResponse hasPermission(@NotNull Long userId, @NotNull String scope, @NotNull String permissionName);

    // [role]
    ApiResponse<Role> getRole(@NotNull String name);

    // [permission]
    ApiResponse<Permission> getPermission(@NotNull String name);

    /* Modify methods */

    // create [role]
    ApiResponse<Role> createRole(@NotNull Role roleArg);
    // delete [role]
    ApiResponse deleteRole(@NotNull String name);
    // update [role]
    ApiResponse<Role> updateRole(@NotNull Role roleArg);

    // create [permission]
    ApiResponse<Permission> createPermission(@NotNull Permission permArg);
    // delete [permission]
    ApiResponse deletePermission(@NotNull String name);
    // update [permission]
    ApiResponse<Permission> updatePermission(@NotNull Permission permArg);


    // + [user, scope] <- [roles]
    ApiResponse<List<Role>> grantRoles(@NotNull Long userId, @NotNull String scope, List<String> roleNames);
    // - [user, scope] <- [roles]
    ApiResponse<List<Role>> revokeRoles(@NotNull Long userId, @NotNull String scope, List<String> roleNames);

    // + [role] <- [permissions]
    ApiResponse<List<Permission>> grantPermissions(@NotNull String roleName, List<String> permissionNames);
    // - [role] <- [permissions]
    ApiResponse<List<Permission>> revokePermissions(@NotNull String roleName, List<String> permissionNames);

}
