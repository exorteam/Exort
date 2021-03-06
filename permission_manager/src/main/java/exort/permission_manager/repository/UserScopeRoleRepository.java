package exort.permission_manager.repository;

import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.entity.UserScopeRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserScopeRoleRepository extends JpaRepository<UserScopeRole, Long> {
    /* Find users */
    @Query("SELECT DISTINCT usr.userId FROM UserScopeRole usr WHERE usr.scope = ?1")
    Page<Long> findUserIdsByScope(String scope, Pageable pageable);

    @Query("SELECT DISTINCT usr.userId FROM UserScopeRole usr WHERE usr.scope = ?1 AND usr.roleId = ?2")
    Page<Long> findUserIdsByScopeAndRoleId(String scope, String roleId, Pageable pageable);

    /* Find scopes */
    @Query("SELECT DISTINCT usr.scope FROM UserScopeRole usr WHERE usr.userId = ?1")
    List<String> findScopesByUserId(Long userId);

    @Query("SELECT DISTINCT usr.scope FROM UserScopeRole usr")
    Page<String> findScopes(Pageable pageable);

    /* Find roles */
    @Query("SELECT usr.role FROM UserScopeRole usr WHERE usr.userId = ?1 AND usr.scope = ?2")
    List<ExortRole> findRolesByUserIdAndScope(Long userId, String scope);

    /* Find usr */
    UserScopeRole findByUserIdAndScopeAndRoleId(Long userId, String scope, String roleId);

    @Query("SELECT usr FROM UserScopeRole usr JOIN RolePerm rp ON usr.roleId = rp.roleId WHERE usr.userId = ?1 AND usr.scope = ?2 AND rp.permId = ?3")
    UserScopeRole findByUserIdAndScopeAndPermId(Long userId, String scope, String permId);

    /* insert (user, scope, role) */
    @Modifying
    @Query(value = "INSERT IGNORE INTO user_scope_role(user_id, scope, role_id) VALUES(?1, ?2, ?3)", nativeQuery = true)
    void insertIgnore(Long userId, String scope, String roleId);

    /* delete (user, scope, role) by role */
    @Modifying
    @Query("DELETE FROM UserScopeRole usr WHERE usr.roleId = ?1")
    void deleteByRoleId(String roleId);

    /* delete (user, scope, role) by user */
    @Modifying
    @Query("DELETE FROM UserScopeRole usr WHERE usr.userId = ?1")
    void deleteByUserId(Long userId);

    /* delete (user, scope, role) by (user, scope) */
    @Modifying
    @Query("DELETE FROM UserScopeRole usr WHERE usr.userId = ?1 AND usr.scope = ?2")
    void deleteByUserIdAndScope(Long userId, String scope);

    /* delete (user, scope, role) by scope */
    @Modifying
    @Query("DELETE FROM UserScopeRole usr WHERE usr.scope = ?1")
    void deleteByScope(String scope);

    /* delete (user, scope, role) by (user, scope, role) */
    @Modifying
    @Query("DELETE FROM UserScopeRole usr WHERE usr.userId = ?1 AND usr.scope = ?2 AND usr.roleId = ?3")
    void deleteByUserIdANDScopeAndRoleId(Long userId, String scope, String roleId);
}
