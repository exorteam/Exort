package exort.association_member_manager.serviceimpl;

import exort.api.http.entity.Application;
import exort.api.http.entity.ApplicationDepartmentInfo;
import exort.api.http.entity.ResponseCode;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociationMemberManageServiceImplTest {
    @Autowired
    private AssociationMemberManageService associationMemberManageService;

    private MockHttpServletResponse response;

    @Autowired
    DepartmentRepository departmentRepository;

    @Before
    public void setUp() {

        response = new MockHttpServletResponse();
    }


    @Test
    public void adoptApplication() {

        ResponseCode<Boolean> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));


        Application<ApplicationDepartmentInfo> application = new Application<>();
        application.setId(123);
        application.setApplicantId(2);
        application.setType("acceptApplication");
        ApplicationDepartmentInfo applicationDepartmentInfo = new ApplicationDepartmentInfo();
        applicationDepartmentInfo.setAssociationId(1);
        applicationDepartmentInfo.setDepartmentId(0);
        application.setObject(applicationDepartmentInfo);

        responseCode = associationMemberManageService.adoptApplication(1, "create", application, response);

        Assert.assertEquals(response.getStatus(), 200);
        Assert.assertEquals(responseCode.getData(), true);
    }

    @Test
    public void getDepartmentTree() {
        ResponseCode<List<Department>> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        associationMemberManageService.getDepartmentTree(1, response);
        Assert.assertEquals(200, response.getStatus());

        responseCode = associationMemberManageService.getDepartmentTree(10, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

    }

    @Test
    public void getSpecDepartmentInfo() {
        ResponseCode<Department> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.getSpecDepartmentInfo(2, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", responseCode.getError());

        responseCode = associationMemberManageService.getSpecDepartmentInfo(1, 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(1, responseCode.getData().getAssociationId());
    }

    @Test
    public void createDepartment() {
        ResponseCode<Department> responseCode;
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.createDepartment(3, "hello", "asd", 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

        responseCode = associationMemberManageService.createDepartment(1, "hello", "asd", 20, response);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("InvalidDepartment", responseCode.getError());

        responseCode = associationMemberManageService.createDepartment(1, "hello", "hello try", 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("hello", responseCode.getData().getName());

    }

    @Test
    public void deleteDepartment() {
        ResponseCode<Department> responseCode;
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.deleteDepartment(1, 10, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", responseCode.getError());

        responseCode = associationMemberManageService.deleteDepartment(1, 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(0, responseCode.getData().getDepartmentId());
    }

    @Test
    public void editDepartment() {
        ResponseCode<Department> responseCode;
        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.editDepartment(1, 0, "black", "back", 200, response);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("InvalidParentId", responseCode.getError());

        responseCode = associationMemberManageService.editDepartment(1, 66, "black", "back", 1, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", responseCode.getError());

        responseCode = associationMemberManageService.editDepartment(1, 0, "black", "back", 0, response);


        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("black", responseCode.getData().getName());
    }

    @Test
    public void getSpecMemberList() {
        ResponseCode<List<Integer>> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.getSpecMemberList(2, 3, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", responseCode.getError());

        responseCode = associationMemberManageService.getSpecMemberList(1, 0, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(new ArrayList<>(), responseCode.getData());

    }

    @Test
    public void removeOneFromDepartment() {
        ResponseCode<Boolean> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.removeOneFromDepartment(1, 8, 1, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", responseCode.getError());

        associationMemberManageService.removeOneFromDepartment(1, 0, 1, response);
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void addOneToDepartment() {
        ResponseCode<Boolean> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.addOneToDepartment(1, 8, 1, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("DepartmentNotFound", responseCode.getError());

        associationMemberManageService.addOneToDepartment(1, 0, 1, response);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void checkUserPermissionInAssociation() {
        ResponseCode<Boolean> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.checkUserPermissionInAssociation(9, 1, "write", response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

        associationMemberManageService.addOneToDepartment(1, 0, 1, response);
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void getUserAssociation() {
        ResponseCode<List<Integer>> responseCode;

        responseCode = associationMemberManageService.getUserAssociation(2, response);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(new ArrayList<>(), responseCode.getData());

    }

    @Test
    public void getUserDepartment() {
        ResponseCode<List<Department>> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.getUserDepartment(3, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

        responseCode = associationMemberManageService.getUserDepartment(1, 2, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(new ArrayList<>(), responseCode.getData());

    }

    @Test
    public void deleteOneInAssociation() {
        ResponseCode<Boolean> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.deleteOneInAssociation(3, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

        responseCode = associationMemberManageService.deleteOneInAssociation(1, 2, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(true, responseCode.getData());

    }

    @Test
    public void addOneToAssociation() {
        ResponseCode<Boolean> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.addOneToAssociation(3, 2, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

        responseCode = associationMemberManageService.addOneToAssociation(1, 2, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(true, responseCode.getData());
    }

    @Test
    public void getAssoUserList() {
        ResponseCode<List<Integer>> responseCode;

        departmentRepository.deleteAll();
        departmentRepository.save(new Department(1,"hello","das",0));

        responseCode = associationMemberManageService.getAssoUserList(3, response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("AssociationNotFound", responseCode.getError());

        responseCode = associationMemberManageService.getAssoUserList(1, response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(new ArrayList<>(), responseCode.getData());
    }

    @Test
    public void initDepartment() {
        ResponseCode<Boolean> responseCode;
        int ran=(int)(Math.random()*100+10);

        responseCode=associationMemberManageService.initDepartment(ran,3,response);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(true, responseCode.getData());
    }
}