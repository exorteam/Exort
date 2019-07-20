package exort.permission_manager.repository;

import exort.permission_manager.entity.ExortPerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermRepository extends JpaRepository<ExortPerm, String> {
    @Query("SELECT perm FROM ExortPerm perm ORDER BY perm.category ASC")
    List<ExortPerm> findAllOrderByCategory();
}
