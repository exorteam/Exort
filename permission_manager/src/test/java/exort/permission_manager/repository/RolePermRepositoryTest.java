package exort.permission_manager.repository;

import java.util.List;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.entity.RolePerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RolePermRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private RolePermRepository rpr;
    @Autowired
    private PermRepository pr;
    @Autowired
    private RoleRepository rr;

    @BeforeEach
    void setUp() {
        em.persist(new ExortPerm("perm1", "cat1", "desc1"));
        em.persist(new ExortPerm("perm2", "cat2", "desc2"));
        em.persist(new ExortPerm("perm3", "cat1", "desc3"));
        em.persist(new ExortRole("role1", "cat1", "desc1"));
        em.persist(new ExortRole("role8", "cat2", "desc2"));
        em.persist(new ExortRole("role9", "cat1", "desc3"));
        em.persist(new RolePerm("role1", "perm1"));
        em.persist(new RolePerm("role1", "perm2"));
        em.flush();
    }

    @Test
    void findAllOrderByCategory() {
        List<ExortPerm> perms = pr.findAllOrderByCategory();
        assertEquals(3, perms.size());
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm3", perms.get(1).getId());
        assertEquals("perm2", perms.get(2).getId());
    }

    @Test
    void findPermsByCategory() {
        List<ExortPerm> perms = pr.findByCategory("cat1");
        assertEquals(2, perms.size());
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm3", perms.get(1).getId());
    }

    @Test
    void findPermsByRoleId() {
        List<ExortPerm> perms = rpr.findPermsByRoleId("role1");
        assertEquals(2, perms.size());
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("cat1", perms.get(0).getCategory());
        assertEquals("desc1", perms.get(0).getDescription());
        assertEquals("perm2", perms.get(1).getId());
    }

    @Test
    void insertIgnore() {
        rpr.insertIgnore("role1", "perm1");
        rpr.insertIgnore("role1", "perm3");
        rpr.insertIgnore("role1", "perm4");
        rpr.insertIgnore("role2", "perm1");

        assertEquals(3, rpr.findAll().size());
    }

    @Test
    void deleteRole() {
        // unable to delete a role referenced by a role_perm
        assertThrows(
                org.springframework.dao.DataIntegrityViolationException.class,
                () -> { rr.deleteById("role1"); rr.flush(); });
    }

    @Test
    void deletePerm() {
        // unable to delete a perm referenced by a role_perm
        assertThrows(
                org.springframework.dao.DataIntegrityViolationException.class,
                () -> { pr.deleteById("perm1"); pr.flush(); });
    }

    @Test
    void deleteRolePerm() {
        // delete role_perm by role id
        rpr.deleteByRoleId("role1");
        // safe to delete role
        rr.deleteById("role1");
        // delete role_perm by perm id
        rpr.deleteByPermId("perm1");
        // safe to delete perm
        pr.deleteById("perm1");
        assertEquals(2, rr.findAll().size());
        assertEquals(2, pr.findAll().size());
        assertEquals(0, rpr.findAll().size());
    }

    @Test
    void deleteByRoleIdAndPermId() {
        // use this method to delete a role_perm instead of deleteById
        rpr.deleteByRoleIdAndPermId("role1", "perm1");
        assertEquals(1, rpr.findAll().size());
    }

    @Test
    void findRolesByCategory() {
        List<ExortRole> roles = rr.findByCategory("cat1");
        assertEquals(2, roles.size());
        assertEquals("role1", roles.get(0).getId());
        assertEquals("role9", roles.get(1).getId());
    }
}
