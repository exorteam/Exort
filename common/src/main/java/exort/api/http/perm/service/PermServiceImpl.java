package exort.api.http.perm.service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.OperationBatch;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class PermServiceImpl implements PermService {

    @Value("${exort.perm.endpoint: localhost}")
    @Getter
    @Setter
    private String endpoint;

    private RestTemplate rest;

    @Autowired
    public PermServiceImpl() {
        rest = new RestTemplate();
        rest.setErrorHandler(new PermServiceResponseErrorHandler());
    }

    private String url(String path) {
        return "http://" + endpoint + path;
    }

    private String pageUrl(String path, Integer pageNum, Integer pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url(path));
        if (pageNum != null) {
            builder = builder.queryParam("pageNum", pageNum);
        }
        if (pageSize != null) {
            builder = builder.queryParam("pageSize", pageSize);
        }
        return builder.build().toString();
    }

    @Override
    public ApiResponse<List<String>> getScopes(@NotNull Long userId) {
        return rest.exchange(
                url("/users/{userId}/scopes"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<List<String>>>() { },
                userId
        ).getBody();
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes() {
        return getScopes(null, null);
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes(Integer pageSize) {
        return getScopes(null, pageSize);
    }

    @Override
    public ApiResponse<PagedData<String>> getScopes(Integer pageNum, Integer pageSize) {
        return rest.exchange(
                pageUrl("/scopes", pageNum, pageSize),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<PagedData<String>>>() { }
        ).getBody();
    }

    @Override
    public ApiResponse<List<Role>> getRoles(@NotNull Long userId, @NotNull String scope) {
        return rest.exchange(
                url("/users/{userId}/scopes/{scope}/roles"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<List<Role>>>() { },
                userId, scope
        ).getBody();
    }

    @Override
    public ApiResponse<List<Role>> grantRoles(@NotNull Long userId, @NotNull String scope, List<String> roleNames) {
        return rest.exchange(
                url("/users/{userId}/scopes/{scope}/roles"),
                HttpMethod.PUT, new HttpEntity<>(new OperationBatch<>("add", roleNames)),
                new ParameterizedTypeReference<ApiResponse<List<Role>>>() { },
                userId, scope
        ).getBody();
    }

    @Override
    public ApiResponse<List<Role>> revokeRoles(@NotNull Long userId, @NotNull String scope, List<String> roleNames) {
        return rest.exchange(
                url("/users/{userId}/scopes/{scope}/roles"),
                HttpMethod.PUT, new HttpEntity<>(new OperationBatch<>("remove", roleNames)),
                new ParameterizedTypeReference<ApiResponse<List<Role>>>() { },
                userId, scope
        ).getBody();
    }

    @Override
    public ApiResponse hasRole(@NotNull Long userId, @NotNull String scope, @NotNull String roleName) {
        return rest.exchange(
                url("/users/{userId}/scopes/{scope}/roles/{roleName}"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse>() { },
                userId, scope, roleName
        ).getBody();
    }

    @Override
    public ApiResponse hasPermission(@NotNull Long userId, @NotNull String scope, @NotNull String permissionName) {
        return rest.exchange(
                url("/users/{userId}/scopes/{scope}/permissions/{permissionName}"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse>() { },
                userId, scope, permissionName
        ).getBody();
    }


    @Override
    public ApiResponse<PagedData<Long>> getUsers(@NotNull String scope) {
        return getUsers(scope, (Integer) null, null);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, Integer pageSize) {
        return getUsers(scope, (Integer) null, pageSize);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, Integer pageNum, Integer pageSize) {
        return rest.exchange(
                pageUrl("/scopes/{scope}/users", pageNum, pageSize),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<PagedData<Long>>>() { },
                scope
        ).getBody();
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, @NotNull String roleName) {
        return getUsers(scope, roleName, null, null);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, @NotNull String roleName, Integer pageSize) {
        return getUsers(scope, roleName, null, pageSize);
    }

    @Override
    public ApiResponse<PagedData<Long>> getUsers(@NotNull String scope, @NotNull String roleName, Integer pageNum, Integer pageSize) {
        return rest.exchange(
                pageUrl("/scopes/{scope}/roles/{roleName}/users", pageNum, pageSize),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<PagedData<Long>>>() { },
                scope, roleName
        ).getBody();
    }

    @Override
    public ApiResponse<Role> createRole(@NotNull Role roleArg) {
        return rest.exchange(
                url("/roles"),
                HttpMethod.POST, new HttpEntity<>(roleArg),
                new ParameterizedTypeReference<ApiResponse<Role>>() { }
        ).getBody();
    }

    @Override
    public ApiResponse deleteRole(@NotNull String name) {
        return rest.exchange(
                url("/roles/{name}"),
                HttpMethod.DELETE, null,
                new ParameterizedTypeReference<ApiResponse>() { },
                name
        ).getBody();
    }

    @Override
    public ApiResponse<Role> updateRole(@NotNull Role roleArg) {
        String name = roleArg.getName();
        roleArg.setName(null);
        return rest.exchange(
                url("/roles/{name}"),
                HttpMethod.PUT, new HttpEntity<>(roleArg),
                new ParameterizedTypeReference<ApiResponse<Role>>() { },
                name
        ).getBody();
    }

    @Override
    public ApiResponse<Role> getRole(@NotNull String name) {
        return rest.exchange(
                url("/roles/{name}"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<Role>>() { },
                name
        ).getBody();
    }

    @Override
    public ApiResponse<List<Permission>> getPermissions(@NotNull String roleName) {
        return rest.exchange(
                url("/roles/{name}/permissions"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<List<Permission>>>() { },
                roleName
        ).getBody();
    }

    @Override
    public ApiResponse<List<Permission>> grantPermissions(@NotNull String roleName, List<String> permissionNames) {
        return rest.exchange(
                url("/roles/{roleName}/permissions"),
                HttpMethod.PUT, new HttpEntity<>(new OperationBatch<>("add", permissionNames)),
                new ParameterizedTypeReference<ApiResponse<List<Permission>>>() { },
                roleName
        ).getBody();
    }

    @Override
    public ApiResponse<List<Permission>> revokePermissions(@NotNull String roleName, List<String> permissionNames) {
        return rest.exchange(
                url("/roles/{roleName}/permissions"),
                HttpMethod.PUT, new HttpEntity<>(new OperationBatch<>("remove", permissionNames)),
                new ParameterizedTypeReference<ApiResponse<List<Permission>>>() { },
                roleName
        ).getBody();
    }

    @Override
    public ApiResponse<Permission> createPermission(@NotNull Permission permArg) {
        return rest.exchange(
                url("/permissions"),
                HttpMethod.POST, new HttpEntity<>(permArg),
                new ParameterizedTypeReference<ApiResponse<Permission>>() { }
        ).getBody();
    }

    @Override
    public ApiResponse deletePermission(@NotNull String name) {
        return rest.exchange(
                url("/permissions/{name}"),
                HttpMethod.DELETE, null,
                new ParameterizedTypeReference<ApiResponse>() { },
                name
        ).getBody();
    }

    @Override
    public ApiResponse<Permission> updatePermission(@NotNull Permission permArg) {
        String name = permArg.getName();
        permArg.setName(null);
        return rest.exchange(
                url("/permissions/{name}"),
                HttpMethod.PUT, new HttpEntity<>(permArg),
                new ParameterizedTypeReference<ApiResponse<Permission>>() { },
                name
        ).getBody();
    }

    @Override
    public ApiResponse<Permission> getPermission(@NotNull String name) {
        return rest.exchange(
                url("/permissions/{name}"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<Permission>>() { },
                name
        ).getBody();
    }

    @Override
    public ApiResponse<List<Permission>> getPermissions() {
        return rest.exchange(
                url("/permissions"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ApiResponse<List<Permission>>>() { }
        ).getBody();
    }
}
