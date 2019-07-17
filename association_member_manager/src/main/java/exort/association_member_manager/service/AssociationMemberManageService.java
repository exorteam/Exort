package exort.association_member_manager.service;


import exort.association_member_manager.dto.ResponseCode;
import exort.association_member_manager.entity.Department;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AssociationMemberManageService {
    public ResponseCode getSpecApplication(int applyId);

    public ResponseCode getSomeApplications(Optional<Integer> userId, Optional<Integer> associationId, Optional<Integer> departmentId, Optional<Date> startTime, Optional<Date> endTime, int page, int size);

    public ResponseCode adoptApplication(int applyId);

    public ResponseCode refuseApplication(int applyId);

    public ResponseCode<List<Department>> getDepartmentTree(int associationId, HttpServletResponse response);

    public ResponseCode<Department> getSpecDepartmentInfo(int associationId, int departmentId, HttpServletResponse response);

    public ResponseCode<Department> createDepartment(int associationId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response);

    public ResponseCode<Department> deleteDepartment(int associationId, int departmentId, HttpServletResponse response);

    public ResponseCode<Department> editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response);

    public ResponseCode<List<Integer>> getSpecMemberList(int associationId, int departmentId, HttpServletResponse response);

    public ResponseCode<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId, HttpServletResponse response);

    public ResponseCode<Boolean> addOneToDepartment(int associationId, int departmentId, int userId, HttpServletResponse response);

//    public ResponseCode changeOneToDepartment(int associationId, int directionDepartmentId, int userId, HttpServletResponse response);

    public ResponseCode<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission, HttpServletResponse response);

    public ResponseCode<List<Integer>> getUserAssociation(int userId, HttpServletResponse response);

    public ResponseCode<List<Department>> getUserDepartment(int associationId, int userId, HttpServletResponse response);

    public ResponseCode<Boolean> deleteOneInAssociation(int associationId, int userId, HttpServletResponse response);

    public ResponseCode<Boolean> addOneToAssociation(int associationId, int userId, HttpServletResponse response);

    public ResponseCode<List<Integer>> getAssoUserList(int associationId, HttpServletResponse response);

    public ResponseCode<Boolean> initDepartment(int associationId, int userId, HttpServletResponse response);
}
