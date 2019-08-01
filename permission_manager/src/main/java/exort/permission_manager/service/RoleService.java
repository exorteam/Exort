package exort.permission_manager.service;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;

import java.util.List;

public interface RoleService {

    /* CRUD */

    ExortRole create(String name, String category, String description);

    ExortRole get(String name);

    void delete(String name);

    void deleteByCategory(String category);

    ExortRole update(String name, String description);

    /* List roles of the category */
    List<ExortRole> list(String category);

    /* List all permissions of the role */
    List<ExortPerm> listPerms(String name);

    /* Grant permissions to the role */
    List<ExortPerm> grantPerms(String name, List<String> permIds);

    /* Revoke permissions of the role */
    List<ExortPerm> revokePerms(String name, List<String> permIds);

}
