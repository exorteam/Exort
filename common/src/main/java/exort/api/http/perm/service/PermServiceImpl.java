package exort.api.http.perm.service;

import exort.api.http.common.ResponseType;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.OperationBatch;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermServiceImpl extends RestTemplate implements PermService {

    @Value("${exort.perm.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.perm.endpoint:localhost}")
    public void setLocalhost(String endpoint) { super.setEndpoint(endpoint); }

    @Override
    public ApiResponse<List<String>> getScopes(Long userId) {
        return request(new ResponseType<List<String>>(),
                HttpMethod.GET, "/users/{userId}/scopes", userId);
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes() {
        return request(new ResponseType<PagedData<String>>(),
                HttpMethod.GET, new PageQuery(), "/scopes");
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes(PageQuery pageQuery) {
        return request(new ResponseType<PagedData<String>>(),
                HttpMethod.GET, pageQuery, "/scopes");
    }

    @Override
    public ApiResponse<List<Role>> getRoles(Long userId, String scope) {
        return request(new ResponseType<List<Role>>(),
                HttpMethod.GET, "/users/{userId}/scopes/{scope}/roles",
                userId, scope);
    }

    @Override
    public ApiResponse<List<Role>> grantRoles(Long userId, String scope, List<String> roleNames) {
        return request(new ResponseType<List<Role>>(),
                new OperationBatch<>("add", roleNames),
                HttpMethod.PUT, "/users/{userId}/scopes/{scope}/roles",
                userId, scope);
    }

    @Override
    public ApiResponse<List<Role>> revokeRoles(Long userId, String scope, List<String> roleNames) {
        return request(new ResponseType<List<Role>>(),
                new OperationBatch<>("remove", roleNames),
                HttpMethod.PUT, "/users/{userId}/scopes/{scope}/roles",
                userId, scope);
    }

    @Override
    public ApiResponse hasRole(Long userId, String scope, String roleName) {
        return request(new ResponseType(),
                HttpMethod.GET, "/users/{userId}/scopes/{scope}/roles/{roleName}",
                userId, scope, roleName);
    }

    @Override
    public ApiResponse hasPermission(Long userId, String scope, String permissionName) {
        return request(new ResponseType<>(),
                HttpMethod.GET, "/users/{userId}/scopes/{scope}/permissions/{permissionName}",
                userId, scope, permissionName);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope) {
        return request(new ResponseType<PagedData<Long>>(),
                HttpMethod.GET, new PageQuery(), "/scopes/{scope}/users",
                scope);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope, PageQuery pageQuery) {
        return request(new ResponseType<PagedData<Long>>(),
                HttpMethod.GET, pageQuery, "/scopes/{scope}/users",
                scope);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope, String roleName) {
        return request(new ResponseType<PagedData<Long>>(),
                HttpMethod.GET, new PageQuery(), "/scopes/{scope}/roles/{roleName}/users",
                scope, roleName);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(String scope, String roleName, PageQuery pageQuery) {
        return request(new ResponseType<PagedData<Long>>(),
                HttpMethod.GET, pageQuery, "/scopes/{scope}/roles/{roleName}/users",
                scope, roleName);
    }

    @Override
    public ApiResponse<Role> createRole(Role roleArg) {
        return request(new ResponseType<Role>(),
                roleArg,
                HttpMethod.POST, "/roles");
    }

    @Override
    public ApiResponse deleteRole(String name) {
        return request(new ResponseType(),
                HttpMethod.DELETE, "/roles/{name}", name);
    }

    @Override
    public ApiResponse<Role> updateRole(Role roleArg) {
        String name = roleArg.getName();
        roleArg.setName(null);
        return  request(new ResponseType<Role>(),
                roleArg,
                HttpMethod.PUT, "/roles/{name}", name);
    }

    @Override
    public ApiResponse<Role> getRole(String name) {
        return request(new ResponseType<Role>(),
                HttpMethod.GET, "/roles/{name}", name);
    }

    @Override
    public ApiResponse<List<Permission>> getPermissions(String roleName) {
        return request(new ResponseType<List<Permission>>(),
                HttpMethod.GET, "/roles/{name}/permissions", roleName);
    }

    @Override
    public ApiResponse<List<Permission>> grantPermissions(String roleName, List<String> permissionNames) {
        return request(new ResponseType<List<Permission>>(),
                new OperationBatch<>("add", permissionNames),
                HttpMethod.PUT, "/roles/{roleName}/permissions", roleName);
    }

    @Override
    public ApiResponse<List<Permission>> revokePermissions(String roleName, List<String> permissionNames) {
        return request(new ResponseType<List<Permission>>(),
                new OperationBatch<>("remove", permissionNames),
                HttpMethod.PUT, "/roles/{roleName}/permissions", roleName);
    }

    @Override
    public ApiResponse<Permission> createPermission(Permission permArg) {
        return request(new ResponseType<Permission>(),
                permArg,
                HttpMethod.POST, "/permissions");
    }

    @Override
    public ApiResponse deletePermission(String name) {
        return request(new ResponseType<>(),
                HttpMethod.DELETE, "/permissions/{name}", name);
    }

    @Override
    public ApiResponse<Permission> updatePermission(Permission permArg) {
        String name = permArg.getName();
        permArg.setName(null);
        return request(new ResponseType<Permission>(),
                permArg,
                HttpMethod.PUT, "/permissions/{name}", name);
    }

    @Override
    public ApiResponse<Permission> getPermission(String name) {
        return request(new ResponseType<Permission>(),
                HttpMethod.GET, "/permissions/{name}", name);
    }

    @Override
    public ApiResponse<List<Permission>> getPermissions() {
        return request(new ResponseType<List<Permission>>(),
                HttpMethod.GET, "/permissions");
    }
}
