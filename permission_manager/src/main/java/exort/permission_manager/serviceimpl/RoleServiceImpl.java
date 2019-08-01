package exort.permission_manager.serviceimpl;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.repository.RolePermRepository;
import exort.permission_manager.repository.RoleRepository;
import exort.permission_manager.repository.UserScopeRoleRepository;
import exort.permission_manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository rr;
    @Autowired
    private RolePermRepository rpr;
    @Autowired
    private UserScopeRoleRepository usrr;

    @Transactional
    @Override
    public ExortRole create(String name, String category, String description) {
        if (rr.findById(name).isPresent()) {
            return null;
        }
        ExortRole role = new ExortRole(name, category, description);
        rr.save(role);
        return role;
    }

    @Transactional
    @Override
    public void delete(String name) {
        rpr.deleteByRoleId(name);
        usrr.deleteByRoleId(name);
        try {
            rr.deleteById(name);
        } catch (EmptyResultDataAccessException ignore) { }
    }

    @Transactional
    @Override
    public void deleteByCategory(String category) {
        List<ExortRole> roles = rr.findByCategory(category);
        for (ExortRole role: roles) {
            rpr.deleteByRoleId(role.getId());
            usrr.deleteByRoleId(role.getId());
            try {
                rr.deleteById(role.getId());
            } catch (EmptyResultDataAccessException ignore) { }
        }
    }

    @Transactional
    @Override
    public ExortRole update(String name, String description) {
        ExortRole role = rr.findById(name).orElse(null);
        if (role != null) {
            role.setDescription(description);
            rr.save(role);
            return role;
        }
        return null;
    }

    @Override
    public List<ExortRole> list(String category) {
        return rr.findByCategory(category);
    }

    @Override
    public ExortRole get(String name) {
        return rr.findById(name).orElse(null);
    }

    @Override
    public List<ExortPerm> listPerms(String name) {
        return rpr.findPermsByRoleId(name);
    }

    @Transactional
    @Override
    public List<ExortPerm> grantPerms(String name, List<String> permIds) {
        for (String permId: permIds) {
            rpr.insertIgnore(name, permId);
        }
        return listPerms(name);
    }

    @Transactional
    @Override
    public List<ExortPerm> revokePerms(String name, List<String> permIds) {
        for (String permId: permIds) {
            rpr.deleteByRoleIdAndPermId(name, permId);
        }
        return listPerms(name);
    }
}
