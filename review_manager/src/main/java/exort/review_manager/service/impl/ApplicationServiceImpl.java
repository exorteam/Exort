package exort.review_manager.service.impl;

import exort.review_manager.entity.Application;
import exort.review_manager.repository.ApplicationRepository;
import exort.review_manager.repository.IdGeneratorRepository;
import exort.review_manager.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private IdGeneratorRepository idgen;

    @Autowired
    private ApplicationRepository ar;

    private Long nextId() {
        return idgen.nextId("ApplicationService");
    }

    @Override
    public Application get(Long id) {
        return ar.findById(id).orElse(null);
    }

    @Override
    public Page<Application> list(Long applicantId, String type, String state, int pageNum, int pageSize, String sortBy) {
        Collection types = (type == null ? ar.ALL_TYPES : Collections.singleton(type));
        Collection states = (state == null ? ar.ALL_STATES : Collections.singleton(state));
        Sort sort = ("handledTime".equals(sortBy) ? ar.BY_HANDLED_TIME : ar.BY_CREATED_TIME);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        if (applicantId == null) {
            return ar.findByTypeInAndStateIn(types, states, pageable);
        } else {
            return ar.findByApplicantIdAndTypeInAndStateIn(applicantId, types, states, pageable);
        }
    }

    @Override
    public Page<Application> listActivitySignUp(String activityId, String state, int pageNum, int pageSize, String sortBy) {
        Collection states = (state == null ? ar.ALL_STATES : Collections.singleton(state));
        Sort sort = ("handledTime".equals(sortBy) ? ar.BY_HANDLED_TIME : ar.BY_CREATED_TIME);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        return ar.findActivitySignUpByActivityIdAndStateIn(activityId, states, pageable);
    }

    @Override
    public Page<Application> listAssociationMemberSignUp(String associationId, Integer departmentId, String state, int pageNum, int pageSize, String sortBy) {
        Collection states = (state == null ? ar.ALL_STATES : Collections.singleton(state));
        Sort sort = ("handledTime".equals(sortBy) ? ar.BY_HANDLED_TIME : ar.BY_CREATED_TIME);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        if (departmentId == null) {
            return ar.findAssociationMemberSignUpByAssociationIdAndStateIn(associationId, states, pageable);
        } else {
            return ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(associationId, departmentId, states, pageable);
        }
    }

    @Override
    public Application create(Long applicantId, String type, Object details) {
        Long id = this.nextId();
        Application application = new Application(id, applicantId, type, details);
        return ar.insert(application);
    }

    @Override
    public Application setState(Long id, String state, Long operator, Object receipt) {
        Application application = ar.findById(id).orElse(null);
        if (application != null) {
            application.setState(state);
            application.setHandledTime(new Date());
            application.setOperator(operator);
            application.setReceipt(receipt);
            return ar.save(application);
        }
        return null;
    }
}
