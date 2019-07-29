package exort.permission_manager.repository;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.RolePerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermRepository extends JpaRepository<RolePerm, Long> {
    @Query("SELECT rp.perm FROM RolePerm rp WHERE rp.roleId = ?1")
    List<ExortPerm> findPermsByRoleId(String roleId);

    @Modifying
    @Query(value = "INSERT IGNORE INTO role_perm(role_id, perm_id) VALUES(?1, ?2)", nativeQuery = true)
    void insertIgnore(String roleId, String permId);

    @Modifying
    @Query("DELETE FROM RolePerm rp WHERE rp.roleId = ?1 AND rp.permId = ?2")
    void deleteByRoleIdAndPermId(String roleId, String permId);

    @Modifying
    @Query("DELETE FROM RolePerm rp WHERE rp.roleId = ?1")
    void deleteByRoleId(String roleId);

    @Modifying
    @Query("DELETE FROM RolePerm rp WHERE rp.permId = ?1")
    void deleteByPermId(String permId);
}
