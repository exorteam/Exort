package exort.association_member_manager.service;

import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;

import java.util.List;

public interface AssociationMemberManageService {

    public List<Department> findDepartmentList(String associationId);

    public Department findDepartment(String associationId, int departmentId);

    public Boolean checkUserInAsso(long userId, String associationId);

    public Boolean checkUserInAsso(int userId, String associationId);

    public Boolean checkAsso(String associationId);

    public Boolean checkDepartment(String associationId, int departmentId);

    public List<Department> getDepartmentTree(String associationId);

    public Department getSpecDepartmentInfo(String associationId, int departmentId);

    public Department createDepartment(String associationId, String departmentName, String departmentDesc, int parentId);

    public Department deleteDepartment(String associationId, int departmentId);

    public Department editDepartment(String associationId, int departmentId, String departmentName, String departmentDesc, int parentId);

    public List<Integer> getSpecMemberList(String associationId, int departmentId);

    public Boolean removeOneFromDepartment(String associationId, int departmentId, int userId);

    public Boolean addOneToDepartment(String associationId, int departmentId, int userId);

    public boolean checkUserPerm(int userId, String associationId, String permission);

    public List<String> getUserAssociation(List<String> assos);

    public List<Department> getUserDepartment(String associationId, int userId);

    public Boolean deleteOneInAssociation(String associationId, int userId);

    public Boolean addOneToAssociation(String associationId, int userId);

    public List<Integer> getAssoUserList(String associationId);

    public Boolean initDepartment(String associationId, int userId);

    public Boolean deleteAllDepartments(String associationId);
}
