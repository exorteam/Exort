package exort.review_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.service.ActivityService;
import exort.api.http.assomgr.entity.Association;
import exort.api.http.assomgr.service.AssociationManagerService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.member.entity.InitAssociationInfo;
import exort.api.http.member.entity.UserId;
import exort.api.http.member.service.AssoMemService;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationFilter;
import exort.api.http.review.entity.Operation;
import exort.api.http.review.entity.details.ActivitySignUp;
import exort.api.http.review.entity.details.AssociationInfo;
import exort.api.http.review.entity.details.AssociationMemberSignUp;
import exort.api.http.review.entity.receipt.AssociationMemberReceipt;
import exort.review_manager.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AssoMemService assoMemService;

    @Autowired
    private AssociationManagerService associationManagerService;

    @Autowired
    private ApplicationService as;

    public final static List<String> availableStates = Arrays.asList(
            exort.review_manager.entity.Application.STATE_ACCEPTED,
            exort.review_manager.entity.Application.STATE_PENDING,
            exort.review_manager.entity.Application.STATE_CANCELED,
            exort.review_manager.entity.Application.STATE_REFUSED
    );

    public final static List<String> availableTypes = Arrays.asList(
            exort.review_manager.entity.Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP,
            exort.review_manager.entity.Application.TYPE_ACTIVITY_SIGN_UP,
            exort.review_manager.entity.Application.TYPE_ASSOCIATION_INFO
    );

    public final static String ACCEPT = "accept";
    public final static String REFUSE = "refuse";
    public final static String CANCEL = "cancel";
    public final static List<String> availableOperation = Arrays.asList(
            ACCEPT,
            REFUSE,
            CANCEL);

    private Application toDTO(exort.review_manager.entity.Application application) {
        Application<Object, Object> dto = new Application<>();
        dto.setId(application.getId());
        dto.setType(application.getType());
        dto.setApplicantId(application.getApplicantId());
        dto.setState(application.getState());
        dto.setCreatedTime(application.getCreatedTime());
        dto.setHandledTime(application.getHandledTime());
        dto.setMaterialIds(application.getMaterialIds());
        dto.setDetails(application.getDetails());
        dto.setOperator(application.getOperator());
        dto.setReceipt(application.getReceipt());
        return dto;
    }

    private PagedData<Application> toDTO(Page<exort.review_manager.entity.Application> page) {
        List<Application> res = new ArrayList<>();
        for (exort.review_manager.entity.Application application:page.getContent()) {
            res.add(toDTO(application));
        }
        return new PagedData<>(page.getNumber(), page.getSize(),
                page.getTotalElements(), res);
    }

    @GetMapping("/applications/{id}")
    public ApiResponse<Application> getApplication(@PathVariable("id") Long id) {
        exort.review_manager.entity.Application application = as.get(id);
        if (application == null) {
            throw new ApiError(404, "notFound", "Application #" + id + " not found");
        }
        return new ApiResponse<>(toDTO(application));
    }

    @GetMapping("/applications")
    public ApiResponse<PagedData<Application>> listApplications(@RequestBody ApplicationFilter filter, PageQuery pageQuery) {
        String state = availableStates.contains(filter.getState()) ? filter.getState() : null;
        String type = availableTypes.contains(filter.getType()) ? filter.getType() : null;
        Object details = filter.getDetails();
        pageQuery = PageQuery.relocate(pageQuery, 20, 100);

        if (exort.review_manager.entity.Application.TYPE_ACTIVITY_SIGN_UP.equals(type) && details != null) {
            ActivitySignUp signUp = new ObjectMapper().convertValue(details, ActivitySignUp.class);
            String activityId = signUp.getActivityId();
            if (activityId != null) {
                return new ApiResponse<>(toDTO(as.listActivitySignUp(
                        activityId, state,
                        pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getSortBy())));
            }
        }
        if (exort.review_manager.entity.Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP.equals(type) && details != null) {
            AssociationMemberSignUp signUp = new ObjectMapper().convertValue(details, AssociationMemberSignUp.class);
            String associationId = signUp.getAssociationId();
            Integer departmentId = signUp.getDepartmentId() == null ? null : signUp.getDepartmentId().get(0);
            if (associationId != null) {
                return new ApiResponse<>(toDTO(as.listAssociationMemberSignUp(
                        associationId, departmentId, state,
                        pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getSortBy())));
            }
        }
        return new ApiResponse<>(toDTO(as.list(
                filter.getApplicantId(), type, state,
                pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getSortBy())));
    }

    @PostMapping("/applications")
    public ApiResponse<Application> createApplication(@RequestBody Application application) {
        Long applicantId = application.getApplicantId();
        String type = application.getType();
        Object details = application.getDetails();
        Object signUp;

        if (applicantId == null) {
            throw new ApiError(400, "badApplicantId", "Applicant id is required.");
        }

        switch (type) {
            case exort.review_manager.entity.Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP: {
                AssociationMemberSignUp input = new ObjectMapper().convertValue(details, AssociationMemberSignUp.class);
                String associationId = input.getAssociationId();
                List<Integer> departmentId = input.getDepartmentId();

                if (associationId == null || associationManagerService.getAssociation(associationId).getData() == null) {
                    throw new ApiError(400, "badAssociationId", "Association not specified or not existed.");
                }

                signUp = new exort.review_manager.entity.AssociationMemberSignUp(associationId, departmentId);
                break;
            }
            case exort.review_manager.entity.Application.TYPE_ACTIVITY_SIGN_UP: {
                ActivitySignUp input = new ObjectMapper().convertValue(details, ActivitySignUp.class);
                String activityId = input.getActivityId();
                Activity activity;
                if (activityId == null || (activity = activityService.getActivity(activityId).getData()) == null) {
                    throw new ApiError(400, "badActivityId", "Activity not specified or not existed.");
                }

                // skip review
                if (!activity.isIfReview()) {
                    ApiResponse res = activityService.addParticipants(activityId, new RequestActivity(0, null, Arrays.asList(applicantId.intValue()), null));
                    if (res.getData() == null) {
                        throw new ApiError(500, res.getError(), res.getMessage());
                    }
                    return new ApiResponse<>(application);
                }

                signUp = new exort.review_manager.entity.ActivitySignUp(activityId);
                break;
            }
            case exort.review_manager.entity.Application.TYPE_ASSOCIATION_INFO: {
                AssociationInfo input = new ObjectMapper().convertValue(details, AssociationInfo.class);
                String name = input.getName();
                if (name == null) {
                    throw new ApiError(400 ,"badName", "Name not specified.");
                }
                List<String> tags = input.getTags() == null ? new ArrayList<>() : input.getTags();
                signUp = new exort.review_manager.entity.AssociationInfo(name, input.getDescription(), input.getLogo(), tags);
                break;
            }
        default:
            throw new ApiError(400, "badType", "Type should be one of " + availableTypes);
        }
        exort.review_manager.entity.Application res = as.create(applicantId, type, signUp);
        return new ApiResponse<>(toDTO(res));
    }

    @PutMapping("/applications/{id}/state")
    public ApiResponse<Application> setState(@PathVariable("id") Long id, @RequestBody Operation input) {
        exort.review_manager.entity.Application application = as.get(id);
        Long operator = input.getOperator();
        String operation = input.getOperation();
        if (application == null) {
            throw new ApiError(404, "notFound", "Application #" + id + " not found");
        }
        if (operator == null) {
            throw new ApiError(400, "badOperator", "Operator not specified.");
        }
        if (!availableOperation.contains(operation)) {
            throw new ApiError(400, "badOperation", "Operation should be one of " + availableOperation);
        }

        Long applicantId = application.getApplicantId();
        String state = null;
        switch (operation) {
            case ApplicationController.ACCEPT: {
                state = exort.review_manager.entity.Application.STATE_ACCEPTED;

                switch (application.getType()) {
                    case exort.review_manager.entity.Application.TYPE_ACTIVITY_SIGN_UP: {
                        String activityId = new ObjectMapper().convertValue(application.getDetails(), exort.review_manager.entity.ActivitySignUp.class).getActivityId();
                        ApiResponse res = activityService.addParticipants(activityId, new RequestActivity(0, null, Arrays.asList(applicantId.intValue()), null));
                        if (res.getData() == null) {
                            throw new ApiError("notFound".equals(res.getError()) ? 404 : 500, res.getError(), res.getMessage());
                        }
                        break;
                    }
                    case exort.review_manager.entity.Application.TYPE_ASSOCIATION_MEMBER_SIGN_UP: {
                        String associationId = new ObjectMapper().convertValue(application.getDetails(), exort.review_manager.entity.AssociationMemberSignUp.class).getAssociationId();
                        Integer departmentId = new ObjectMapper().convertValue(input.getReceipt(), AssociationMemberReceipt.class).getDepartmentId();
                        departmentId = (departmentId == null ? assoMemService.MEMBER_ID : departmentId);
                        ApiResponse res = assoMemService.addOneToDepartment(associationId, departmentId, new UserId(applicantId.intValue()));
                        if (res.getData() == null) {
                            throw new ApiError("DepartmentNotFound".equals(res.getError()) ? 404: 500, res.getError(), res.getMessage());
                        }
                        break;
                    }
                    case exort.review_manager.entity.Application.TYPE_ASSOCIATION_INFO: {
                        exort.review_manager.entity.AssociationInfo details = new ObjectMapper().convertValue(application.getDetails(), exort.review_manager.entity.AssociationInfo.class);
                        exort.api.http.assomgr.entity.AssociationInfo info = new exort.api.http.assomgr.entity.AssociationInfo();
                        info.setName(details.getName());
                        info.setDescription(details.getDescription());
                        info.setLogo(details.getLogo());
                        info.setTags(details.getTags());
                        ApiResponse<Association> res = associationManagerService.createAssociation(info);
                        if (res.getData() == null) {
                            throw new ApiError(500, res.getError(), res.getMessage());
                        }
                        InitAssociationInfo initInfo = new InitAssociationInfo(applicantId.intValue(), res.getData().getId());
                        ApiResponse res2 = assoMemService.initDepartment(initInfo);
                        if (res2.getData() == null) {
                            throw new ApiError(500, res.getError(), res.getMessage());
                        }
                        break;
                    }
                    default:
                        throw new RuntimeException("Application type error" + application.getType());
                }
                break;
            }
            case ApplicationController.REFUSE: {
                state = exort.review_manager.entity.Application.STATE_REFUSED;
                break;
            }
            case ApplicationController.CANCEL: {
                state = exort.review_manager.entity.Application.STATE_CANCELED;
                break;
            }
        }

        return new ApiResponse<>(toDTO(as.setState(id, state, operator, input.getReceipt())));
    }
}
