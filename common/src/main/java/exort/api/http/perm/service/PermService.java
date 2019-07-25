package exort.api.http.perm.service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.entity.Role;
import java.util.List;

public interface PermService {

    // Root of request url, for example "http://host:port"
    String root();

    // root + path, for example "http://host:port/resources/{resourceId}"
    String url(String path);

    // root + path + pagination, for example "http://host:port/resources/{resourceId}?pageNum=1&pageSize=20&sortBy=name"
    String url(String path, PageQuery pageQuery);

    /* Get methods */

    // [user] => [scopes]
    ApiResponse<List<String>> getScopes(Long userId);

    // [scopes] (pagination)
    ApiResponse<PagedData<String>> getScopes();
    ApiResponse<PagedData<String>> getScopes(PageQuery pageQuery);

    // [scope] => [users] (pagination)
    ApiResponse<PagedData<Long>> getUsers(String scope);
    ApiResponse<PagedData<Long>> getUsers(String scope, PageQuery pageQuery);

    // [scope, role] => [users] (pagination)
    ApiResponse<PagedData<Long>> getUsers(String scope, String roleName);
    ApiResponse<PagedData<Long>> getUsers(String scope, String roleName, PageQuery pageQuery);

    // [user, role] => [roles]
    ApiResponse<List<Role>> getRoles(Long userId, String scope);

    // [role] => [permissions]
    ApiResponse<List<Permission>> getPermissions(String roleName);

    // [permissions]
    ApiResponse<List<Permission>> getPermissions();
    ApiResponse<List<Permission>> getPermissionsByCategory(String category);

    // [roles]
    ApiResponse<List<Role>> getRolesByCategory(String category);

    // [role]
    ApiResponse<Role> getRole(String name);

    // [permission]
    ApiResponse<Permission> getPermission(String name);

    // [user, scope, role] => [{} / null]
    ApiResponse hasRole(Long userId, String scope, String roleName);
    // [user, scope, permission] => [{} / null]
    ApiResponse hasPermission(Long userId, String scope, String permissionName);

    /* Modify methods */

    // create [role]
    ApiResponse<Role> createRole(Role roleArg);
    // delete [role]
    ApiResponse deleteRole(String name);
    // update [role]
    ApiResponse<Role> updateRole(Role roleArg);

    // create [permission]
    ApiResponse<Permission> createPermission(Permission permArg);
    // delete [permission]
    ApiResponse deletePermission(String name);
    // update [permission]
    ApiResponse<Permission> updatePermission(Permission permArg);


    // + [user, scope] <- [roles]
    ApiResponse<List<Role>> grantRoles(Long userId, String scope, List<String> roleNames);
    // - [user, scope] <- [roles]
    ApiResponse<List<Role>> revokeRoles(Long userId, String scope, List<String> roleNames);

    // + [role] <- [permissions]
    ApiResponse<List<Permission>> grantPermissions(String roleName, List<String> permissionNames);
    // - [role] <- [permissions]
    ApiResponse<List<Permission>> revokePermissions(String roleName, List<String> permissionNames);

    // - [user, scope]
    ApiResponse removeUser(Long userId, String scope);
    // - user
    ApiResponse removeUser(Long userId);
}
