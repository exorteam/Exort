package exort.permission_manager.service;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;

import java.util.List;

public interface RoleService {
    ExortRole create(String name, String description);
    void delete(String name);
    ExortRole update(String name, String description);
    ExortRole get(String name);
    List<ExortPerm> listPerms(String name);
    List<ExortPerm> grantPerms(String name, List<String> permIds);
    List<ExortPerm> revokePerms(String name, List<String> permIds);
}
