package exort.permission_manager.serviceimpl;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.entity.RolePerm;
import exort.permission_manager.repository.PermRepository;
import exort.permission_manager.repository.RolePermRepository;
import exort.permission_manager.repository.RoleRepository;
import exort.permission_manager.service.PermService;
import exort.permission_manager.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RoleServiceImplTest {

    @Autowired
    private RolePermRepository rpr;
    @Autowired
    private PermRepository pr;
    @Autowired
    private RoleRepository rr;

    @Autowired
    private RoleService rs;

    @BeforeEach
    void setUp() {
        pr.save(new ExortPerm("perm1", "cat1", "desc1"));
        pr.save(new ExortPerm("perm2", "cat2", "desc2"));
        pr.save(new ExortPerm("perm3", "cat1", "desc3"));
        rr.save(new ExortRole("role1", "cat1", "desc1"));
        rr.save(new ExortRole("role2", "cat2", "desc2"));
        rr.save(new ExortRole("role3", "cat1", "desc3"));
        rpr.save(new RolePerm("role1", "perm1"));
        rpr.save(new RolePerm("role1", "perm3"));
        rpr.save(new RolePerm("role2", "perm1"));
        rpr.save(new RolePerm("role2", "perm2"));
    }

    @Test
    void create() {
        ExortRole role = rs.create("role4", "cat1", "desc4");
        assertEquals(4, rr.findAll().size());
        assertEquals(rr.findById("role4").get(), role);

        // duplicate
        assertNull(rs.create("role1", "cat1", "desc5"));
        assertNull(rs.create("role4", "cat1", "desc5"));
    }

    @Test
    void delete() {
        // delete non-exist role
        rs.delete("role4");
        assertEquals(3, pr.findAll().size());
        assertEquals(3, rr.findAll().size());
        assertEquals(4, rpr.findAll().size());

        // delete free role
        rs.delete("role3");
        assertEquals(3, pr.findAll().size());
        assertEquals(2, rr.findAll().size());
        assertEquals(4, rpr.findAll().size());

        // delete role own some perms
        rs.delete("role1");
        assertEquals(3, pr.findAll().size());
        assertEquals(1, rr.findAll().size());
        assertEquals(2, rpr.findAll().size());
    }

    @Test
    void deleteByCategory() {
        // delete roles in a category
        rs.deleteByCategory("cat1");
        assertEquals(3, pr.findAll().size());
        assertEquals(1, rr.findAll().size());
        assertEquals(2, rpr.findAll().size());

        // delete roles in an empty category
        rs.deleteByCategory("cat3");
        assertEquals(3, pr.findAll().size());
        assertEquals(1, rr.findAll().size());
        assertEquals(2, rpr.findAll().size());
    }

    @Test
    void update() {
        ExortRole role = rs.update("role1", "cat2", "desc");
        assertEquals(rr.findById("role1").get(), role);
        assertEquals(1, rr.findByCategory("cat1").size());
        assertEquals(2, rr.findByCategory("cat2").size());

        // update non-exist role
        assertNull(rs.update("role5", "cat2", "desc"));
    }

    @Test
    void get() {
        assertEquals(rr.findById("role1").get(), rs.get("role1"));
        assertNull(rs.get("role5"));
    }

    @Test
    void list() {
        List<ExortRole> roles = rr.findByCategory("cat1");
        assertEquals(2, roles.size());
        assertEquals("role1", roles.get(0).getId());
        assertEquals("role3", roles.get(1).getId());
    }

    @Test
    void listPerms() {
        List<ExortPerm> perms = rs.listPerms("role1");
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm3", perms.get(1).getId());
        perms = rs.listPerms("role2");
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm2", perms.get(1).getId());
    }

    @Test
    void grantPerms() {
        // only perm2 is valid and others will be ignored
        List<ExortPerm> perms = rs.grantPerms("role1", Arrays.asList("perm2", "perm3", "perm4"));
        assertEquals(rpr.findPermsByRoleId("role1"), perms);
        assertEquals(3, perms.size());
        assertEquals("perm1", perms.get(0).getId());
        assertEquals("perm2", perms.get(1).getId());
        assertEquals("perm3", perms.get(2).getId());
    }

    @Test
    void revokePerms() {
        // only perm3 is valid and others will be ignored
        List<ExortPerm> perms = rs.revokePerms("role1", Arrays.asList("perm2", "perm3"));
        assertEquals(rpr.findPermsByRoleId("role1"), perms);
        assertEquals(1, perms.size());
        assertEquals("perm1", perms.get(0).getId());
    }
}
