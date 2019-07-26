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
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociationMemberManageServiceImplTest {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private PermService ps;

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    private String roleName(int associationId, int departmentId) {
        switch (departmentId) {
            case 1:
                return MANAGER;
            case 2:
                return MEMBER;
            default:
                return "association_" + associationId + "_" + departmentId;
        }
    }

    private String scope(int associationId) {
        return "association_" + associationId;
    }

    @Before
    public void setUp() {
        associationMemberManageService.initDepartment(1, 1);
        ps.deletePermission("asso_member_test_write");
    }

    @After
    public void afterAll() {
        departmentRepository.deleteAll();
        associationMemberManageService.initDepartment(1, 1);
    }

    @Test
    public void adoptApplication() {

        ApplicationDepartmentInfo applicationDepartmentInfo = new ApplicationDepartmentInfo();
        applicationDepartmentInfo.setAssociationId(1);
        applicationDepartmentInfo.setDepartmentId(2);
        Application<ApplicationDepartmentInfo> application = new Application<>(3L, "acceptApplication", applicationDepartmentInfo);

        associationMemberManageService.adoptApplication(3, "create", application);

        Assert.assertEquals(true, associationMemberManageService.adoptApplication(3, "create", application));

        // 删去创建的用户
        associationMemberManageService.deleteOneInAssociation(1, 3);
    }

    @Test
    public void getDepartmentTree() {
        associationMemberManageService.initDepartment(2, 2);

        associationMemberManageService.getDepartmentTree(2);

        Assert.assertEquals(2, associationMemberManageService.getDepartmentTree(2).size());

    }

    @Test
    public void getSpecDepartmentInfo() {
        associationMemberManageService.initDepartment(2, 2);
        Department departmentInfo = associationMemberManageService.getSpecDepartmentInfo(2, 1);
        Assert.assertEquals("管理层", departmentInfo.getName());
    }

    @Test
    public void createDepartment() {
        Department department = associationMemberManageService.createDepartment(1, "hello", "hello try", 0);
        Assert.assertEquals("hello", department.getName());

        // 删除新创建的role
        ps.deleteRole(roleName(1, department.getDepartmentId()));
    }

    @Test
    public void deleteDepartment() {
        Department createDepartment = associationMemberManageService.createDepartment(1, "hello", "das", 1);

        Department department = associationMemberManageService.deleteDepartment(createDepartment.getAssociationId(), createDepartment.getDepartmentId());

        Assert.assertEquals(3, department.getDepartmentId());

        // 删除新创建的role
        ps.deleteRole(roleName(1, department.getDepartmentId()));
    }

    @Test
    public void editDepartment() {
        Department createDepartment = associationMemberManageService.createDepartment(1, "hello", "das", 1);

        Department department = associationMemberManageService.editDepartment(createDepartment.getAssociationId(), createDepartment.getDepartmentId(), "black", "back", 0);

        Assert.assertEquals("black", department.getName());

        // 删除新创建的role
        ps.deleteRole(roleName(1, department.getDepartmentId()));
    }

    @Test
    public void getSpecMemberList() {
        Department department = associationMemberManageService.createDepartment(1, "asd", "asd", 1);
        associationMemberManageService.addOneToDepartment(1, department.getDepartmentId(), 1);
        associationMemberManageService.addOneToDepartment(1, department.getDepartmentId(), 2);
        associationMemberManageService.addOneToDepartment(1, department.getDepartmentId(), 3);
        associationMemberManageService.addOneToDepartment(1, department.getDepartmentId(), 4);
        associationMemberManageService.addOneToDepartment(1, department.getDepartmentId(), 5);

        Assert.assertEquals(5, associationMemberManageService.getSpecMemberList(1, department.getDepartmentId()).size());

        // 删除role
        ps.deleteRole(roleName(department.getAssociationId(), department.getDepartmentId()));
    }

    @Test
    public void removeOneFromDepartment() {

        int userId = 3, associationId = 1;
        int beforesize = ps.grantRoles(Long.valueOf(userId), scope(associationId), Arrays.asList(MEMBER)).getData().size();

        Assert.assertEquals(true, associationMemberManageService.removeOneFromDepartment(associationId, 2, userId));

        int aftersize = ps.getRoles((long) 3, roleName(1, 2)).getData().size();

        Assert.assertEquals(beforesize, aftersize + 1);
    }

    @Test
    public void addOneToDepartment() {

        Assert.assertEquals(true, associationMemberManageService.addOneToDepartment(1, 2, 6));

        ps.removeUser((long) 6, scope(1));
    }

    @Test
    public void checkUserPerm() {

        associationMemberManageService.addOneToDepartment(1, 2, 10);

        ps.createPermission(new Permission("asso_member_test_write"));
        ps.grantPermissions(roleName(1, 2), Arrays.asList("asso_member_test_write"));


        Assert.assertEquals(true, associationMemberManageService.checkUserPerm(10, 1, "asso_member_test_write"));
        ps.removeUser((long) 10, scope(1));
        ps.deletePermission("asso_member_test_write");
    }

    @Test
    public void getUserAssociation() {
        associationMemberManageService.addOneToDepartment(1, 2, 10);

        List<Integer> b = associationMemberManageService.getUserAssociation(ps.getScopes((long) 10).getData());

        Assert.assertEquals(1, b.size());


        ps.removeUser((long) 10, scope(1));
    }

    @Test
    public void getUserDepartment() {
        associationMemberManageService.addOneToDepartment(1, 2, 10);

        List<Department> departments = associationMemberManageService.getUserDepartment(1, 10);
        Assert.assertEquals(1, departments.size());

        ps.removeUser((long) 10, scope(1));
    }

    @Test
    public void deleteOneInAssociation() {
        associationMemberManageService.addOneToDepartment(1, 2, 10);

        long before = ps.getUsers(scope(1)).getData().getTotalSize();

        associationMemberManageService.deleteOneInAssociation(1, 10);
        long after = ps.getUsers(scope(1)).getData().getTotalSize();
        Assert.assertEquals(after, before - 1);

    }

    @Test
    public void addOneToAssociation() {
        long before = ps.getUsers(scope(1)).getData().getTotalSize();

        associationMemberManageService.addOneToDepartment(1, 2, 10);

        long after = ps.getUsers(scope(1)).getData().getTotalSize();

        associationMemberManageService.deleteOneInAssociation(1, 10);

        Assert.assertEquals(after, before + 1);
    }

    @Test
    public void getAssoUserList() {

        associationMemberManageService.initDepartment(2, 10);

        ps.grantRoles((long) 3, scope(2), Arrays.asList(roleName(2, 2)));

        List<Integer> list = associationMemberManageService.getAssoUserList(2);

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void initDepartment() {
//        int ran = (int) (Math.random() * 100 + 10);
        int ran = 1;

        Boolean b = associationMemberManageService.initDepartment(ran, 3);

        Assert.assertEquals(true, departmentRepository.existsByAssociationIdAndDepartmentId(ran, 1));
        Assert.assertEquals(true, departmentRepository.existsByAssociationIdAndDepartmentId(ran, 2));
    }
}