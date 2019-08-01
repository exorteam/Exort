package exort.association_member_manager.serviceimpl;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationDepartmentInfo;
import exort.association_member_manager.entity.Department;
import exort.association_member_manager.repository.DepartmentRepository;
import exort.association_member_manager.service.AssociationMemberManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AssociationMemberManageServiceImpl implements AssociationMemberManageService {

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    @Override
    public Boolean checkAsso(String associationId) {
        return departmentRepository.existsByAssociationId(associationId);
    }

    @Override
    public Boolean checkDepartment(String associationId, int departmentId) {
        return departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId);
    }

    private String roleName(String associationId, int departmentId) {
        switch (departmentId) {
            case 1:
                return MANAGER;
            case 2:
                return MEMBER;
            default:
                return "association_" + associationId + "_" + departmentId;
        }
    }

    private String scope(String associationId) {
        return "association_" + associationId;
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PermService ps;

    public Boolean checkUserInAsso(long userId, String associationId) {
        return (ps.hasRole(userId, scope(associationId), MEMBER).getData() != null);
    }

    public Boolean checkUserInAsso(int userId, String associationId) {
        return (ps.hasRole((long) userId, scope(associationId), MEMBER).getData() != null);
    }

    public List<Department> findDepartmentList(String associationId) {
        return departmentRepository.findAllByAssociationId(associationId);
    }

    @Override
    @Transactional
    public Boolean adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application) {

        return addOneToAssociation(application.getObject().getAssociationId(), userId);
    }


    @Override
    public List<Department> getDepartmentTree(String associationId) {

        return findDepartmentList(associationId);
    }

    @Override
    public Department getSpecDepartmentInfo(String associationId, int departmentId) {
        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        return department;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Department createDepartment(String associationId, String departmentName, String departmentDesc, int parentId) {

        Department department = new Department(associationId, departmentName, departmentDesc, parentId);
        Department maxDepartment = departmentRepository.findFirstByAssociationIdOrderByDepartmentIdDesc(associationId);

        department.setDepartmentId((maxDepartment != null ? maxDepartment.getDepartmentId() + 1 : 1));

        department = departmentRepository.save(department);
        ps.createRole(new Role(roleName(associationId, department.getDepartmentId()), scope(associationId), departmentDesc));

        return department;
    }

    @Override
    public Department findDepartment(String associationId, int departmentId) {
        return departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);
    }

    @Override
    @Transactional
    public Department deleteDepartment(String associationId, int departmentId) {

        Department department = findDepartment(associationId, departmentId);

        departmentRepository.delete(department);
        ps.deleteRole(roleName(associationId, departmentId));

        return department;
    }

    @Override
    @Transactional
    public Department editDepartment(String associationId, int departmentId, String departmentName, String departmentDesc, int parentId) {

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);


        department.setName(departmentName);
        department.setDescription(departmentDesc);
        department.setParentId(parentId);

        departmentRepository.save(department);


        return department;
    }

    @Override
    public List<Integer> getSpecMemberList(String associationId, int departmentId) {

        // outside
        PagedData<Long> pagedusers = ps.getUsers(scope(associationId), roleName(associationId, departmentId), new PageQuery(50)).getData();

        List<Integer> userList = new ArrayList<>();
        for (Long user : pagedusers.getContent()) {
            userList.add(user.intValue());
        }

        return userList;
    }

    @Override
    @Transactional
    public Boolean removeOneFromDepartment(String associationId, int departmentId, int userId) {

        ps.revokeRoles((long) userId, scope(associationId), Arrays.asList(roleName(associationId, departmentId)));

        return true;
    }

    @Override
    @Transactional
    public Boolean addOneToDepartment(String associationId, int departmentId, int userId) {
        // outside
        // add_user_to_department
        ps.grantRoles((long) userId, scope(associationId), Arrays.asList(roleName(associationId, departmentId)));

        return true;
    }

    @Override
    public boolean checkUserPerm(int userId, String associationId, String permission) {
        return (ps.hasPermission((long) userId, scope(associationId), permission).getData() != null);
    }


    @Override
    public List<String> getUserAssociation(List<String> assos) {

        //outside
        List<String> associationId = new ArrayList<>();

        for (String asso : assos) {
            String result = asso.substring(12);
            associationId.add(result);
        }

        return associationId;
    }

    @Override
    public List<Department> getUserDepartment(String associationId, int userId) {

        List<Role> departmentlist = ps.getRoles((long) userId, scope(associationId)).getData();

        List<Department> departments = new ArrayList<>();

        if (departmentlist.size() == 0) {
            return departments;
        }

        for (Role role : departmentlist) {
            switch (role.getName()) {
                case MANAGER: {
                    departments.add(departmentRepository.findByAssociationIdAndDepartmentId(associationId, 1));
                    break;
                }
                case MEMBER: {
                    departments.add(departmentRepository.findByAssociationIdAndDepartmentId(associationId, 2));
                    break;
                }
                default: {
                    String as = (scope(associationId)).substring(12);
                    String department = (role.getName()).substring(scope(associationId).length() + 1);
                    departments.add(departmentRepository.findByAssociationIdAndDepartmentId(associationId, Integer.valueOf(department.substring(as.length()))));
                }
            }
        }

        return departments;
    }

    @Override
    @Transactional
    public Boolean deleteOneInAssociation(String associationId, int userId) {

        // outside
        // delete user in asso
        List<Role> roles = ps.getRoles(Long.valueOf(userId), scope(associationId)).getData();
        List<String> rolenames = new ArrayList<>();
        for (Role role : roles) {
            rolenames.add(role.getName());
        }
        ps.revokeRoles(Long.valueOf(userId), scope(associationId), rolenames);

        return true;
    }

    @Override
    @Transactional
    public Boolean addOneToAssociation(String associationId, int userId) {

        // outside
        // add user to asso
        List<Role> roles = ps.grantRoles((long) userId, scope(associationId), Arrays.asList(MEMBER)).getData();

        return (roles.size() != 0);
    }

    @Override
    public List<Integer> getAssoUserList(String associationId) {

        // outside
        // get users in asso
        List<Integer> users = new ArrayList<>();
        List<Long> longs = ps.getUsers(scope(associationId), new PageQuery(50)).getData().getContent();
        for (Long l : longs) {
            users.add(l.intValue());
        }

        return users;
    }

    @Override
    @Transactional
    public Boolean initDepartment(String associationId, int userId) {

        if (checkAsso(associationId)) {
            List<Department> list = getDepartmentTree(associationId);
            for (Department department : list) {
                departmentRepository.delete(department);
            }
        }

        Department manageDepartment = new Department(associationId, 1, "管理层", "管理部门的最基础部门", 0);

        Department allUsers = new Department(associationId, 2, "所有成员", "社团中所有成员", 0);

        departmentRepository.save(manageDepartment);
        departmentRepository.save(allUsers);

        ps.grantRoles((long)userId,scope(associationId),Arrays.asList(MEMBER,MANAGER));

        return true;
    }

    @Override
    public Boolean deleteAllDepartments(String associationId) {
        List<Department> departments = departmentRepository.findAllByAssociationId(associationId);

        for (Department department : departments) {
            departmentRepository.delete(department);

            if (ps.deleteRole(roleName(associationId, department.getDepartmentId())).getData() == null) {
                return false;
            }
        }

        return true;
    }
}
