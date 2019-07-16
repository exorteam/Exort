package exort.association_member_management.serviceimpl;

import exort.association_member_management.dto.ResponseCode;
import exort.association_member_management.entity.Application;
import exort.association_member_management.entity.Department;
import exort.association_member_management.repository.ApplicationRepository;
import exort.association_member_management.repository.DepartmentRepository;
import exort.association_member_management.service.AssociationMemberManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class AssociationMemberManageServiceImpl implements AssociationMemberManageService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseCode getSpecApplication(int applyId) {
        ResponseCode responseCode=new ResponseCode();

        responseCode.setMsg("Success.");
        HashMap<String,Application> data=new HashMap<>();
        data.put("application",applicationRepository.findById(applyId).get());
        responseCode.setData(data);

        return responseCode;
    }

    //条件拼接
    @Override
    public ResponseCode getSomeApplications(Optional<Integer> userId, Optional<Integer> associationId, Optional<Integer> departmentId, Optional<Date> startTime, Optional<Date> endTime,int page,int size) {
        ResponseCode responseCode=new ResponseCode();

        Specification<Application> specification=new Specification<Application>() {
            @Override
            public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if(userId.isPresent()){
                    list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class),userId.get()));
                }
                if(associationId.isPresent() && departmentId.isPresent()){
                    list.add(criteriaBuilder.equal(root.get("associationId").as(Integer.class),associationId.get()));
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(Integer.class),departmentId.get()));
                }

                if(associationId.isPresent()){
                    list.add(criteriaBuilder.equal(root.get("associationId").as(Integer.class),associationId.get()));
                }

                if(startTime.isPresent() && endTime.isPresent()){
                    list.add(criteriaBuilder.between(root.get("applyTime").as(Date.class),startTime.get(),endTime.get()));
                }

                criteriaQuery.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));

                return criteriaQuery.getRestriction();
            }
        };

      Pageable pageable=PageRequest.of(page,size);

        Page<Application> applications=applicationRepository.findAll(specification,pageable);


        HashMap<String,Page<Application>> data=new HashMap<>();
        data.put("application",applications);
        responseCode.setData(data);

        return responseCode;
    }

    @Override
    public ResponseCode adoptApplication(int applyId) {
        ResponseCode responseCode=new ResponseCode();

        Application application= applicationRepository.findById(applyId).get();
        application.setState(Application.ACCEPTED);
        applicationRepository.save(application);

        responseCode.setMsg("Success Adopt");

        return responseCode;
    }

    @Override
    public ResponseCode refuseApplication(int applyId) {
        ResponseCode responseCode=new ResponseCode();

        Application application= applicationRepository.findById(applyId).get();
        application.setState(Application.REFUSED);
        applicationRepository.save(application);

        responseCode.setMsg("Success Refuse");
        return responseCode;
    }

    @Override
    public ResponseCode getDepartmentTree(int associationId) {
        ResponseCode responseCode=new ResponseCode();

        List<Department>departments=departmentRepository.findAllByAssociationId(associationId);

        responseCode.setMsg("ok");
        HashMap<String,List<Department>> departmentTree=new HashMap<>();
        departmentTree.put("departmentTree",departments);
        responseCode.setData(departmentTree);

        return responseCode;
    }

    @Override
    public ResponseCode getSpecDepartmentInfo(int associationId, int departmentId) {
        ResponseCode responseCode=new ResponseCode();

        List<Department>departments=departmentRepository.findAllByAssociationIdAndDepartmentId(associationId,departmentId);

        responseCode.setMsg("ok");
        HashMap<String,List<Department>> ret=new HashMap<>();
        ret.put("SpecDepartmentInfo",departments);
        responseCode.setData(ret);

        return responseCode;
    }

    @Override
    public ResponseCode createDepartment(int associationId, String departmentName, String departmentDesc, int parentId) {
        ResponseCode responseCode=new ResponseCode();

        Department department=new Department(associationId,departmentName,departmentDesc,parentId);
        departmentRepository.save(department);

        responseCode.setMsg("Success Create Department");
        HashMap<String,Integer> ret=new HashMap<>();
        ret.put("associationId",associationId);
        ret.put("departmentId",department.getDepartmentId());

        responseCode.setData(ret);

        return responseCode;
    }

    @Override
    public ResponseCode deleteDepartment(int associationId, int departmentId) {
        ResponseCode responseCode=new ResponseCode();

        Department department=departmentRepository.findByAssociationIdAndDepartmentId(associationId,departmentId);
        departmentRepository.delete(department);

        responseCode.setMsg("Success Delete Department");

        return responseCode;
    }

    @Override
    public ResponseCode editDepartment(int associationId,int departmentId, String departmentName, String departmentDesc, int parentId) {
        ResponseCode responseCode=new ResponseCode();

        Department department=departmentRepository.findByAssociationIdAndDepartmentId(associationId,departmentId);
        department.setName(departmentName);
        department.setDescription(departmentDesc);
        department.setParentId(parentId);

        departmentRepository.save(department);

        responseCode.setMsg("Success Edit Department");

        return responseCode;
    }

    @Override
    public ResponseCode getSpecMemberList(int associationId, int departmentId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode removeOneFromDepartment(int associationId, int departmentId, int userId) {
        ResponseCode responseCode=new ResponseCode();

        return responseCode;
    }

    @Override
    public ResponseCode addOneToDepartment(int associationId, int departmentId, int userId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode changeOneToDepartment(int associationId, int directionDepartmentId, int userId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode checkUserPermissionInAssociation(int associationId, int userId, String permission) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode getUserAssociation(int userId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode getUserDepartment(int associationId, int userId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode deleteOneInAssociation(int associationId, int userId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode addOneToAssociation(int associationId, int userId) {
        ResponseCode responseCode=new ResponseCode();
        return responseCode;
    }

    @Override
    public ResponseCode getAssoUserList(int associationId) {
        return null;
    }

    @Override
    public ResponseCode initDepartment(int associationId, int userId) {
        return null;
    }
}
