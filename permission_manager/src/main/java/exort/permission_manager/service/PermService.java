package exort.permission_manager.service;

import exort.permission_manager.entity.ExortPerm;

import java.util.List;

public interface PermService {

    /* CRUD */

    ExortPerm create(String name, String category, String description);

    ExortPerm get(String name);

    void delete(String name);

    ExortPerm update(String name, String category, String description);

    /* List all permissions order by category */
    List<ExortPerm> list();

    /* List permissions in the category */
    List<ExortPerm> list(String category);

}
