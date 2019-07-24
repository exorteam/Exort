package exort.association_member_manager.serviceimpl;

import com.google.common.collect.Lists;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
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

        ApiResponse<Boolean> ApiResponse;

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

        ApiResponse = associationMemberManageService.adoptApplication(3, "create", application, response);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(ApiResponse.getData(), true);
    }

    @Test
    public void getDepartmentTree() {
        ApiResponse<List<Department>> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        associationMemberManageService.getDepartmentTree(1, response);
        Assert.assertEquals(200, response.getStatus());

        ApiResponse = associationMemberManageService.getDepartmentTree(10, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

    }

    @Test
    public void getSpecDepartmentInfo() {
        ApiResponse<Department> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.getSpecDepartmentInfo(2, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.getSpecDepartmentInfo(1, 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(1, ApiResponse.getData().getAssociationId());
    }

    @Test
    public void createDepartment() {
        ApiResponse<Department> ApiResponse;
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.createDepartment(3, "hello", "asd", 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.createDepartment(1, "hello", "asd", 20, response);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("InvalidDepartment", ApiResponse.getError());

        ApiResponse = associationMemberManageService.createDepartment(1, "hello", "hello try", 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("hello", ApiResponse.getData().getName());

    }

    @Test
    public void deleteDepartment() {
        ApiResponse<Department> ApiResponse;
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));
        departmentRepository.save(new Department(1, 2, "hello", "das", 0));
        departmentRepository.save(new Department(1, 3, "hello", "das", 0));

        ApiResponse = associationMemberManageService.deleteDepartment(1, 10, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.deleteDepartment(1, 3, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(3, ApiResponse.getData().getDepartmentId());
    }

    @Test
    public void editDepartment() {
        ApiResponse<Department> ApiResponse;
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.editDepartment(1, 0, "black", "back", 200, response);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("InvalidParentId", ApiResponse.getError());

        ApiResponse = associationMemberManageService.editDepartment(1, 66, "black", "back", 1, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.editDepartment(1, 0, "black", "back", 0, response);


        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("black", ApiResponse.getData().getName());
    }

    @Test
    public void getSpecMemberList() {
        ApiResponse<List<Integer>> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.getSpecMemberList(2, 3, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.getSpecMemberList(1, 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(new ArrayList<>(), ApiResponse.getData());

    }

    @Test
    public void removeOneFromDepartment() {
        ApiResponse<Boolean> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));
        departmentRepository.save(new Department(1, 2, "hello", "das", 0));

        int userId = 3, associationId = 1;
        ps.grantRoles(Long.valueOf(userId), scope(associationId), Arrays.asList(MANAGER, MEMBER));

        ApiResponse = associationMemberManageService.removeOneFromDepartment(associationId, 8, userId, response);
        Assert.assertEquals(401, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", ApiResponse.getError());

        associationMemberManageService.removeOneFromDepartment(associationId, 2, userId, response);
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void addOneToDepartment() {
        ApiResponse<Boolean> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.addOneToDepartment(1, 8, 1, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", ApiResponse.getError());

        associationMemberManageService.addOneToDepartment(1, 0, 1, response);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void checkUserPermissionInAssociation() {
        ApiResponse<Boolean> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.checkUserPermissionInAssociation(9, 1, "write", response);
        Assert.assertEquals(401, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

        associationMemberManageService.addOneToDepartment(1, 0, 1, response);
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void getUserAssociation() {
        ApiResponse<List<Integer>> ApiResponse;

        ApiResponse = associationMemberManageService.getUserAssociation(2, response);

        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void getUserDepartment() {
        ApiResponse<List<Department>> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, 1, "hello", "das", 0));

        ps.grantRoles((long)2,scope(1),Arrays.asList(MEMBER,MANAGER));


        ApiResponse = associationMemberManageService.getUserDepartment(3, 2, response);
        Assert.assertEquals(401, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

        ps.grantRoles((long)2,scope(1),Arrays.asList(MEMBER));
        ApiResponse = associationMemberManageService.getUserDepartment(1, 2, response);
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void deleteOneInAssociation() {
        ApiResponse<Boolean> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.deleteOneInAssociation(3, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.deleteOneInAssociation(1, 2, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(true, ApiResponse.getData());

    }

    @Test
    public void addOneToAssociation() {
        ApiResponse<Boolean> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.addOneToAssociation(3, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.addOneToAssociation(1, 2, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(true, ApiResponse.getData());
    }

    @Test
    public void getAssoUserList() {
        ApiResponse<List<Integer>> ApiResponse;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1, "hello", "das", 0));

        ApiResponse = associationMemberManageService.getAssoUserList(3, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", ApiResponse.getError());

        ApiResponse = associationMemberManageService.getAssoUserList(1, response);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void initDepartment() {
        ApiResponse<Boolean> ApiResponse;
        int ran = (int) (Math.random() * 100 + 10);

        ApiResponse = associationMemberManageService.initDepartment(ran, 3, response);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(true, ApiResponse.getData());
    }
}