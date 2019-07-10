package com.exort.association_member_management.service;


import com.exort.association_member_management.entity.Application;
import com.exort.association_member_management.entity.Department;

import java.util.Date;
import java.util.List;

public interface ApplicationService {
    public Application getSpecApplication(int applyId);
    public List<Application> getSomeApplications(int userId, int associationId, int departmentId, Date startTime, Date endTime, int numInOnePage);
    public boolean adoptApplication(int applyId);
    public boolean refuseApplication(int applyId);
    public List<Department> getDepartmentTree(int associationId);
    public Department getSpecDepartmentInfo(int associationId,int departmentId);
    public boolean createDepartment(int associationId,String departmentName,String departmentDesc,int parentId);
    public boolean deleteDepartment(int associationId,int departmentId);
    public boolean editDepartment(int departmentId,String departmentName,String departmentDesc,int parentId);
    public List<Integer> getSpecMemberList(int associationId,int departmentId,int numInOnePage);
    public boolean removeOneFromDepartment(int associationId,int departmentId,int userId);
    public boolean addOneToDepartment(int associationId,int departmentId,int userId);
    public boolean changeOneToDepartment(int associationId,int directionDepartmentId,int userId);
    public boolean checkUserPermissionInAssociation(int associationId,int userId,int permission);
    public List<Integer> getUserAssociation(int userId);
    public List<Department> getUserDepartment(int associationId,int userId);
    public boolean deleteOneInAssociation(int associationId,int userId);
    public boolean addOneToAssociation(int assciationId,int userId);

}
