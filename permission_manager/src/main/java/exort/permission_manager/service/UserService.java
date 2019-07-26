package exort.permission_manager.service;

import exort.permission_manager.entity.ExortRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    // List users by scope (pagination)
    Page<Long> list(String scope, Integer pageNum, Integer pageSize);

    // List users by scope and role (pagination)
    Page<Long> list(String scope, String roleId, Integer pageNum, Integer pageSize);

    // whether the user has the role on the scope
    boolean hasRole(Long userId, String scope, String roleId);

    // whether the user has the permission on the scope
    boolean hasPerm(Long userId, String scope, String permId);

    // List scopes by user
    List<String> listScopes(Long userId);

    // List all scopes (pagination)
    Page<String> listScopes(Integer pageNum, Integer pageSize);

    // List all roles owned by the user on the scope
    List<ExortRole> listRoles(Long userId, String scope);

    // grant roles to the user on the scope
    List<ExortRole> grantRoles(Long userId, String scope, List<String> roleIds);

    // revoke roles of the user on the scope
    List<ExortRole> revokeRoles(Long userId, String scope, List<String> roleIds);

    // remove user from the scope
    void revokeAllRoles(Long userId, String scope);

    // remove user from all scope
    void revokeAllRoles(Long userId);

}
