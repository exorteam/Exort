package exort.association_member_manager.api_server.impl;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.api_server.ApiServer;
import exort.association_member_manager.entity.Department;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ApiServerImpl implements ApiServer {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ApiResponse<Boolean> adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application, HttpServletResponse response) {

        String url="localhost:8900/application/accept";
        MultiValueMap<String,Object> paramMap=new LinkedMultiValueMap<>();
        paramMap.add("userId",userId);
        paramMap.add("event",event);
        paramMap.add("application",application);

        try{
            ApiResponse apiResponse=  restTemplate.postForObject(url,paramMap,ApiResponse.class);

        }catch (HttpClientErrorException e){

        }

        return null;
    }

    @Override
    public ApiResponse<List<Department>> getDepartmentTree(int associationId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Department> getSpecDepartmentInfo(int associationId, int departmentId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Department> createDepartment(int associationId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Department> editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<List<Integer>> getUserAssociation(int userId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<List<Integer>> getAssoUserList(int associationId, HttpServletResponse response) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> initDepartment(int associationId, int userId, HttpServletResponse response) {
        return null;
    }
}
