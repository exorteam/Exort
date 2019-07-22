package exort.association_member_manager.api_server.impl;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.member.entity.DepartmentInfo;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.api.http.review.entity.CallbackParam;
import exort.association_member_manager.api_server.ApiServer;
import exort.association_member_manager.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiServerImpl implements ApiServer {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ApiResponse adoptApplication(CallbackParam<ApplicationDepartmentInfo> callbackParam) {
        ApiResponse apiResponse = ApiResponse.emptyResponse();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CallbackParam> requestEntity = new HttpEntity<>(callbackParam, headers);

        String url = "http://localhost:8900/application/accept";
        MultiValueMap<String, CallbackParam<ApplicationDepartmentInfo>> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("callbackParam", callbackParam);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<ApiResponse>() {
            });
//            System.out.println(responseEntity.getBody());
            apiResponse = responseEntity.getBody();
//            System.out.println(apiResponse);

        } catch (HttpClientErrorException e) {

            apiResponse.setError("InvalidUser");
            apiResponse.setMessage("用户已存在");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Department>> getDepartmentTree(int associationId) {
        ApiResponse<List<Department>> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        String url = "http://localhost:8900/associations/" + associationId + "/departments";
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);

        try {
            ResponseEntity<ApiResponse<List<Department>>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<List<Department>>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("该社团不存在");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> getSpecDepartmentInfo(int associationId, int departmentId) {
        ApiResponse<Department> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId;

        try {
            ResponseEntity<ApiResponse<Department>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<Department>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> createDepartment(int associationId, DepartmentInfo departmentInfo) {

        ApiResponse<Department> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DepartmentInfo> requestEntity = new HttpEntity<>(departmentInfo, headers);
        String url = "http://localhost:8900/associations/"+associationId+"/departments";

        try {
            ResponseEntity<ApiResponse<Department>> responseEntity = restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<ApiResponse<Department>>() {
            });
            apiResponse = responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                apiResponse.setError("InvalidDepartment");
                apiResponse.setMessage("部门创建信息不合法");
            } else {
                apiResponse.setError("AssociationNotFound");
                apiResponse.setMessage("该社团不存在");
            }
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId) {

        ApiResponse<Department> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.DELETE;
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId;

        try {
            ResponseEntity<ApiResponse<Department>> responseEntity = restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<ApiResponse<Department>>() {
            });
            apiResponse = responseEntity.getBody();

        } catch (HttpClientErrorException e) {

            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Department> editDepartment(int associationId, int departmentId, DepartmentInfo departmentInfo) {

        ApiResponse<Department> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PUT;
        headers.setContentType(MediaType.APPLICATION_JSON);

        departmentInfo.setAssociationId(associationId);
        departmentInfo.setDepartmentId(departmentId);
        HttpEntity<DepartmentInfo> requestEntity = new HttpEntity<>(departmentInfo, headers);

        String url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId;

        try {
            ResponseEntity<ApiResponse<Department>> responseEntity = restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<ApiResponse<Department>>() {
            });
            apiResponse = responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                apiResponse.setError("InvalidParentId");
                apiResponse.setMessage("无效父节点");
            } else {
                apiResponse.setError("DepartmentNotFound");
                apiResponse.setMessage("不存在该部门");
            }

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId) {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId + "/members";

        try {

            ResponseEntity<ApiResponse<List<Integer>>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<List<Integer>>>() {
            });


            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//            System.out.println(e);
            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId) {

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.DELETE;
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId + "/members/" + userId;

        try {
            ResponseEntity<ApiResponse<Boolean>> responseEntity = restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<ApiResponse<Boolean>>() {
            });
            apiResponse = responseEntity.getBody();

        } catch (HttpClientErrorException e) {

            apiResponse.setError("DepartmentNotFound");
            apiResponse.setMessage("不存在该部门");

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId) {

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Integer> requestEntity = new HttpEntity<>(userId, headers);
        String url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId + "/members";

        try {
            ResponseEntity<ApiResponse<Boolean>> responseEntity = restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<ApiResponse<Boolean>>() {
            });
            apiResponse = responseEntity.getBody();

        } catch (HttpClientErrorException e) {
//            System.out.println(e);

            switch (e.getStatusCode().value()) {
                case 400: {
                    apiResponse.setError("InvalidUserId");
                    apiResponse.setMessage("不存在该用户");
                    break;
                }
                default: {
                    apiResponse.setError("DepartmentNotFound");
                    apiResponse.setMessage("不存在该部门");
                    break;
                }
            }

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/members/" + userId + "/permissions/" + permission;

        try {
            ResponseEntity<ApiResponse<Boolean>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<Boolean>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 400: {
                    apiResponse.setError("InvalidUserId");
                    apiResponse.setMessage("不存在该用户");
                    break;
                }
                case 401: {
                    apiResponse.setError("AssociationNotFound");
                    apiResponse.setMessage("不存在该社团");
                    break;
                }
                case 402: {
                    apiResponse.setError("UserNotFound");
                    apiResponse.setMessage("用户不在该社团中");
                    break;
                }
                default: {
                    apiResponse.setError("PermissionNotFound");
                    apiResponse.setMessage("没有该权限");
                    break;
                }
            }
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Integer>> getUserAssociation(int userId) {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/users/" + userId + "/associations";

        try {
            ResponseEntity<ApiResponse<List<Integer>>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<List<Integer>>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId) {
        ApiResponse<List<Department>> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/users/" + userId + "/associations/" + associationId + "/departments";

        try {
            ResponseEntity<ApiResponse<List<Department>>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<List<Department>>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {

            switch (e.getStatusCode().value()) {
                case 400: {
                    apiResponse.setError("InvalidUserId");
                    apiResponse.setMessage("不存在该用户");
                    break;
                }
                case 401: {
                    apiResponse.setError("AssociationNotFound");
                    apiResponse.setMessage("不存在该社团");
                    break;
                }
                default: {
                    apiResponse.setError("UserNotFound");
                    apiResponse.setMessage("用户没有参与该社团");
                    break;
                }
            }

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.DELETE;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/members/" + userId;

        try {
            ResponseEntity<ApiResponse<Boolean>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<Boolean>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {

            switch (e.getStatusCode().value()) {
                case 400: {
                    apiResponse.setError("InvalidUserId");
                    apiResponse.setMessage("不存在该用户");
                    break;
                }
                case 401: {
                    apiResponse.setError("UserNotFound");
                    apiResponse.setMessage("社团中不存在该用户");
                    break;
                }
                default: {
                    apiResponse.setError("AssociationNotFound");
                    apiResponse.setMessage("不存在该社团");
                    break;
                }
            }

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.POST;
        HttpEntity<Integer> entity = new HttpEntity<>(userId, headers);
        String url = "http://localhost:8900/associations/" + associationId + "/members";

        try {
            ResponseEntity<ApiResponse<Boolean>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<Boolean>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {

            switch (e.getStatusCode().value()) {
                case 400: {
                    apiResponse.setError("InvalidUserId");
                    apiResponse.setMessage("不存在该用户");
                    break;
                }
                default: {
                    apiResponse.setError("AssociationNotFound");
                    apiResponse.setMessage("不存在该社团");
                    break;
                }
            }

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Integer>> getAssoUserList(int associationId) {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.GET;
        HttpEntity entity = new HttpEntity(headers);
        String url = "http://localhost:8900/associations/" + associationId + "/members";

        try {
            ResponseEntity<ApiResponse<List<Integer>>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<List<Integer>>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            apiResponse.setError("AssociationNotFound");
            apiResponse.setMessage("不存在该社团");

        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> initDepartment(InitAssociationInfo initAssociationInfo) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpMethod method = HttpMethod.POST;
        HttpEntity<InitAssociationInfo> entity = new HttpEntity<>(initAssociationInfo, headers);
        String url = "http://localhost:8900/associations";

        try {
            ResponseEntity<ApiResponse<Boolean>> responseEntity = this.restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<ApiResponse<Boolean>>() {
            });

            apiResponse = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            apiResponse.setError("InvalidUser");
            apiResponse.setMessage("用户不存在");

        }

        return apiResponse;
    }
}
