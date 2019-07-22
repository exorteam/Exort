package exort.association_member_manager.serviceimpl;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class AssociationMemberManageServiceImpl implements AssociationMemberManageService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ApiResponse<Boolean> adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

//        String url="localhost:8900/application/accept";
//        MultiValueMap<String,Object> paramMap=new LinkedMultiValueMap<>();
//        paramMap.add("userId",userId);
//        paramMap.add("event",event);
//        paramMap.add("application",application);
//
//        try{
//            ApiResponse apiResponse=  restTemplate.postForObject(url,paramMap,apiResponse.class);
//            System.out.println(apiResponse);
//
//        }catch (HttpClientErrorException e){
//            System.out.println(e);
//        }

        // outside
        boolean userInAsso = false;
//        String url="http://localhost:8900/users/"+userId+"/scopes";
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            userInAsso=true;
//        }catch (HttpClientErrorException e){
//            userInAsso=false;
//        }


        if (!userInAsso) {
            response.setStatus(200);

//            System.out.println(application);

            apiResponse = addOneToAssociation(application.getObject().getAssociationId(), userId, response);
        } else {
            response.setStatus(400);

            apiResponse.setError("InvalidUser");
            apiResponse.setMessage("用户已存在");
        }

        return apiResponse;
    }

//    @Override
//    public ApiResponse refuseApplication(int applyId) {
//        ApiResponse apiResponse = new ApiResponse();
//
//        Application application = applicationRepository.findById(applyId).get();
//        application.setState(Application.REFUSED);
//        applicationRepository.save(application);
//
//        apiResponse.setMessage("Success Refuse");
//        return apiResponse;
//    }

    @Override
    public ApiResponse<List<Department>> getDepartmentTree(int associationId, HttpServletResponse response) {
        ApiResponse<List<Department>> apiResponse = new ApiResponse<>();

        List<Department> departments = departmentRepository.findAllByAssociationId(associationId);

        if (departments.size() == 0) {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("该社团不存在");

        } else {
            response.setStatus(200);

            apiResponse.setData(departments);
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> getSpecDepartmentInfo(int associationId, int departmentId, HttpServletResponse response) {
        ApiResponse<Department> apiResponse = new ApiResponse<>();

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        if (department == null) {
            response.setStatus(404);

            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("该部门不存在");
        } else {
            response.setStatus(200);

            apiResponse.setData(department);
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> createDepartment(int associationId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response) {
        ApiResponse<Department> apiResponse = new ApiResponse<>();

        if (departmentRepository.existsByAssociationId(associationId)) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, parentId)) {

                Department department = new Department(associationId, departmentName, departmentDesc, parentId);
                Department maxDepartment = departmentRepository.findFirstByAssociationIdOrderByDepartmentIdDesc(associationId);

                department.setDepartmentId((maxDepartment != null ? maxDepartment.getDepartmentId() + 1 : 0));
                departmentRepository.save(department);

                response.setStatus(200);

                apiResponse.setData(department);

            } else {
                response.setStatus(400);

                apiResponse.setError("InvalidDepartment");
                apiResponse.setMessage("部门创建信息不合法");
            }

        } else {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("该社团不存在");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId, HttpServletResponse response) {
        ApiResponse<Department> apiResponse = new ApiResponse<>();

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        if (department == null) {
            response.setStatus(404);

            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");
        } else {
            response.setStatus(200);

            apiResponse.setData(department);

            departmentRepository.delete(department);
        }


        return apiResponse;
    }

    @Override
    public ApiResponse<Department> editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response) {
        ApiResponse<Department> apiResponse = new ApiResponse<>();

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        if (department == null) {
            response.setStatus(404);

            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");
        } else {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, parentId)) {

                response.setStatus(200);

                department.setName(departmentName);
                department.setDescription(departmentDesc);
                department.setParentId(parentId);

                departmentRepository.save(department);

                apiResponse.setData(department);

            } else {
                response.setStatus(400);

                apiResponse.setError("InvalidParentId");
                apiResponse.setMessage("无效父节点");
            }

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId, HttpServletResponse response) {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>();

        if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {

            response.setStatus(200);

            // outside
            String url = "http://localhost:8900/scopes/" + associationId + "/roles/" + departmentId + "/users";
            List<Integer> userList = new ArrayList<>();
//            try{
//                ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//                userList=(List<Integer>)responseEntity.getBody().getData();
//            }catch (HttpClientErrorException e){
//                response.setStatus(404);
//
//                apiResponse.setError("roleNotFound");
//                apiResponse.setMessage("角色不存在");
//
//                return apiResponse;
//            }

            apiResponse.setData(userList);

        } else {
            response.setStatus(404);

            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existUser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (existUser) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {
                // outside
                boolean existUserInAsso = true;
//                String url = "http://localhost:8900/scopes/" + associationId + "/roles/" + departmentId + "/users";
//                try {
//                    ResponseEntity<ApiResponse> responseEntity = this.restTemplate.getForEntity(url, apiResponse.class);
//                    existUserInAsso = true;
//                } catch (HttpClientErrorException e) {
//                    existUserInAsso = false;
//                }


                if (existUserInAsso) {
                    response.setStatus(200);

                    apiResponse.setData(true);

                } else {
                    response.setStatus(404);

                    apiResponse.setError("UserNotFound");
                    apiResponse.setMessage("社团中不存在该用户");
                }

            } else {
                response.setStatus(404);

                apiResponse.setError("DepartmentNotFound");
                apiResponse.setMessage("不存在该部门");
            }

        } else {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existUser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (existUser) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {

                response.setStatus(200);

                // outside
                // add_user_to_department
//                HttpHeaders headers = new HttpHeaders();
//                String data = new String();
//                HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
//                String url = "http://localhost:8900/users/?id=" + userId;
//                try {
//                    ResponseEntity<ApiResponse> responseEntity = this.restTemplate.postForEntity(url, apiResponse.class);
//
//                } catch (HttpClientErrorException e) {
//                    existUser = false;
//                }

                apiResponse.setData(true);
            } else {
                response.setStatus(404);

                apiResponse.setError("DepartmentNotFound");
                apiResponse.setMessage("不存在该部门");
            }

        } else {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
        }

        return apiResponse;
    }

