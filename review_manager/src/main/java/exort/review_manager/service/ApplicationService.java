package exort.review_manager.service;

import exort.review_manager.entity.Application;
import org.springframework.data.domain.Page;

public interface ApplicationService {

    Application get(Long id);

    Page<Application> list(
            Long applicantId, String type, String state,
            int pageNum, int pageSize, String sortBy);

    Page<Application> listActivitySignUp(
            String activityId, String state,
            int pageNum, int pageSize, String sortBy);

    Page<Application> listAssociationMemberSignUp(
            String associationId, Integer departmentId, String state,
            int pageNum, int pageSize, String sortBy);

    Application create(Long applicantId, String type, Object details);

    Application setState(Long id, String state, Long operator, Object receipt);

}
