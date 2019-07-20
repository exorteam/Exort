package exort.permission_manager.service;

import exort.permission_manager.entity.ExortRole;

import java.util.List;

public interface UserService {
    List<Long> list(String scope);
    List<Long> list(String scope, String roleId);
    boolean hasRole(Long userId, String scope, String roleId);
    boolean hasPerm(Long userId, String scope, String permId);
    List<String> listScopes(Long userId);
    List<ExortRole> listRoles(Long userId, String scope);
    List<ExortRole> grantRoles(Long userId, String scope, List<String> roleIds);
    List<ExortRole> revokeRoles(Long userId, String scope, List<String> roleIds);
}
