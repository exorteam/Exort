package exort.association_member_manager.serviceimpl;

import com.google.common.collect.Lists;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
import io.swagger.models.auth.In;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociationMemberManageServiceImplTest {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    private MockHttpServletResponse response;

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

        response = new MockHttpServletResponse();
    }


    @Test
    public void adoptApplication() {

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));


        Application<ApplicationDepartmentInfo> application = new Application<>();
        application.setId(123L);
        application.setApplicantId(5L);
        application.setType("acceptApplication");
        ApplicationDepartmentInfo applicationDepartmentInfo = new ApplicationDepartmentInfo();
        applicationDepartmentInfo.setAssociationId(1);
        applicationDepartmentInfo.setDepartmentId(0);
        application.setObject(applicationDepartmentInfo);

        associationMemberManageService.adoptApplication(3, "create", application);

        Assert.assertEquals(true, associationMemberManageService.adoptApplication(3, "create", application));
    }

    @Test
    public void getDepartmentTree() {

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        associationMemberManageService.getDepartmentTree(1);

        Assert.assertEquals(departmentRepository.findAllByAssociationId(1), associationMemberManageService.getDepartmentTree(1));

    }

    @Test
    public void getSpecDepartmentInfo() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        Department departmentInfo = associationMemberManageService.getSpecDepartmentInfo(1, 0);
        Assert.assertEquals(1, departmentInfo.getAssociationId());
    }

    @Test
    public void createDepartment() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));

        Department department = associationMemberManageService.createDepartment(1, "hello", "hello try", 0);
        Assert.assertEquals("hello", department.getName());

    }

    @Test
    public void deleteDepartment() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));
        departmentRepository.save(new Department(1, 2, "hello", "das", 0));
        departmentRepository.save(new Department(1, 3, "hello", "das", 0));

        Department department = associationMemberManageService.deleteDepartment(1, 3);
        Assert.assertEquals(3, department.getDepartmentId());
    }

    @Test
    public void editDepartment() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        Department department = associationMemberManageService.editDepartment(1, 0, "black", "back", 0);

        Assert.assertEquals("black", department.getName());
    }

    @Test
    public void getSpecMemberList() {

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        Assert.assertEquals(new ArrayList<>(), associationMemberManageService.getSpecMemberList(1, 0));

    }

    @Test
    public void removeOneFromDepartment() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));
        departmentRepository.save(new Department(1, 2, "hello", "das", 0));

        int userId = 3, associationId = 1;
        ps.grantRoles(Long.valueOf(userId), scope(associationId), Arrays.asList(MANAGER, MEMBER));

        Assert.assertEquals(true, associationMemberManageService.removeOneFromDepartment(associationId, 2, userId));

    }

    @Test
    public void addOneToDepartment() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));


        Assert.assertEquals(true, associationMemberManageService.addOneToDepartment(1, 0, 1));
    }

    @Test
    public void checkUserPermissionInAssociation() {

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));


        Assert.assertEquals(true, associationMemberManageService.addOneToDepartment(1, 0, 1));

    }

    @Test
    public void getUserAssociation() {

        List<Integer> b = associationMemberManageService.getUserAssociation(ps.getScopes((long) 2).getData());
        List<Integer> ret = new ArrayList<>();
        ret.add(1);

        Assert.assertEquals(ret, b);

    }

    @Test
    public void getUserDepartment() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));
        departmentRepository.save(new Department(1, 2, "hello", "das", 0));

        ps.grantRoles((long) 2, scope(1), Arrays.asList(MEMBER, MANAGER));

        List<Department> departments = associationMemberManageService.getUserDepartment(1, 2);
        Assert.assertEquals(2, departments.size());

    }

    @Test
    public void deleteOneInAssociation() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        Boolean b = associationMemberManageService.deleteOneInAssociation(1, 2);
        Assert.assertEquals(true, b);

    }

    @Test
    public void addOneToAssociation() {

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        Boolean b = associationMemberManageService.addOneToAssociation(1, 2);
        Assert.assertEquals(true, b);
    }

    @Test
    public void getAssoUserList() {
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        List<Integer> list = associationMemberManageService.getAssoUserList(1);
        List<Integer> ret = new ArrayList<>();
        ret.add(1);
        ret.add(2);
        ret.add(3);

        Assert.assertEquals(ret, list);
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