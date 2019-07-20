package exort.permission_manager.repository;

import exort.permission_manager.entity.ExortRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<ExortRole, String> { }
