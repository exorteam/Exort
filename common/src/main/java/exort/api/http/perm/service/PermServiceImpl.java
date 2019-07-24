package exort.api.http.perm.service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.OperationBatch;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermServiceImpl extends RestTemplate implements PermService {

    @Value("${exort.perm.protocol: http}")
    @Getter
    @Setter
    private String protocol;

    @Value("${exort.perm.endpoint: localhost}")
    @Getter
    @Setter
    private String endpoint;

    @Override
    public ApiResponse<List<String>> getScopes(Long userId) {
        return request(HttpMethod.GET, "/users/{userId}/scopes", userId);
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes() {
        return request(HttpMethod.GET, new PageQuery(), "/scopes");
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes(PageQuery pageQuery) {
        return request(HttpMethod.GET, pageQuery, "/scopes");
    }

    @Override
    public ApiResponse<List<Role>> getRoles(Long userId, String scope) {
        return request(HttpMethod.GET, "/users/{userId}/scopes/{scope}/roles",
                userId, scope);
    }

    @Override
    public ApiResponse<List<Role>> grantRoles(Long userId, String scope, List<String> roleNames) {
        return request(new OperationBatch<>("add", roleNames),
                HttpMethod.PUT, "/users/{userId}/scopes/{scope}/roles",
                userId, scope);
    }

    @Override
    public ApiResponse<List<Role>> revokeRoles(Long userId, String scope, List<String> roleNames) {
        return request(new OperationBatch<>("remove", roleNames),
                HttpMethod.PUT, "/users/{userId}/scopes/{scope}/roles",
                userId, scope);
    }

    @Override
    public ApiResponse hasRole(Long userId, String scope, String roleName) {
        return request(HttpMethod.GET, "/users/{userId}/scopes/{scope}/roles/{roleName}",
                userId, scope, roleName);
    }

    @Override
    public ApiResponse hasPermission(Long userId, String scope, String permissionName) {
        return request(HttpMethod.GET, "/users/{userId}/scopes/{scope}/permissions/{permissionName}",
                userId, scope, permissionName);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope) {
        return request(HttpMethod.GET, new PageQuery(), "/scopes/{scope}/users",
                scope);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope, PageQuery pageQuery) {
        return request(HttpMethod.GET, pageQuery, "/scopes/{scope}/users",
                scope);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope, String roleName) {
        return request(HttpMethod.GET, new PageQuery(), "/scopes/{scope}/roles/{roleName}/users",
                scope, roleName);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope, String roleName, PageQuery pageQuery) {
        return request(HttpMethod.GET, pageQuery, "/scopes/{scope}/roles/{roleName}/users",
                scope, roleName);
    }

    @Override
    public ApiResponse<Role> createRole(Role roleArg) {
        return request(roleArg,
                HttpMethod.POST, "/roles");
    }

    @Override
    public ApiResponse deleteRole(String name) {
        return request(HttpMethod.DELETE, "/roles/{name}", name);
    }

    @Override
    public ApiResponse<Role> updateRole(Role roleArg) {
        String name = roleArg.getName();
        roleArg.setName(null);
        return  request(roleArg,
                HttpMethod.PUT, "/roles/{name}", name);
    }

    @Override
    public ApiResponse<Role> getRole(String name) {
        return request(HttpMethod.GET, "/roles/{name}", name);
    }

    @Override
    public ApiResponse<List<Permission>> getPermissions(String roleName) {
        return request(HttpMethod.GET, "/roles/{name}/permissions", roleName);
    }

    @Override
    public ApiResponse<List<Permission>> grantPermissions(String roleName, List<String> permissionNames) {
        return request(new OperationBatch<>("add", permissionNames),
                HttpMethod.PUT, "/roles/{roleName}/permissions", roleName);
    }

    @Override
    public ApiResponse<List<Permission>> revokePermissions(String roleName, List<String> permissionNames) {
        return request(new OperationBatch<>("remove", permissionNames),
                HttpMethod.PUT, "/roles/{roleName}/permissions", roleName);
    }

    @Override
    public ApiResponse<Permission> createPermission(Permission permArg) {
        return request(permArg,
                HttpMethod.POST, "/permissions");
    }

    @Override
    public ApiResponse deletePermission(String name) {
        return request(HttpMethod.DELETE, "/permissions/{name}", name);
    }

    @Override
    public ApiResponse<Permission> updatePermission(Permission permArg) {
        String name = permArg.getName();
        permArg.setName(null);
        return request(permArg,
                HttpMethod.PUT, "/permissions/{name}", name);
    }

    @Override
    public ApiResponse<Permission> getPermission(String name) {
        return request(HttpMethod.GET, "/permissions/{name}", name);
    }

    @Override
    public ApiResponse<List<Permission>> getPermissions() {
        return request(HttpMethod.GET, "/permissions");
    }
}
