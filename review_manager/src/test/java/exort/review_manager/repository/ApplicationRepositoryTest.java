package exort.review_manager.repository;

import exort.review_manager.entity.ActivitySignUp;
import exort.review_manager.entity.Application;
import exort.review_manager.entity.AssociationInfo;
import exort.review_manager.entity.AssociationMemberSignUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository ar;

    @Autowired
    private MongoTemplate mt;

    @BeforeEach
    void setUp() {
        mt.dropCollection(Application.class);
        Application activitySignUp1 = new Application(1L, 1L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity1"));
        Application activitySignUp2 = new Application(2L, 1L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity2"));
        Application activitySignUp3 = new Application(3L, 2L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity2"));
        Application associationInfo1 = new Application(4L, 1L,
                Application.TYPE_ASSOCIATION_INFO,
                new AssociationInfo("asso1", "desc1", ""));
        Application associationInfo2 = new Application(5L, 2L,
                Application.TYPE_ASSOCIATION_INFO,
                new AssociationInfo("asso2", "desc2", ""));
        Application associationMemberSignUp1 = new Application(6L, 1L,
                Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP,
                new AssociationMemberSignUp("asso1", Arrays.asList(1, 2)));
        Application associationMemberSignUp2 = new Application(7L, 1L,
                Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP,
                new AssociationMemberSignUp("asso1", Arrays.asList(2, 3)));
        Application associationMemberSignUp3 = new Application(8L, 2L,
                Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP,
                new AssociationMemberSignUp("asso2", Arrays.asList(1, 3)));
        mt.save(activitySignUp1);
        activitySignUp2.setState(Application.STATE_ACCEPTED);
        activitySignUp2.setHandledTime(new Date());
        mt.save(activitySignUp2);
        mt.save(activitySignUp3);
        mt.save(associationInfo1);
        mt.save(associationInfo2);
        mt.save(associationMemberSignUp1);
        mt.save(associationMemberSignUp2);
        associationMemberSignUp3.setState(Application.STATE_ACCEPTED);
        associationMemberSignUp3.setHandledTime(new Date());
        mt.save(associationMemberSignUp3);
    }

    @Test
    void insert() {
        // insert a new entity
        Application a = new Application(999L, 1L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity1"));
        ar.insert(a);
        Application b = mt.findById(999L, Application.class);
        assertEquals(a, b);
        // insert a entity with existed id
        final Application c = new Application(999L, 2L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity2"));
        assertThrows(DuplicateKeyException.class, () -> {
            ar.insert(c);
        });
    }

    @Test
    void save() {
        // insert a new entity
        Application a = new Application(999L, 1L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity1"));
        ar.save(a);
        Application b = mt.findById(999L, Application.class);
        assertEquals(a, b);
        // save a entity with existed id
        final Application c = new Application(999L, 2L,
                Application.TYPE_ACTIVITY_SIGN_UP,
                new ActivitySignUp("activity2"));
        ar.save(c);
        b = mt.findById(999L, Application.class);
        assertEquals(c, b);
    }

    @Test
    void findApplications() {
        Pageable pageable = PageRequest.of(0, 10, ar.BY_CREATED_TIME);

        assertEquals(8, ar.findByTypeInAndStateIn(
                ar.ALL_TYPES, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(3, ar.findByTypeInAndStateIn(
                ar.TYPE_ACTIVITY_SIGN_UP, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(2, ar.findByTypeInAndStateIn(
                ar.TYPE_ASSOCIATION_INFO, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(2, ar.findByTypeInAndStateIn(
                ar.ALL_TYPES, ar.STATE_ACCEPTED, pageable
        ).getTotalElements());

        assertEquals(1, ar.findByTypeInAndStateIn(
                ar.TYPE_ASSOCIATION_MEMBER_SIGN_UP, ar.STATE_ACCEPTED, pageable
        ).getTotalElements());

        assertEquals(5, ar.findByApplicantIdAndTypeInAndStateIn(
                1L, ar.ALL_TYPES, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(1, ar.findByApplicantIdAndTypeInAndStateIn(
                2L, ar.TYPE_ASSOCIATION_MEMBER_SIGN_UP, ar.STATE_ACCEPTED, pageable
        ).getTotalElements());

        assertEquals(0, ar.findByApplicantIdAndTypeInAndStateIn(
                1L, ar.TYPE_ASSOCIATION_MEMBER_SIGN_UP, ar.STATE_ACCEPTED, pageable
        ).getTotalElements());
    }

    @Test
    void findActivitySignUpByTypeInAndStateIn() {
        Pageable pageable = PageRequest.of(0, 10, ar.BY_CREATED_TIME);

        assertEquals(1, ar.findActivitySignUpByActivityIdAndStateIn(
                "activity1", ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(2, ar.findActivitySignUpByActivityIdAndStateIn(
                "activity2", ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(1, ar.findActivitySignUpByActivityIdAndStateIn(
                "activity2", ar.STATE_ACCEPTED, pageable
        ).getTotalElements());
    }

    @Test
    void findAssociationMemberSignUpByAssociationIdAndStateIn() {
        Pageable pageable = PageRequest.of(0, 10, ar.BY_CREATED_TIME);

        assertEquals(2, ar.findAssociationMemberSignUpByAssociationIdAndStateIn(
                "asso1", ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(1, ar.findAssociationMemberSignUpByAssociationIdAndStateIn(
                "asso2", ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(0, ar.findAssociationMemberSignUpByAssociationIdAndStateIn(
                "asso1", ar.STATE_ACCEPTED, pageable
        ).getTotalElements());

        assertEquals(1, ar.findAssociationMemberSignUpByAssociationIdAndStateIn(
                "asso2", ar.STATE_ACCEPTED, pageable
        ).getTotalElements());
    }

    @Test
    void findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn() {
        Pageable pageable = PageRequest.of(0, 10, ar.BY_CREATED_TIME);

        assertEquals(1, ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(
                "asso1", 1, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(2, ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(
                "asso1", 2, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(1, ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(
                "asso2", 1, ar.ALL_STATES, pageable
        ).getTotalElements());

        assertEquals(0, ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(
                "asso1", 2, ar.STATE_ACCEPTED, pageable
        ).getTotalElements());

        assertEquals(1, ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(
                "asso2", 1, ar.STATE_ACCEPTED, pageable
        ).getTotalElements());

        assertEquals(0, ar.findAssociationMemberSignUpByAssociationIdAndDepartmentIdAndStateIn(
                "asso2", 2, ar.ALL_STATES, pageable
        ).getTotalElements());
    }

}