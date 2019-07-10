package com.exort.association_member_management.service;


import com.exort.association_member_management.dto.ResponseCode;

import java.util.Date;
import java.util.Optional;

public interface AssociationMemberManageService {
    public ResponseCode getSpecApplication(int applyId);
    public ResponseCode getSomeApplications(Optional<Integer> userId, Optional<Integer> associationId, Optional<Integer> departmentId, Optional<Date> startTime, Optional<Date> endTime,int page,int size);
    public ResponseCode adoptApplication(int applyId);
    public ResponseCode refuseApplication(int applyId);
    public ResponseCode getDepartmentTree(int associationId);
    public ResponseCode getSpecDepartmentInfo(int associationId,int departmentId);
    public ResponseCode createDepartment(int associationId,String departmentName,String departmentDesc,int parentId);
    public ResponseCode deleteDepartment(int associationId,int departmentId);
    public ResponseCode editDepartment(int departmentId,String departmentName,String departmentDesc,int parentId);
    public ResponseCode getSpecMemberList(int associationId,int departmentId,int numInOnePage);
    public ResponseCode removeOneFromDepartment(int associationId,int departmentId,int userId);
    public ResponseCode addOneToDepartment(int associationId,int departmentId,int userId);
    public ResponseCode changeOneToDepartment(int associationId,int directionDepartmentId,int userId);
    public ResponseCode checkUserPermissionInAssociation(int associationId,int userId,int permission);
    public ResponseCode getUserAssociation(int userId);
    public ResponseCode getUserDepartment(int associationId,int userId);
    public ResponseCode deleteOneInAssociation(int associationId,int userId);
    public ResponseCode addOneToAssociation(int associationId,int userId);

}
