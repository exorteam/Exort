package exort.api.http.member.service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.member.entity.UserId;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.api.http.review.entity.CallbackParam;

import java.util.List;

public interface AssoMemService {
    //    通过申请
    public ApiResponse<Boolean> adoptApplication(CallbackParam<ApplicationDepartmentInfo> callbackParam);

    //    得到部门树
    public ApiResponse<List<DepartmentInfo>> getDepartmentTree(String associationId);

    //    得到某个部门的具体信息
    public ApiResponse<DepartmentInfo> getSpecDepartmentInfo(String associationId, int departmentId);

    //    在某个社团中创建部门
    public ApiResponse<DepartmentInfo> createDepartment(String associationId, DepartmentInfo departmentInfo);

    //    删除部门
    public ApiResponse<DepartmentInfo> deleteDepartment(String associationId, int departmentId);

    //    编辑部门信息
    public ApiResponse<DepartmentInfo> editDepartment(String associationId, int departmentId, DepartmentInfo departmentInfo);

    //    得到部门成员列表
    public ApiResponse<List<Integer>> getSpecMemberList(String associationId, int departmentId);

    //    从部门中删去某个成员
    public ApiResponse<Boolean> removeOneFromDepartment(String associationId, int departmentId, int userId);

    //    向部门中添加某个成员
    public ApiResponse<Boolean> addOneToDepartment(String associationId, int departmentId, UserId userId);

    //    得到用户所有的社团列表
    public ApiResponse<List<Integer>> getUserAssociation(int userId);

    //    得到用户所有的部门列表
    public ApiResponse<List<DepartmentInfo>> getUserDepartment(String associationId, int userId);

    //    从社团移除某个用户
    public ApiResponse<Boolean> deleteOneInAssociation(String associationId, int userId);

    //    向社团添加某个用户
    public ApiResponse<Boolean> addOneToAssociation(String associationId, UserId userId);

    //    得到社团的成员列表
    public ApiResponse<List<Integer>> getAssoUserList(String associationId);

    //    初始化部门
    public ApiResponse<Boolean> initDepartment(InitAssociationInfo initAssociationInfo);

    //    [associationId => scope]
    public String scope(String associationId);

    //    [associationId,departmentId => roleName]
    public String roleName(String associationId, int departmentId);

}
