package exort.association_member_manager.serviceimpl;

import com.google.common.collect.Lists;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.api.http.review.entity.CallbackParam;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Member;
import java.util.*;

@Service
public class AssociationMemberManageServiceImpl implements AssociationMemberManageService {

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    private String roleName(int associationId, int departmentId) {
        switch (departmentId) {
            case 1:
                return MANAGER;
            case 2:
                return MEMBER;
            default:
                return "association_" + associationId + "_" + departmentId;
        }
    }

    private String scope(int associationId) {
        return "association_" + associationId;
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PermService ps;

    @Override
    public ApiResponse<Boolean> adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
//        ps.hasRole(application.getApplicantId(), scope(application.getObject().getAssociationId()), MEMBER);
//        ps.grantRoles(application.getApplicantId(), scope(application.getObject().getAssociationId()), Arrays.asList(MEMBER));

        // outside
        boolean userInAsso = false;
        if (ps.hasRole(application.getApplicantId(), scope(application.getObject().getAssociationId()), MEMBER).getData() != null) {
            userInAsso = true;
        }

        if (!userInAsso) {
            response.setStatus(200);

            apiResponse = addOneToAssociation(application.getObject().getAssociationId(), userId, response);
        } else {
            response.setStatus(400);

            apiResponse.setError("InvalidUser");
            apiResponse.setMessage("用户已存在");
        }

        return apiResponse;
    }


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

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, parentId) || parentId == 0) {

                Department department = new Department(associationId, departmentName, departmentDesc, parentId);
                Department maxDepartment = departmentRepository.findFirstByAssociationIdOrderByDepartmentIdDesc(associationId);

                department.setDepartmentId((maxDepartment != null ? maxDepartment.getDepartmentId() + 1 : 1));
                System.out.println(maxDepartment);
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
            PagedData<Long> pagedusers = ps.getUsers(scope(associationId), roleName(associationId, departmentId), new PageQuery(50)).getData();

            List<Integer> userList = new ArrayList<>();
            for (Long user : pagedusers.getContent()) {
                userList.add(user.intValue());
            }


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


        if (existUser) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {
                // outside
                boolean existUserInAsso = true;
                if (ps.hasRole(Long.valueOf(userId), scope(associationId), MEMBER).getData() == null) {
                    existUserInAsso = false;
                }

                if (existUserInAsso) {
                    response.setStatus(200);

                    apiResponse.setData(true);

                } else {
                    response.setStatus(404);

                    apiResponse.setError("UserNotFound");
                    apiResponse.setMessage("社团中不存在该用户");
                }

            } else {
                response.setStatus(401);

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


        if (existUser) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {

                response.setStatus(200);

                // outside
                // add_user_to_department
                ps.grantRoles(Long.valueOf(userId), scope(associationId), Arrays.asList(MEMBER));

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


    @Override
    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existUser = true;


        if (!existUser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");

            return apiResponse;
        }

        if (departmentRepository.existsByAssociationId(associationId)) {

            // outside
            boolean existUserInAsso = false;
            if (ps.hasRole(Long.valueOf(userId), scope(associationId), MEMBER).getData() != null) {
                existUserInAsso = true;
            }


            if (!existUserInAsso) {
                response.setStatus(402);

                apiResponse.setError("UserNotFound");
                apiResponse.setMessage("用户不在该社团中");
                return apiResponse;

            }

            // outside
            boolean hasPermission = false;

            try {
                ps.hasPermission(Long.valueOf(userId), scope(associationId), permission);
                hasPermission = true;
            } catch (Exception e) {
                hasPermission = false;
            }

            if (hasPermission) {
                response.setStatus(200);

                apiResponse.setData(true);
            } else {
                response.setStatus(404);

                apiResponse.setError("PermissionNotFound");
                apiResponse.setMessage("没有该权限");
            }

        } else {
            response.setStatus(401);

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

        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        //outside
        List<Integer> associationId = new ArrayList<>();
        List<String> assos = ps.getScopes(Long.valueOf(userId)).getData();
        if (assos == null) {
            response.setStatus(400);

            apiResponse.setError(ps.getScopes(Long.valueOf(userId)).getError());
            apiResponse.setMessage(ps.getScopes(Long.valueOf(userId)).getMessage());
            return apiResponse;
        }
        for (String asso : assos) {
            associationId.add(Integer.valueOf(asso));
        }

        response.setStatus(200);

        apiResponse.setData(associationId);

        return apiResponse;
    }

    @Override
    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<List<Department>> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;


        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        if (departmentRepository.existsByAssociationId(associationId)) {

            // outside
            boolean userInAsso = false;
            if (ps.hasRole(Long.valueOf(userId), scope(associationId), MEMBER).getData() != null) {
                userInAsso = true;
            }


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
            response.setStatus(401);

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
        boolean userInAsso = false;
        if (ps.hasRole(Long.valueOf(userId), scope(associationId), MEMBER).getData() != null) {
            userInAsso = true;
        }


        if (userInAsso) {
            response.setStatus(200);

            // outside
            // delete user in asso
            List<Role> roles = ps.getRoles(Long.valueOf(userId), scope(associationId)).getData();
            List<String> rolenames = new ArrayList<>();
            for (Role role : roles) {
                rolenames.add(role.getName());
            }
            ps.revokeRoles(Long.valueOf(userId), scope(associationId), rolenames);

            apiResponse.setData(true);

        } else {
            response.setStatus(401);

            apiResponse.setError("UserNotFound");
            apiResponse.setMessage("社团中不存在该用户");
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;


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
        ps.grantRoles(Long.valueOf(userId), scope(associationId), Arrays.asList(MEMBER));

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
        List<Long> longs = ps.getUsers(scope(associationId), new PageQuery(50)).getData().getContent();
        for (Long l : longs) {
            users.add(l.intValue());
        }

        response.setStatus(200);
        apiResponse.setData(users);

        return apiResponse;
    }

    @Override
    public ApiResponse<Boolean> initDepartment(int associationId, int userId, HttpServletResponse response) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        // outside
        boolean existuser = true;


        if (!existuser) {
            response.setStatus(400);

            apiResponse.setError("InvalidUserId");
            apiResponse.setMessage("不存在该用户");
            return apiResponse;
        }

        response.setStatus(200);
        Department manageDepartment = new Department(associationId, 1, "管理层", "管理部门的最基础部门", 0);

        Department allUsers = new Department(associationId, 2, "所有成员", "社团中所有成员", 0);

        departmentRepository.save(manageDepartment);
        departmentRepository.save(allUsers);

        apiResponse.setData(true);

        return apiResponse;
    }
}
