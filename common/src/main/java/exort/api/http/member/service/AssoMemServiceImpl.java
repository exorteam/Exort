package exort.api.http.member.service;

import com.google.common.reflect.TypeToken;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.member.entity.UserId;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.api.http.review.entity.CallbackParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssoMemServiceImpl extends RestTemplate implements AssoMemService {

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    @Value("${exort.mem.protocol:http}")
    public void setProtocol(String protocol) {
        super.setProtocol(protocol);
    }

    @Value("${exort.mem.endpoint:localhost}")
    public void setEndpoint(String endpoint) {
        super.setEndpoint(endpoint);
    }


    @Override
    public ApiResponse<Boolean> adoptApplication(CallbackParam<ApplicationDepartmentInfo> callbackParam) {
        return request(new TypeToken<Boolean>() {
        }, callbackParam, HttpMethod.POST, "/application/accept");
    }

    @Override
    public ApiResponse<List<DepartmentInfo>> getDepartmentTree(String associationId) {
        return request(new TypeToken<List<DepartmentInfo>>() {
        }, HttpMethod.GET, "/associations/{associationId}/departments", associationId);
    }

    @Override
    public ApiResponse<DepartmentInfo> getSpecDepartmentInfo(String associationId, int departmentId) {
        return request(new TypeToken<DepartmentInfo>() {
        }, HttpMethod.GET, "/associations/{associationId}/departments/{departmentId}", associationId, departmentId);
    }

    @Override
    public ApiResponse<DepartmentInfo> createDepartment(String associationId, DepartmentInfo departmentInfo) {
        return request(new TypeToken<DepartmentInfo>() {
        }, departmentInfo, HttpMethod.POST, "/associations/{associationId}/departments", associationId);
    }

    @Override
    public ApiResponse<DepartmentInfo> deleteDepartment(String associationId, int departmentId) {
        return request(new TypeToken<DepartmentInfo>() {
        }, HttpMethod.DELETE, "/associations/{associationId}/departments/{departmentId}", associationId, departmentId);
    }

    @Override
    public ApiResponse<DepartmentInfo> editDepartment(String associationId, int departmentId, DepartmentInfo departmentInfo) {
        return request(new TypeToken<DepartmentInfo>() {
        }, departmentInfo, HttpMethod.PUT, "/associations/{associationId}/departments/{departmentId}", associationId, departmentId);
    }

    @Override
    public ApiResponse<List<Integer>> getSpecMemberList(String associationId, int departmentId) {
        return request(new TypeToken<List<Integer>>() {
        }, HttpMethod.GET, "/associations/{associationId}/departments/{departmentId}/members", associationId, departmentId);
    }

    @Override
    public ApiResponse<Boolean> removeOneFromDepartment(String associationId, int departmentId, int userId) {
        return request(new TypeToken<Boolean>() {
        }, HttpMethod.DELETE, "/associations/{associationId}/departments/{departmentId}/members/{userId}", associationId, departmentId, userId);
    }

    @Override
    public ApiResponse<Boolean> addOneToDepartment(String associationId, int departmentId, UserId userId) {
        return request(new TypeToken<Boolean>() {
        }, userId, HttpMethod.POST, "/associations/{associationId}/departments/{departmentId}/members", associationId, departmentId);
    }

    @Override
    public ApiResponse<List<Integer>> getUserAssociation(int userId) {
        return request(new TypeToken<List<Integer>>() {
        }, HttpMethod.GET, "/users/{userId}/associations", userId);
    }

    @Override
    public ApiResponse<List<DepartmentInfo>> getUserDepartment(String associationId, int userId) {
        return request(new TypeToken<List<DepartmentInfo>>() {
        }, HttpMethod.GET, "/users/{userId}/associations/{associationId}/departments", userId, associationId);
    }

    @Override
    public ApiResponse<Boolean> deleteOneInAssociation(String associationId, int userId) {
        return request(new TypeToken<Boolean>() {
        }, HttpMethod.DELETE, "/associations/{associationId}/members/{userId}", associationId, userId);
    }

    @Override
    public ApiResponse<Boolean> addOneToAssociation(String associationId, UserId userId) {
        return request(new TypeToken<Boolean>() {
        }, userId, HttpMethod.POST, "/associations/{associationId}/members", associationId);
    }

    @Override
    public ApiResponse<Boolean> deleteAllDepartments(String associationId) {
        return request(new TypeToken<Boolean>() {
        }, HttpMethod.DELETE, "/associations/{associationId}/departments", associationId);
    }

    @Override
    public ApiResponse<List<Integer>> getAssoUserList(String associationId) {
        return request(new TypeToken<List<Integer>>() {
        }, HttpMethod.GET, "/associations/{associationId}/members", associationId);
    }

    @Override
    public ApiResponse<Boolean> initDepartment(InitAssociationInfo initAssociationInfo) {
        return request(new TypeToken<Boolean>() {
        }, initAssociationInfo, HttpMethod.POST, "/associations");
    }

    @Override
    public String scope(String associationId) {
        return "association_" + associationId;
    }

    @Override
    public String roleName(String associationId, int departmentId) {
        switch (departmentId) {
            case 1:
                return MANAGER;
            case 2:
                return MEMBER;
            default:
                return "association_" + associationId + "_" + departmentId;
        }
    }
}
