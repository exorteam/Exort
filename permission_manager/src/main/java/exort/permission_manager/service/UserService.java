package exort.permission_manager.service;

import exort.permission_manager.entity.ExortRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<Long> list(String scope, Integer pageNum, Integer pageSize);
    Page<Long> list(String scope, String roleId, Integer pageNum, Integer pageSize);
    boolean hasRole(Long userId, String scope, String roleId);
    boolean hasPerm(Long userId, String scope, String permId);
    List<String> listScopes(Long userId);
    Page<String> listScopes(Integer pageNum, Integer pageSize);
    List<ExortRole> listRoles(Long userId, String scope);
    List<ExortRole> grantRoles(Long userId, String scope, List<String> roleIds);
    List<ExortRole> revokeRoles(Long userId, String scope, List<String> roleIds);
}
