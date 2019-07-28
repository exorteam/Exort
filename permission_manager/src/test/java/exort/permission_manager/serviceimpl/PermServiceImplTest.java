package exort.permission_manager.serviceimpl;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.entity.RolePerm;
import exort.permission_manager.repository.PermRepository;
import exort.permission_manager.repository.RolePermRepository;
import exort.permission_manager.repository.RoleRepository;
import exort.permission_manager.repository.UserScopeRoleRepository;
import exort.permission_manager.service.PermService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PermServiceImplTest {

    @Autowired
    private RolePermRepository rpr;
    @Autowired
    private PermRepository pr;
    @Autowired
    private RoleRepository rr;

    @Autowired
    private PermService ps;

    @BeforeEach
    void setUp() {
        pr.save(new ExortPerm("perm1", "cat1", "desc1"));
        pr.save(new ExortPerm("perm2", "cat2", "desc2"));
        pr.save(new ExortPerm("perm3", "cat1", "desc3"));
        pr.save(new ExortPerm("perm4", "cat1", "desc4"));
        rr.save(new ExortRole("role1", "cat1", "desc1"));
        rr.save(new ExortRole("role2", "cat2", "desc2"));
        rpr.save(new RolePerm("role1", "perm1"));
        rpr.save(new RolePerm("role1", "perm3"));
        rpr.save(new RolePerm("role2", "perm1"));
        rpr.save(new RolePerm("role2", "perm2"));
    }

    @Test
    void create() {
        ExortPerm perm = ps.create("perm5", "cat2", "desc5");
        List<ExortPerm> perms = pr.findAllOrderByCategory();
        assertEquals(5, perms.size());
        assertEquals(perms.get(4), perm);

        // duplicate
        assertNull(ps.create("perm1", "cat2", "desc6"));
        assertNull(ps.create("perm5", "cat2", "desc6"));
    }

    @Test
    void delete() {
        // delete non-exist perm
        ps.delete("perm5");
        assertEquals(4, pr.findAll().size());
        assertEquals(2, rr.findAll().size());
        assertEquals(4, rpr.findAll().size());

        // delete free perm
        ps.delete("perm4");
        assertEquals(3, pr.findAll().size());
        assertEquals(2, rr.findAll().size());
        assertEquals(4, rpr.findAll().size());

        // delete perm owned by some roles
        ps.delete("perm1");
        assertEquals(2, pr.findAll().size());
        assertEquals(2, rr.findAll().size());
        assertEquals(2, rpr.findAll().size());
    }

    @Test
    void update() {
        ExortPerm perm = ps.update("perm1", "cat2", "desc1");
        assertEquals(pr.findById("perm1").get(), perm);

        // update non-exist perm
        assertNull(ps.update("perm5", "cat2", "desc1"));
    }

    @Test
    void get() {
        assertEquals(pr.findById("perm1").get(), ps.get("perm1"));
        assertNull(ps.get("perm5"));
    }

    @Test
    void list() {
        List<ExortPerm> perms = ps.list();
        assertEquals(4, perms.size());
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm3", perms.get(1).getId());
        assertEquals("perm4", perms.get(2).getId());
        assertEquals("perm2", perms.get(3).getId());

        perms = ps.list("cat1");
        assertEquals(3, perms.size());
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm3", perms.get(1).getId());
        assertEquals("perm4", perms.get(2).getId());
    }
}
