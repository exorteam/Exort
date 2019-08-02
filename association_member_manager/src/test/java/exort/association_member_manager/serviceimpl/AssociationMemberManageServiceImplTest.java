package exort.association_member_manager.serviceimpl;

import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AssociationMemberManageServiceImplTest {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private PermService ps;

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    private String roleName(String associationId, int departmentId) {
        switch (departmentId) {
            case 1:
                return MANAGER;
            case 2:
                return MEMBER;
            default:
                return "association_" + associationId + "_" + departmentId;
        }
    }

    private String scope(String associationId) {
        return "association_" + associationId;
    }

    @Before
    public void setUp() {
        associationMemberManageService.initDepartment("a", 1);
    }

    @After
    public void afterAll() {
        departmentRepository.deleteAll();
        ps.clearScope(scope("a"));
        ps.deleteRolesByCategory(scope("a"));
    }

    @Test
    public void adoptApplication() {

        ApplicationDepartmentInfo applicationDepartmentInfo = new ApplicationDepartmentInfo();
        applicationDepartmentInfo.setAssociationId("a");
        applicationDepartmentInfo.setDepartmentId(2);
        Application<ApplicationDepartmentInfo> application = new Application<>(3L, "acceptApplication", applicationDepartmentInfo);

        Assert.assertEquals(true, associationMemberManageService.adoptApplication(3, "create", application));

        // 删去创建的用户
        associationMemberManageService.deleteOneInAssociation("a", 3);
    }

    @Test
    public void getDepartmentTree() {
        associationMemberManageService.initDepartment("b", 2);

        associationMemberManageService.getDepartmentTree("b");

        Assert.assertEquals(2, associationMemberManageService.getDepartmentTree("b").size());

    }

    @Test
    public void getSpecDepartmentInfo() {
        associationMemberManageService.initDepartment("b", 2);
        Department departmentInfo = associationMemberManageService.getSpecDepartmentInfo("b", 1);
        Assert.assertEquals("管理层", departmentInfo.getName());
    }

    @Test
    public void createDepartment() {
        Department department = associationMemberManageService.createDepartment("a", "hello", "hello try", 0);
        Assert.assertEquals("hello", department.getName());

        // 删除新创建的role
        ps.deleteRole(roleName("a", department.getDepartmentId()));
    }

    @Test
    public void deleteDepartment() {
        Department createDepartment = associationMemberManageService.createDepartment("b", "hello", "das", 1);

        Department department = associationMemberManageService.deleteDepartment(createDepartment.getAssociationId(), createDepartment.getDepartmentId());

        Assert.assertEquals(1, department.getDepartmentId());

        // 删除新创建的role
        ps.deleteRole(roleName("b", department.getDepartmentId()));
    }

    @Test
    public void editDepartment() {
        Department createDepartment = associationMemberManageService.createDepartment("a", "hello", "das", 1);

        Department department = associationMemberManageService.editDepartment(createDepartment.getAssociationId(), createDepartment.getDepartmentId(), "black", "back", 0);

        Assert.assertEquals("black", department.getName());

        // 删除新创建的role
        ps.deleteRole(roleName("a", department.getDepartmentId()));
    }

    @Test
    public void getSpecMemberList() {
        Department department = associationMemberManageService.createDepartment("a", "asd", "asd", 1);
        associationMemberManageService.addOneToDepartment("a", department.getDepartmentId(), 1);
        associationMemberManageService.addOneToDepartment("a", department.getDepartmentId(), 2);
        associationMemberManageService.addOneToDepartment("a", department.getDepartmentId(), 3);
        associationMemberManageService.addOneToDepartment("a", department.getDepartmentId(), 4);
        associationMemberManageService.addOneToDepartment("a", department.getDepartmentId(), 5);

        Assert.assertEquals(5, associationMemberManageService.getSpecMemberList("a", department.getDepartmentId()).size());

        // 删除role
        ps.deleteRole(roleName(department.getAssociationId(), department.getDepartmentId()));
    }

    @Test
    public void removeOneFromDepartment() {

        int userId = 3;
        String associationId = "a";
        int beforesize = ps.grantRoles(Long.valueOf(userId), scope(associationId), Arrays.asList(MEMBER)).getData().size();

        Assert.assertEquals(true, associationMemberManageService.removeOneFromDepartment(associationId, 2, userId));

        int aftersize = ps.getRoles((long) 3, roleName("a", 2)).getData().size();

        Assert.assertEquals(beforesize, aftersize + 1);
    }

    @Test
    public void addOneToDepartment() {

        Assert.assertEquals(true, associationMemberManageService.addOneToDepartment("a", 2, 6));

        ps.removeUser((long) 6, scope("a"));
    }

    @Test
    public void checkUserPerm() {

        associationMemberManageService.addOneToDepartment("a", 2, 10);

        ps.createPermission(new Permission("asso_member_test_write"));
        ps.grantPermissions(roleName("a", 2), Arrays.asList("asso_member_test_write"));


        Assert.assertEquals(true, associationMemberManageService.checkUserPerm(10, "a", "asso_member_test_write"));
        ps.removeUser((long) 10, scope("a"));
        ps.deletePermission("asso_member_test_write");
    }

    @Test
    public void getUserAssociation() {
        associationMemberManageService.addOneToDepartment("a", 2, 10);

        List<String> b = associationMemberManageService.getUserAssociation(ps.getScopes((long) 10).getData());

        Assert.assertEquals(2, b.size());

        ps.removeUser((long) 10, scope("a"));
    }

    @Test
    public void getUserDepartment() {
        associationMemberManageService.addOneToDepartment("a", 2, 10);

        List<Department> departments = associationMemberManageService.getUserDepartment("a", 10);
        Assert.assertEquals(1, departments.size());

        ps.removeUser((long) 10, scope("a"));
    }

    @Test
    public void deleteOneInAssociation() {
        associationMemberManageService.addOneToDepartment("a", 2, 10);

        long before = ps.getUsers(scope("a")).getData().getTotalSize();

        associationMemberManageService.deleteOneInAssociation("a", 10);
        long after = ps.getUsers(scope("a")).getData().getTotalSize();
        Assert.assertEquals(after, before - 1);

    }

    @Test
    public void addOneToAssociation() {
        long before = ps.getUsers(scope("a")).getData().getTotalSize();

        associationMemberManageService.addOneToDepartment("a", 2, 11);

        long after = ps.getUsers(scope("a")).getData().getTotalSize();

        associationMemberManageService.deleteOneInAssociation("a", 11);

        Assert.assertEquals(after, before + 1);
    }

    @Test
    public void getAssoUserList() {

        associationMemberManageService.initDepartment("b", 10);

        ps.grantRoles((long) 3, scope("b"), Arrays.asList(roleName("b", 2)));

        List<Integer> list = associationMemberManageService.getAssoUserList("b");

        Assert.assertEquals(3, list.size());
    }

    @Test
    public void initDepartment() {
//        int ran = (int) (Math.random() * 100 + 10);
        String ran = "a";

        Boolean b = associationMemberManageService.initDepartment(ran, 3);

        Assert.assertEquals(true, departmentRepository.existsByAssociationIdAndDepartmentId(ran, 1));
        Assert.assertEquals(true, departmentRepository.existsByAssociationIdAndDepartmentId(ran, 2));
    }
}