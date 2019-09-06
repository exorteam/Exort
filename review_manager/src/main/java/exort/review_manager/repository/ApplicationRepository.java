package exort.review_manager.repository;

import exort.review_manager.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public interface ApplicationRepository extends MongoRepository<Application, Long> {

    Collection<String> ALL_STATES = Arrays.asList(
            Application.STATE_ACCEPTED,
            Application.STATE_CANCELED,
            Application.STATE_PENDING,
            Application.STATE_REFUSED);
    Collection<String> STATE_ACCEPTED = Collections.singleton(Application.STATE_ACCEPTED);
    Collection<String> STATE_CANCELED = Collections.singleton(Application.STATE_CANCELED);
    Collection<String> STATE_PENDING = Collections.singleton(Application.STATE_PENDING);
    Collection<String> STATE_REFUSED = Collections.singleton(Application.STATE_REFUSED);

    Collection<String> ALL_TYPES = Arrays.asList(
            Application.TYPE_ACTIVITY_SIGN_UP,
            Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP,
            Application.TYPE_ASSOCIATION_INFO);
    Collection<String> TYPE_ACTIVITY_SIGN_UP = Collections.singleton(Application.TYPE_ACTIVITY_SIGN_UP);
    Collection<String> TYPE_ASSOCIATION_MEMBER_SIGN_UP = Collections.singleton(Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP);
    Collection<String> TYPE_ASSOCIATION_INFO = Collections.singleton(Application.TYPE_ASSOCIATION_INFO);

    Sort BY_CREATED_TIME = Sort.by(Sort.Direction.DESC, "created_time");
    Sort BY_HANDLED_TIME = Sort.by(Sort.Direction.DESC, "handled_time");

    Page<Application> findByApplicantIdAndTypeInAndStateIn(Long applicantId, Collection<String> types, Collection<String> states, Pageable pageable);

    Page<Application> findByTypeInAndStateIn(Collection<String> types, Collection<String> states, Pageable pageable);

    @Query("{'type': 'ActivitySignUp', 'details.activity_id': ?0, 'state': {$in: ?1}}")
    Page<Application> findActivitySignUpByActivityIdAndStateIn(String activityId, Collection<String> states, Pageable pageable);

    @Query("{'type': 'AssociationMemberSignUp', 'details.association_id': ?0, 'state': {$in: ?1}}")
    Page<Application> findAssociationMemberSignUpByAssociationIdAndStateIn(String associationId, Collection<String> states, Pageable pageable);

    @Query("{'type': 'AssociationMemberSignUp', 'details.association_id': ?0, 'details.department_ids': ?1, 'state': {$in: ?2}}")
    Page<Application> findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(String associationId, Integer departmentId, Collection<String> states, Pageable pageable);

}
