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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AssociationMemberManageServiceImpl implements AssociationMemberManageService {

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    private String getnum(String origin) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(origin);
        String result = m.replaceAll("").trim();
        return result;
    }

    @Override
    public boolean checkAsso(int associationId) {
        return departmentRepository.existsByAssociationId(associationId);
    }

    @Override
    public boolean checkDepartment(int associationId, int departmentId) {
        return departmentRepository.existsByAssociationIdAndDepartmentId(associationId, departmentId);
    }

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

    public boolean checkUserInAsso(long userId, int associationId) {
        return (ps.hasRole(userId, scope(associationId), MEMBER).getData() != null);
    }

    public boolean checkUserInAsso(int userId, int associationId) {
        return (ps.hasRole((long) userId, scope(associationId), MEMBER).getData() != null);
    }

    public List<Department> findDepartmentList(int associationId) {
        return departmentRepository.findAllByAssociationId(associationId);
    }

    @Override
    public Boolean adoptApplication(int userId, String event, Application<ApplicationDepartmentInfo> application) {

        return addOneToAssociation(application.getObject().getAssociationId(), userId);
    }


    @Override
    public List<Department> getDepartmentTree(int associationId) {

        return findDepartmentList(associationId);
    }

    @Override
    public Department getSpecDepartmentInfo(int associationId, int departmentId) {
        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);

        return department;
    }

    @Override
    public Department createDepartment(int associationId, String departmentName, String departmentDesc, int parentId) {


        Department department = new Department(associationId, departmentName, departmentDesc, parentId);
        Department maxDepartment = departmentRepository.findFirstByAssociationIdOrderByDepartmentIdDesc(associationId);

        department.setDepartmentId((maxDepartment != null ? maxDepartment.getDepartmentId() + 1 : 1));
        System.out.println(maxDepartment);
        departmentRepository.save(department);

        return department;
    }

    @Override
    public Department findDepartment(int associationId, int departmentId) {
        return departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);
    }

    @Override
    public Department deleteDepartment(int associationId, int departmentId) {

        Department department = findDepartment(associationId, departmentId);

        departmentRepository.delete(department);

        return department;
    }

    @Override
    public Department editDepartment(int associationId, int departmentId, String departmentName, String departmentDesc, int parentId) {

        Department department = departmentRepository.findByAssociationIdAndDepartmentId(associationId, departmentId);


        department.setName(departmentName);
        department.setDescription(departmentDesc);
        department.setParentId(parentId);

        departmentRepository.save(department);


        return department;
    }

    @Override
    public List<Integer> getSpecMemberList(int associationId, int departmentId) {

        // outside
        PagedData<Long> pagedusers = ps.getUsers(scope(associationId), roleName(associationId, departmentId), new PageQuery(50)).getData();

        List<Integer> userList = new ArrayList<>();
        for (Long user : pagedusers.getContent()) {
            userList.add(user.intValue());
        }

        return userList;
    }

    @Override
    public Boolean removeOneFromDepartment(int associationId, int departmentId, int userId) {

        ps.revokeRoles((long) userId, scope(associationId), Arrays.asList(roleName(associationId, departmentId)));

        return true;
    }

    @Override
    public Boolean addOneToDepartment(int associationId, int departmentId, int userId) {
        // outside
        // add_user_to_department
        ps.grantRoles((long) userId, scope(associationId), Arrays.asList(MEMBER));

        return true;
    }

    @Override
    public boolean checkUserPerm(int userId, int associationId, String permission) {
        return (ps.hasPermission((long) userId, scope(associationId), permission).getData() != null);
    }

    @Override
    public Boolean checkUserPermissionInAssociation(int associationId, int userId, String permission) {

        return true;
    }


    @Override
    public List<Integer> getUserAssociation(List<String> assos) {

        //outside
        List<Integer> associationId = new ArrayList<>();

        for (String asso : assos) {
            String result = getnum(asso);
            associationId.add(Integer.valueOf(result));
        }

        return associationId;
    }

    @Override
    public List<Department> getUserDepartment(int associationId, int userId) {

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
                    String as = getnum(scope(associationId));
                    String department = getnum(role.getName());
                    departments.add(departmentRepository.findByAssociationIdAndDepartmentId(associationId, Integer.valueOf(department.substring(as.length()))));
                }
            }
        }

        return departments;
    }

    @Override
    public Boolean deleteOneInAssociation(int associationId, int userId) {

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
    public Boolean addOneToAssociation(int associationId, int userId) {

        // outside
        // add user to asso
        ps.grantRoles((long) userId, scope(associationId), Arrays.asList(MEMBER));

        return true;
    }

    @Override
    public List<Integer> getAssoUserList(int associationId) {

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
    public Boolean initDepartment(int associationId, int userId) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        Department manageDepartment = new Department(associationId, 1, "管理层", "管理部门的最基础部门", 0);

        Department allUsers = new Department(associationId, 2, "所有成员", "社团中所有成员", 0);

        departmentRepository.save(manageDepartment);
        departmentRepository.save(allUsers);

        apiResponse.setData(true);

        return true;
    }
}
