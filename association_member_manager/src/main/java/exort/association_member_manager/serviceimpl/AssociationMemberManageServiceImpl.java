package exort.association_member_manager.serviceimpl;

import exort.api.http.entity.Application;
import exort.api.http.entity.ApplicationDepartmentInfo;
import exort.api.http.entity.ResponseCode;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class AssociationMemberManageServiceImpl implements AssociationMemberManageService {

    @Autowired
    private DepartmentRepository departmentRepository;

//    @Override
//    public ResponseCode getSpecApplication(int applyId) {
//        ResponseCode responseCode = new ResponseCode();
//        responseCode.setMessage("Success.");
//        HashMap<String, Application> data = new HashMap<>();
//        data.put("application", applicationRepository.findById(applyId).get());
//        responseCode.setData(data);
//
//        return responseCode;
//    }

    //条件拼接
//    @Override
//    public ResponseCode getSomeApplications(Optional<Integer> userId, Optional<Integer> associationId, Optional<Integer> departmentId, Optional<Date> startTime, Optional<Date> endTime, int page, int size) {
//        ResponseCode responseCode = new ResponseCode();
//
//        Specification<Application> specification = new Specification<Application>() {
//            @Override
//            public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> list = new ArrayList<>();
//
//                if (userId.isPresent()) {
//                    list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), userId.get()));
//                }
//                if (associationId.isPresent() && departmentId.isPresent()) {
//                    list.add(criteriaBuilder.equal(root.get("associationId").as(Integer.class), associationId.get()));
//                    list.add(criteriaBuilder.equal(root.get("departmentId").as(Integer.class), departmentId.get()));
//                }
//
//                if (associationId.isPresent()) {
//                    list.add(criteriaBuilder.equal(root.get("associationId").as(Integer.class), associationId.get()));
//                }
//
//                if (startTime.isPresent() && endTime.isPresent()) {
//                    list.add(criteriaBuilder.between(root.get("applyTime").as(Date.class), startTime.get(), endTime.get()));
//                }
//
//                criteriaQuery.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
//
//                return criteriaQuery.getRestriction();
//            }
//        };
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<Application> applications = applicationRepository.findAll(specification, pageable);
//
//
//        HashMap<String, Page<Application>> data = new HashMap<>();
//        data.put("application", applications);
//        responseCode.setData(data);
//
//        return responseCode;
//    }

    @Override
    public ResponseCode<Boolean> adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean userInAsso = false;

        if (!userInAsso) {
            response.setStatus(200);

            responseCode = addOneToAssociation(application.getObject().getAssociationId(), userId, response);
        } else {
            response.setStatus(400);

            responseCode.setError("InvalidUser");
            responseCode.setMessage("用户已存在");
        }

        return responseCode;
    }

