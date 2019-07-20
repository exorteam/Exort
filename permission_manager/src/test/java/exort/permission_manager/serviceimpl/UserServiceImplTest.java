package exort.permission_manager.serviceimpl;

import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.entity.RolePerm;
import exort.permission_manager.entity.UserScopeRole;
import exort.permission_manager.repository.PermRepository;
import exort.permission_manager.repository.RolePermRepository;
import exort.permission_manager.repository.RoleRepository;
import exort.permission_manager.repository.UserScopeRoleRepository;
import exort.permission_manager.service.RoleService;
import exort.permission_manager.service.UserService;
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
class UserServiceImplTest {

    @Autowired
    private RolePermRepository rpr;
    @Autowired
    private PermRepository pr;
    @Autowired
    private RoleRepository rr;
    @Autowired
    private UserScopeRoleRepository usrr;

    @Autowired
    private UserService us;
    @Autowired
    private RoleService rs;

    @BeforeEach
    void setUp() {
        pr.save(new ExortPerm("perm1", "cat1", "desc1"));
        pr.save(new ExortPerm("perm2", "cat2", "desc2"));
        pr.save(new ExortPerm("perm3", "cat1", "desc3"));
        rr.save(new ExortRole("role1", "desc1"));
        rr.save(new ExortRole("role2", "desc2"));
        rr.save(new ExortRole("role3", "desc3"));
        rpr.save(new RolePerm("role1", "perm1"));
        rpr.save(new RolePerm("role2", "perm3"));
        rpr.save(new RolePerm("role3", "perm1"));
        rpr.save(new RolePerm("role3", "perm2"));
        rpr.save(new RolePerm("role3", "perm3"));
        usrr.save(new UserScopeRole(1L, "scope1", "role1"));
        usrr.save(new UserScopeRole(1L, "scope1", "role2"));
        usrr.save(new UserScopeRole(1L, "scope2", "role3"));
        usrr.save(new UserScopeRole(2L, "scope1", "role3"));
        usrr.save(new UserScopeRole(2L, "scope2", "role1"));
        usrr.save(new UserScopeRole(3L, "scope2", "role3"));
    }

    @Test
    void list() {
        assertArrayEquals(new Long[]{1L, 2L}, us.list("scope1").toArray());
        assertArrayEquals(new Long[]{1L, 2L, 3L}, us.list("scope2").toArray());
        assertArrayEquals(new Long[]{1L}, us.list("scope1", "role1").toArray());
        assertArrayEquals(new Long[]{1L, 3L}, us.list("scope2", "role3").toArray());
    }

    @Test
    void listScopes() {
        assertArrayEquals(new String[]{"scope1", "scope2"}, us.listScopes(1L).toArray());
        assertArrayEquals(new String[]{"scope1", "scope2"}, us.listScopes(2L).toArray());
        assertArrayEquals(new String[]{"scope2"}, us.listScopes(3L).toArray());
    }

    @Test
    void listRoles() {
        ExortRole role1 = rr.getOne("role1");
        ExortRole role2 = rr.getOne("role2");
        ExortRole role3 = rr.getOne("role3");
        assertArrayEquals(new ExortRole[]{role1, role2}, us.listRoles(1L, "scope1").toArray());
        assertArrayEquals(new ExortRole[]{role3}, us.listRoles(1L, "scope2").toArray());
        assertArrayEquals(new ExortRole[]{role3}, us.listRoles(2L, "scope1").toArray());
        assertArrayEquals(new ExortRole[]{role1}, us.listRoles(2L, "scope2").toArray());
        assertArrayEquals(new ExortRole[]{}, us.listRoles(3L, "scope1").toArray());
        assertArrayEquals(new ExortRole[]{role3}, us.listRoles(3L, "scope2").toArray());
    }

    @Test
    void grantRoles() {
        ExortRole role1 = rr.getOne("role1");
        ExortRole role2 = rr.getOne("role2");
        ExortRole role3 = rr.getOne("role3");
        List<ExortRole> roles = us.grantRoles(1L, "scope1", Arrays.asList("role2", "role3", "role4"));
        assertEquals(3, roles.size());
        assertEquals(us.listRoles(1L, "scope1"), roles);
        assertArrayEquals(new ExortRole[]{role1, role2, role3}, roles.toArray());
    }

    @Test
    void revokeRoles() {
        ExortRole role1 = rr.getOne("role1");
        List<ExortRole> roles = us.revokeRoles(1L, "scope1", Arrays.asList("role2", "role3", "role4"));
        assertEquals(1, roles.size());
        assertEquals(us.listRoles(1L, "scope1"), roles);
        assertArrayEquals(new ExortRole[]{role1}, roles.toArray());
    }

    @Test
    void hasRole() {
        assertTrue(us.hasRole(1L, "scope1", "role1"));
        assertTrue(us.hasRole(1L, "scope1", "role2"));
        assertFalse(us.hasRole(1L, "scope1", "role3"));
        assertFalse(us.hasRole(1L, "scope2", "role1"));
        assertTrue(us.hasRole(1L, "scope2", "role3"));
        assertTrue(us.hasRole(2L, "scope1", "role3"));
        assertTrue(us.hasRole(2L, "scope2", "role1"));
        assertFalse(us.hasRole(2L, "scope2", "role3"));
        assertFalse(us.hasRole(3L, "scope1", "role3"));
        assertTrue(us.hasRole(3L, "scope2", "role3"));
    }

    @Test
    void hasPerm() {
        assertTrue(us.hasPerm(1L, "scope1", "perm1"));
        assertFalse(us.hasPerm(1L, "scope1", "perm2"));
        assertTrue(us.hasPerm(1L, "scope1", "perm3"));
        assertTrue(us.hasPerm(1L, "scope2", "perm1"));
        assertTrue(us.hasPerm(1L, "scope2", "perm2"));
        assertTrue(us.hasPerm(1L, "scope2", "perm3"));
        assertTrue(us.hasPerm(2L, "scope1", "perm1"));
        assertTrue(us.hasPerm(2L, "scope1", "perm2"));
        assertTrue(us.hasPerm(2L, "scope1", "perm3"));
        assertTrue(us.hasPerm(2L, "scope2", "perm1"));
        assertFalse(us.hasPerm(2L, "scope2", "perm2"));
        assertFalse(us.hasPerm(2L, "scope2", "perm3"));
        assertFalse(us.hasPerm(3L, "scope1", "perm1"));
        assertFalse(us.hasPerm(3L, "scope1", "perm2"));
        assertFalse(us.hasPerm(3L, "scope1", "perm3"));
        assertTrue(us.hasPerm(3L, "scope2", "perm1"));
        assertTrue(us.hasPerm(3L, "scope2", "perm2"));
        assertTrue(us.hasPerm(3L, "scope2", "perm3"));
    }

    @Test
    void deleteRole() {
        rs.delete("role3");
        assertEquals(3, pr.findAll().size());
        assertEquals(2, rr.findAll().size());
        assertEquals(2, rpr.findAll().size());
        assertEquals(3, usrr.findAll().size());
    }
}
