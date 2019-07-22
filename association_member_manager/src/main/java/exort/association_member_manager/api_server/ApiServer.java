package exort.association_member_manager.api_server;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ApiServer {
    public ApiResponse<Boolean> adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application, HttpServletResponse response);

    public ApiResponse<List<Department>> getDepartmentTree(int associationId, HttpServletResponse response);

    public ApiResponse<Department> getSpecDepartmentInfo(int associationId, int departmentId, HttpServletResponse response);

    public ApiResponse<Department> createDepartment(int associationId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response);

    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId, HttpServletResponse response);

    public ApiResponse<Department> editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response);

    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId, HttpServletResponse response);

    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId, HttpServletResponse response);

    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId, HttpServletResponse response);

    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission, HttpServletResponse response);

    public ApiResponse<List<Integer>> getUserAssociation(int userId, HttpServletResponse response);

    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId, HttpServletResponse response);

    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId, HttpServletResponse response);

    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId, HttpServletResponse response);

    public ApiResponse<List<Integer>> getAssoUserList(int associationId, HttpServletResponse response);

    public ApiResponse<Boolean> initDepartment(int associationId, int userId, HttpServletResponse response);
}
