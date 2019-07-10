package com.exort.association_member_management.serviceimpl;

import com.exort.association_member_management.entity.Application;
import com.exort.association_member_management.entity.Department;
import com.exort.association_member_management.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public Application getSpecApplication(int applyId) {
        return null;
    }

    @Override
    public List<Application> getSomeApplications(int userId, int associationId, int departmentId, Date startTime, Date endTime, int numInOnePage) {
        return null;
    }

    @Override
    public boolean adoptApplication(int applyId) {
        return false;
    }

    @Override
    public boolean refuseApplication(int applyId) {
        return false;
    }

    @Override
    public List<Department> getDepartmentTree(int associationId) {
        return null;
    }

    @Override
    public Department getSpecDepartmentInfo(int associationId, int departmentId) {
        return null;
    }

    @Override
    public boolean createDepartment(int associationId, String departmentName, String departmentDesc, int parentId) {
        return false;
    }

    @Override
    public boolean deleteDepartment(int associationId, int departmentId) {
        return false;
    }

    @Override
    public boolean editDepartment(int departmentId, String departmentName, String departmentDesc, int parentId) {
        return false;
    }

    @Override
    public List<Integer> getSpecMemberList(int associationId, int departmentId, int numInOnePage) {
        return null;
    }

    @Override
    public boolean removeOneFromDepartment(int associationId, int departmentId, int userId) {
        return false;
    }

    @Override
    public boolean addOneToDepartment(int associationId, int departmentId, int userId) {
        return false;
    }

    @Override
    public boolean changeOneToDepartment(int associationId, int directionDepartmentId, int userId) {
        return false;
    }

    @Override
    public boolean checkUserPermissionInAssociation(int associationId, int userId, int permission) {
        return false;
    }

    @Override
    public List<Integer> getUserAssociation(int userId) {
        return null;
    }

    @Override
    public List<Department> getUserDepartment(int associationId, int userId) {
        return null;
    }

    @Override
    public boolean deleteOneInAssociation(int associationId, int userId) {
        return false;
    }

    @Override
    public boolean addOneToAssociation(int assciationId, int userId) {
        return false;
    }
}
