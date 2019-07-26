package exort.permission_manager.repository;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<ExortRole, String> {

    List<ExortRole> findByCategory(String category);

}
