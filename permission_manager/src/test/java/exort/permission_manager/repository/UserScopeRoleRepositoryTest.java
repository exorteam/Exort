package exort.permission_manager.repository;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.entity.RolePerm;
import exort.permission_manager.entity.UserScopeRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserScopeRoleRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserScopeRoleRepository usrr;

    @Autowired
    private RoleRepository rr;


    @BeforeEach
    void setUp() {
        em.persist(new ExortPerm("perm1", "cat1", "desc1"));
        em.persist(new ExortPerm("perm2", "cat2", "desc2"));
        em.persist(new ExortPerm("perm3", "cat1", "desc3"));
        em.persist(new ExortRole("role1", "desc1"));
        em.persist(new ExortRole("role2", "desc2"));
        em.persist(new ExortRole("role3", "desc3"));
        em.persist(new RolePerm("role1", "perm1"));
        em.persist(new RolePerm("role2", "perm3"));
        em.persist(new RolePerm("role3", "perm1"));
        em.persist(new RolePerm("role3", "perm2"));
        em.persist(new RolePerm("role3", "perm3"));
        em.persist(new UserScopeRole(1L, "scope1", "role1"));
        em.persist(new UserScopeRole(1L, "scope1", "role2"));
        em.persist(new UserScopeRole(1L, "scope2", "role3"));
        em.persist(new UserScopeRole(2L, "scope1", "role3"));
        em.persist(new UserScopeRole(2L, "scope2", "role1"));
        em.persist(new UserScopeRole(3L, "scope2", "role3"));
        em.flush();
    }

    @Test
    void findUserIdsByScope() {
        assertEquals(2, usrr.findUserIdsByScope("scope1").size());
        assertEquals(3, usrr.findUserIdsByScope("scope2").size());
    }

    @Test
    void findUserIdsByScopeAndRoleId() {
        assertEquals(1, usrr.findUserIdsByScopeAndRoleId("scope1", "role1").size());
        assertEquals(1, usrr.findUserIdsByScopeAndRoleId("scope1", "role2").size());
        assertEquals(1, usrr.findUserIdsByScopeAndRoleId("scope1", "role3").size());
        assertEquals(1, usrr.findUserIdsByScopeAndRoleId("scope2", "role1").size());
        assertEquals(0, usrr.findUserIdsByScopeAndRoleId("scope2", "role2").size());
        assertEquals(2, usrr.findUserIdsByScopeAndRoleId("scope2", "role3").size());
    }

    @Test
    void findScopesByUserId() {
        assertEquals(2, usrr.findScopesByUserId(1L).size());
        assertEquals(2, usrr.findScopesByUserId(2L).size());
        assertEquals(1, usrr.findScopesByUserId(3L).size());
    }

    @Test
    void findRolesByUserIdAndScope() {
        assertEquals(2, usrr.findRolesByUserIdAndScope(1L, "scope1").size());
        assertEquals(1, usrr.findRolesByUserIdAndScope(1L, "scope2").size());
        assertEquals(1, usrr.findRolesByUserIdAndScope(2L, "scope1").size());
        assertEquals(1, usrr.findRolesByUserIdAndScope(2L, "scope2").size());
        assertEquals(0, usrr.findRolesByUserIdAndScope(3L, "scope1").size());
        List<ExortRole> usr = usrr.findRolesByUserIdAndScope(3L, "scope2");
        assertEquals(1, usr.size());
        assertEquals("role3", usr.get(0).getId());
    }

    @Test
    void findByUserIdAndScopeAndRoleId() {
        assertNotNull(usrr.findByUserIdAndScopeAndRoleId(1L, "scope1", "role1"));
        assertNotNull(usrr.findByUserIdAndScopeAndRoleId(1L, "scope1", "role2"));
        assertNotNull(usrr.findByUserIdAndScopeAndRoleId(1L, "scope2", "role3"));
        assertNotNull(usrr.findByUserIdAndScopeAndRoleId(2L, "scope1", "role3"));
        assertNotNull(usrr.findByUserIdAndScopeAndRoleId(2L, "scope2", "role1"));
        assertNotNull(usrr.findByUserIdAndScopeAndRoleId(3L, "scope2", "role3"));
        assertNull(usrr.findByUserIdAndScopeAndRoleId(4L, "scope1", "role1"));
        assertNull(usrr.findByUserIdAndScopeAndRoleId(1L, "scope3", "role1"));
        assertNull(usrr.findByUserIdAndScopeAndRoleId(1L, "scope1", "role3"));
    }

    @Test
    void findByUserIdAndScopeAndPermId() {
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(1L, "scope1", "perm1"));
        assertNull(usrr.findByUserIdAndScopeAndPermId(1L, "scope1", "perm2"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(1L, "scope1", "perm3"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(1L, "scope2", "perm1"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(1L, "scope2", "perm2"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(1L, "scope2", "perm3"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(2L, "scope1", "perm1"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(2L, "scope1", "perm2"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(2L, "scope1", "perm3"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(2L, "scope2", "perm1"));
        assertNull(usrr.findByUserIdAndScopeAndPermId(2L, "scope2", "perm2"));
        assertNull(usrr.findByUserIdAndScopeAndPermId(2L, "scope2", "perm3"));
        assertNull(usrr.findByUserIdAndScopeAndPermId(3L, "scope1", "perm1"));
        assertNull(usrr.findByUserIdAndScopeAndPermId(3L, "scope1", "perm2"));
        assertNull(usrr.findByUserIdAndScopeAndPermId(3L, "scope1", "perm3"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(3L, "scope2", "perm1"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(3L, "scope2", "perm2"));
        assertNotNull(usrr.findByUserIdAndScopeAndPermId(3L, "scope2", "perm3"));
    }

    @Test
    void insertIgnore() {
        usrr.insertIgnore(1L, "scope1", "role1");
        usrr.insertIgnore(2L, "scope1", "role3");
        usrr.insertIgnore(1L, "scope2", "role1");
        assertEquals(7, usrr.findAll().size());
    }

    @Test
    void deleteRole() {
        // unable delete a role referenced by a user_scope_role
        assertThrows(
                org.springframework.dao.DataIntegrityViolationException.class,
                () -> { rr.deleteById("role1"); rr.flush(); });
    }

    @Test
    void deleteByRoleId() {
        // delete by role id
        usrr.deleteByRoleId("role1");
        // safe to delete role
        rr.deleteById("role1");
        assertEquals(4, usrr.findAll().size());
    }

    @Test
    void deleteByUserIdANDScopeAndRoleId() {
        // use this method to delete user_scope_role instead of deleteById
        usrr.deleteByUserIdANDScopeAndRoleId(1L, "scope1", "role1");
        usrr.deleteByUserIdANDScopeAndRoleId(1L, "scope1", "role3");
        usrr.deleteByUserIdANDScopeAndRoleId(2L, "scope2", "role1");
        assertEquals(4, usrr.findAll().size());
    }
}