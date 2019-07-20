package exort.permission_manager.service;

import exort.permission_manager.entity.ExortPerm;

import java.util.List;

public interface PermService {
    ExortPerm create(String name, String category, String description);
    void delete(String name);
    ExortPerm update(String name, String category, String description);
    ExortPerm get(String name);
    List<ExortPerm> list();
}
