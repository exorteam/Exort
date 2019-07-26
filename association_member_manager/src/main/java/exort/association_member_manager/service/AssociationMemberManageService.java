package exort.association_member_manager.service;

import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;

import java.util.List;

public interface AssociationMemberManageService {

    public List<Department> findDepartmentList(int associationId);

    public Department findDepartment(int associationId, int departmentId);

    public Boolean checkUserInAsso(long userId, int associationId);

    public Boolean checkUserInAsso(int userId, int associationId);

    public Boolean checkAsso(int associationId);

    public Boolean checkDepartment(int associationId, int departmentId);

    public Boolean adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application);

    public List<Department> getDepartmentTree(int associationId);

    public Department getSpecDepartmentInfo(int associationId, int departmentId);

    public Department createDepartment(int associationId, String departmentName, String departmentDesc, int parentId);

    public Department deleteDepartment(int associationId, int departmentId);

    public Department editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId);

    public List<Integer> getSpecMemberList(int associationId, int departmentId);

    public Boolean removeOneFromDepartment(int associationId, int departmentId, int userId);

    public Boolean addOneToDepartment(int associationId, int departmentId, int userId);

    public boolean checkUserPerm(int userId,int associationId,String permission);

    public List<Integer> getUserAssociation(List<String> assos);

    public List<Department> getUserDepartment(int associationId, int userId);

    public Boolean deleteOneInAssociation(int associationId, int userId);

    public Boolean addOneToAssociation(int associationId, int userId);

    public List<Integer> getAssoUserList(int associationId);

    public Boolean initDepartment(int associationId, int userId);
}