//    @Override
//    public ResponseCode refuseApplication(int applyId) {
//        ResponseCode responseCode = new ResponseCode();
//
//        Application application = applicationRepository.findById(applyId).get();
//        application.setState(Application.REFUSED);
//        applicationRepository.save(application);
//
//        responseCode.setMessage("Success Refuse");
//        return responseCode;
//    }

    @Override
    public ResponseCode<List<Department>> getDepartmentTree(int associationId, HttpServletResponse response) {
        ResponseCode<List<Department>> responseCode = new ResponseCode<>();

        List<Department> departments = departmentRepository.findAllByAssociationId(associationId);

        if (departments.size() == 0) {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("该社团不存在");

        } else {
            response.setStatus(200);

            responseCode.setData(departments);
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Department> getSpecDepartmentInfo(int associationId, int departmentId, HttpServletResponse response) {
        ResponseCode<Department> responseCode = new ResponseCode<>();

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        if (department == null) {
            response.setStatus(404);

            responseCode.setError("DepartmentNotFound");
            responseCode.setMessage("该部门不存在");
        } else {
            response.setStatus(200);

            responseCode.setData(department);
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Department> createDepartment(int associationId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response) {
        ResponseCode<Department> responseCode = new ResponseCode<>();

        if (departmentRepository.existsByAssociationId(associationId)) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, parentId)) {

                Department department = new Department(associationId, departmentName, departmentDesc, parentId);
                Department maxDepartment = departmentRepository.findFirstByAssociationIdOrderByDepartmentIdDesc(associationId);

                department.setDepartmentId((maxDepartment != null ? maxDepartment.getDepartmentId() + 1 : 0));
                departmentRepository.save(department);

                response.setStatus(200);

                responseCode.setData(department);

            } else {
                response.setStatus(400);

                responseCode.setError("InvalidDepartment");
                responseCode.setMessage("部门创建信息不合法");
            }

        } else {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("该社团不存在");
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Department> deleteDepartment(int associationId, int departmentId, HttpServletResponse response) {
        ResponseCode<Department> responseCode = new ResponseCode<>();

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        if (department == null) {
            response.setStatus(404);

            responseCode.setError("DepartmentNotFound");
            responseCode.setMessage("不存在该部门");
        } else {
            response.setStatus(200);

            responseCode.setData(department);

            departmentRepository.delete(department);
        }


        return responseCode;
    }

    @Override
    public ResponseCode<Department> editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId, HttpServletResponse response) {
        ResponseCode<Department> responseCode = new ResponseCode<>();

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        if (department == null) {
            response.setStatus(404);

            responseCode.setError("DepartmentNotFound");
            responseCode.setMessage("不存在该部门");
        } else {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, parentId)) {

                response.setStatus(200);

                department.setName(departmentName);
                department.setDescription(departmentDesc);
                department.setParentId(parentId);

                departmentRepository.save(department);

                responseCode.setData(department);

            } else {
                response.setStatus(400);

                responseCode.setError("InvalidParentId");
                responseCode.setMessage("无效父节点");
            }

        }

        return responseCode;
    }

    @Override
    public ResponseCode<List<Integer>> getSpecMemberList(int associationId, int departmentId, HttpServletResponse response) {
        ResponseCode<List<Integer>> responseCode = new ResponseCode<>();

        if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {

            response.setStatus(200);

            // outside
            List<Integer> userList = new ArrayList<>();
            responseCode.setData(userList);

        } else {
            response.setStatus(404);

            responseCode.setError("DepartmentNotFound");
            responseCode.setMessage("不存在该部门");
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean existUser = true;

        if (existUser) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {
                // outside
                boolean existUserInAsso = true;

                if (existUserInAsso) {
                    response.setStatus(200);

                    responseCode.setData(true);

                } else {
                    response.setStatus(404);

                    responseCode.setError("UserNotFound");
                    responseCode.setMessage("社团中不存在该用户");
                }

            } else {
                response.setStatus(404);

                responseCode.setError("DepartmentNotFound");
                responseCode.setMessage("不存在该部门");
            }

        } else {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Boolean> addOneToDepartment(int associationId, int departmentId, int userId, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean existUser = true;

        if (existUser) {

            if (departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId)) {

                response.setStatus(200);

                // outside
                // add_user_to_department

                responseCode.setData(true);
            } else {
                response.setStatus(404);

                responseCode.setError("DepartmentNotFound");
                responseCode.setMessage("不存在该部门");
            }

        } else {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
        }

        return responseCode;
    }

//    @Override
//    public ResponseCode changeOneToDepartment(int associationId, int directionDepartmentId, int userId, HttpServletResponse response) {
//        ResponseCode responseCode = new ResponseCode();
//        return responseCode;
//    }

    @Override
    public ResponseCode<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean existUser = true;

        if (!existUser) {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");

            return responseCode;
        }

        if (departmentRepository.existsByAssociationId(associationId)) {

            // outside
            boolean existUserInAsso = true;

            if (!existUserInAsso) {
                response.setStatus(404);

                responseCode.setError("UserNotFound");
                responseCode.setMessage("用户不在该社团中");
                return responseCode;

            }

            // outside
            boolean hasPermission = true;
            if (hasPermission) {
                response.setStatus(200);

                responseCode.setData(true);
            } else {
                response.setStatus(404);

                responseCode.setError("PermissionNotFound");
                responseCode.setMessage("没有该权限");
            }

        } else {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("不存在该社团");
        }

        return responseCode;
    }

    @Override
    public ResponseCode<List<Integer>> getUserAssociation(int userId, HttpServletResponse response) {
        ResponseCode<List<Integer>> responseCode = new ResponseCode<>();

        // outside
        boolean existuser = true;
        if (!existuser) {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
            return responseCode;
        }

        //outside
        List<Integer> associationId = new ArrayList<>();

        response.setStatus(200);

        responseCode.setData(associationId);

        return responseCode;
    }

    @Override
    public ResponseCode<List<Department>> getUserDepartment(int associationId, int userId, HttpServletResponse response) {
        ResponseCode<List<Department>> responseCode = new ResponseCode<>();

        // outside
        boolean existuser = true;
        if (!existuser) {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
            return responseCode;
        }

        if (departmentRepository.existsByAssociationId(associationId)) {

            // outside
            boolean userInAsso = true;
            if (userInAsso) {
                response.setStatus(200);

                List<Department> departments = new ArrayList<>();
                responseCode.setData(departments);

            } else {
                response.setStatus(404);

                responseCode.setError("UserNotFound");
                responseCode.setMessage("用户没有参与该社团");
            }

        } else {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("不存在该社团");
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Boolean> deleteOneInAssociation(int associationId, int userId, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean existuser = true;
        if (!existuser) {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
            return responseCode;
        }

        if (!departmentRepository.existsByAssociationId(associationId)) {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("不存在该社团");
            return responseCode;
        }

        // outside
        boolean userInAsso = true;
        if (userInAsso) {
            response.setStatus(200);

            // outside
            // delete user in asso

            responseCode.setData(true);

        } else {
            response.setStatus(404);

            responseCode.setError("UserNotFound");
            responseCode.setMessage("用户没有参与该社团");
        }

        return responseCode;
    }

    @Override
    public ResponseCode<Boolean> addOneToAssociation(int associationId, int userId, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean existuser = true;
        if (!existuser) {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
            return responseCode;
        }

        if (!departmentRepository.existsByAssociationId(associationId)) {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("不存在该社团");
            return responseCode;
        }

        response.setStatus(200);

        // outside
        // add user to asso

        responseCode.setData(true);

        return responseCode;
    }

    @Override
    public ResponseCode<List<Integer>> getAssoUserList(int associationId, HttpServletResponse response) {
        ResponseCode<List<Integer>> responseCode = new ResponseCode<>();

        if (!departmentRepository.existsByAssociationId(associationId)) {
            response.setStatus(404);

            responseCode.setError("AssociationNotFound");
            responseCode.setMessage("不存在该社团");
            return responseCode;
        }

        // outside
        // get users in asso
        List<Integer> users = new ArrayList<>();
        response.setStatus(200);
        responseCode.setData(users);

        return responseCode;
    }

    @Override
    public ResponseCode<Boolean> initDepartment(int associationId, int userId, HttpServletResponse response) {
        ResponseCode<Boolean> responseCode = new ResponseCode<>();

        // outside
        boolean existuser = true;
        if (!existuser) {
            response.setStatus(400);

            responseCode.setError("InvalidUserId");
            responseCode.setMessage("不存在该用户");
            return responseCode;
        }

        response.setStatus(200);
        Department manageDepartment = new Department(associationId, "管理层", "管理部门的最基础部门", 0);

        Department allUsers = new Department(associationId, "所有成员", "社团中所有成员", 0);

        departmentRepository.save(manageDepartment);
        departmentRepository.save(allUsers);

        responseCode.setData(true);

        return responseCode;
    }
}
