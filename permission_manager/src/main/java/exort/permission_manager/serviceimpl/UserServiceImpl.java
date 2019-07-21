package exort.permission_manager.serviceimpl;

import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.repository.UserScopeRoleRepository;
import exort.permission_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserScopeRoleRepository usrr;

    @Override
    public Page<Long> list(String scope, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return usrr.findUserIdsByScope(scope, pageable);
    }

    @Override
    public Page<Long> list(String scope, String roleId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return usrr.findUserIdsByScopeAndRoleId(scope, roleId, pageable);
    }

    @Override
    public boolean hasRole(Long userId, String scope, String roleId) {
        return usrr.findByUserIdAndScopeAndRoleId(userId, scope, roleId) != null;
    }

    @Override
    public boolean hasPerm(Long userId, String scope, String permId) {
        return usrr.findByUserIdAndScopeAndPermId(userId, scope, permId) != null;
    }

    @Override
    public List<String> listScopes(Long userId) {
        return usrr.findScopesByUserId(userId);
    }

    @Override
    public List<ExortRole> listRoles(Long userId, String scope) {
        return usrr.findRolesByUserIdAndScope(userId, scope);
    }

    @Override
    public List<ExortRole> grantRoles(Long userId, String scope, List<String> roleIds) {
        for (String roleId: roleIds) {
            usrr.insertIgnore(userId, scope, roleId);
        }
        return listRoles(userId, scope);
    }

    @Override
    public List<ExortRole> revokeRoles(Long userId, String scope, List<String> roleIds) {
        for (String roleId: roleIds) {
            usrr.deleteByUserIdANDScopeAndRoleId(userId, scope, roleId);
        }
        return listRoles(userId, scope);
    }
}
