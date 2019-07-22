package exort.association_member_manager.api_server;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.api.http.review.entity.CallbackParam;
import exort.association_member_manager.entity.Department;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ApiServer {
    public ApiResponse<Boolean> adoptApplication(CallbackParam<ApplicationDepartmentInfo> callbackParam);

    public ApiResponse<List<Department>> getDepartmentTree(int associationId);

    public ApiResponse<Department> getSpecDepartmentInfo(int associationId, int departmentId);

    public ApiResponse<Department> createDepartment(int associationId, DepartmentInfo departmentInfo);

    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId);

    public ApiResponse<Department> editDepartment(int associationId, int departmentId, DepartmentInfo departmentInfo);

    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId);

    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId);

    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId);

    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission);

    public ApiResponse<List<Integer>> getUserAssociation(int userId);

    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId);

    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId);

    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId);

    public ApiResponse<List<Integer>> getAssoUserList(int associationId);

    public ApiResponse<Boolean> initDepartment(InitAssociationInfo initAssociationInfo);
}