//    @Override
//    public ApiResponse changeOneToDepartment(int associationId, int directionDepartmentId, int userId, HttpServletResponse response) {
//        ApiResponse apiResponse = new ApiResponse();
//        return apiResponse;
//    }

    @Override
    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existUser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (!existUser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");

            return apiResponse;
        }

        if (departmentRepository.existsByAssociationId(associationId)) {

            // outside
            boolean existUserInAsso = true;


            if (!existUserInAsso) {
                response.setStatus(404);

                apiResponse.setError("UserNotFound");
                apiResponse.setMessage("用户不在该社团中");
                return apiResponse;

            }

            // outside
            boolean hasPermission = true;
            if (hasPermission) {
                response.setStatus(200);

                apiResponse.setData(true);
            } else {
                response.setStatus(404);

                apiResponse.setError("PermissionNotFound");
                apiResponse.setMessage("没有该权限");
            }

        } else {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("不存在该社团");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Integer>> getUserAssociation(int userId, HttpServletResponse response) {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        //outside
        List<Integer> associationId = new ArrayList<>();

        response.setStatus(200);

        apiResponse.setData(associationId);

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<List<Department>> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        if (departmentRepository.existsByAssociationId(associationId)) {

            // outside
            boolean userInAsso = true;
//        String url="http://localhost:8900/users/"+userId+"/scopes";
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            userInAsso=true;
//        }catch (HttpClientErrorException e){
//            userInAsso=false;
//        }

            if (userInAsso) {
                response.setStatus(200);

                List<Department> departments = new ArrayList<>();
                apiResponse.setData(departments);

            } else {
                response.setStatus(404);

                apiResponse.setError("UserNotFound");
                apiResponse.setMessage("用户没有参与该社团");
            }

        } else {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("不存在该社团");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        if (!departmentRepository.existsByAssociationId(associationId)) {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("不存在该社团");
            return apiResponse;
        }

        // outside
        boolean userInAsso = true;
        //        String url="http://localhost:8900/users/"+userId+"/scopes";
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            userInAsso=true;
//        }catch (HttpClientErrorException e){
//            userInAsso=false;
//        }

        if (userInAsso) {
            response.setStatus(200);

            // outside
            // delete user in asso

            apiResponse.setData(true);

        } else {
            response.setStatus(404);

            apiResponse.setError("UserNotFound");
            apiResponse.setMessage("用户没有参与该社团");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        if (!departmentRepository.existsByAssociationId(associationId)) {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("不存在该社团");
            return apiResponse;
        }

        response.setStatus(200);

        // outside
        // add user to asso

        apiResponse.setData(true);

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Integer>> getAssoUserList(int associationId, HttpServletResponse response) {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>();

        if (!departmentRepository.existsByAssociationId(associationId)) {
            response.setStatus(404);

            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("不存在该社团");
            return apiResponse;
        }

        // outside
        // get users in asso
        List<Integer> users = new ArrayList<>();
//            try{
//                ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//                userList=(List<Integer>)responseEntity.getBody().getData();
//            }catch (HttpClientErrorException e){
//                response.setStatus(404);
//
//                apiResponse.setError("roleNotFound");
//                apiResponse.setMessage("角色不存在");
//
//                return apiResponse;
//            }

        response.setStatus(200);
        apiResponse.setData(users);

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> initDepartment(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;
//        String url="http://localhost:8900/users/?id="+userId;
//        try{
//            ResponseEntity<ApiResponse> responseEntity=this.restTemplate.getForEntity(url,apiResponse.class);
//            existUser=true;
//        }catch (HttpClientErrorException e){
//            existUser=false;
//        }

        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        response.setStatus(200);
        Department manageDepartment = new Department(associationId, "管理层", "管理部门的最基础部门", 0);

        Department allUsers = new Department(associationId, "所有成员", "社团中所有成员", 0);

        departmentRepository.save(manageDepartment);
        departmentRepository.save(allUsers);

        apiResponse.setData(true);

        return apiResponse;
    }
}
