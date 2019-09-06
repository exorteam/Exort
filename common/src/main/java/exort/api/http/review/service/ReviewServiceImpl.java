package exort.api.http.review.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.ApplicationFilter;
import exort.api.http.review.entity.Operation;
import exort.api.http.review.entity.details.ActivitySignUp;
import exort.api.http.review.entity.details.AssociationInfo;
import exort.api.http.review.entity.details.AssociationMemberSignUp;
import exort.api.http.review.entity.receipt.AssociationMemberReceipt;
import exort.api.http.review.entity.receipt.NormalReceipt;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class ReviewServiceImpl extends RestTemplate implements ReviewService {

    private Application getType(@NotNull Application application) {
        switch (application.getType()) {
            case ReviewService.TYPE_ACTIVITY_SIGN_UP: {
                application.setDetails(new ObjectMapper().convertValue(application.getDetails(), ActivitySignUp.class));
                application.setReceipt(new ObjectMapper().convertValue(application.getReceipt(), NormalReceipt.class));
                break;
            }
            case ReviewService.TYPE_ASSOCIATION_INFO: {
                application.setDetails(new ObjectMapper().convertValue(application.getDetails(), AssociationInfo.class));
                application.setReceipt(new ObjectMapper().convertValue(application.getReceipt(), NormalReceipt.class));
                break;
            }
            case ReviewService.TYPE_ASSOCIATION_MEMBER_SIGN_UP: {
                application.setDetails(new ObjectMapper().convertValue(application.getDetails(), AssociationMemberSignUp.class));
                application.setReceipt(new ObjectMapper().convertValue(application.getReceipt(), AssociationMemberReceipt.class));
                break;
            }
            default:
                return null;
        }
        return application;
    }

    @Override
    public ApiResponse<Application> getApplication(Long id) {
        ApiResponse<Application> res = request(new TypeToken<Application>() { },
                HttpMethod.GET, "/applications/{id}", id);
        Application application = res.getData();
        if (application != null && getType(application) == null) {
            return new ApiResponse<>("apiError", "Unknown type for application");
        }
        return res;
    }

    @Override
    public ApiResponse<PagedData<Application>> listApplication(Long applicantId, String type, String state, PageQuery pageQuery) {
        ApiResponse<PagedData<Application>> res = request(new TypeToken<PagedData<Application>>() { },
                new ApplicationFilter<>(applicantId, type, state, null),
                HttpMethod.GET, pageQuery, "/applications");
        if (res.getData() != null) {
            for (Application application : res.getData().getContent()) {
                if (getType(application) == null) {
                    return new ApiResponse<>("apiError", "Unknown type for application");
                }
            }
        }
        return res;
    }

    @Override
    public ApiResponse<PagedData<Application<AssociationInfo, NormalReceipt>>> listAssociationInfo(String state, PageQuery pageQuery) {
        return request(new TypeToken<PagedData<Application<AssociationInfo, NormalReceipt>>>() { },
                new ApplicationFilter<>(null, TYPE_ASSOCIATION_INFO, state, null),
                HttpMethod.GET, pageQuery, "/applications");
    }

    @Override
    public ApiResponse<PagedData<Application<AssociationMemberSignUp, AssociationMemberReceipt>>> listAssociationMemberSignUp(String associationId, Integer departmentId, String state, PageQuery pageQuery) {
        return request(new TypeToken<PagedData<Application<AssociationMemberSignUp, AssociationMemberReceipt>>>() { },
                new ApplicationFilter<>(null, TYPE_ASSOCIATION_MEMBER_SIGN_UP, state, new AssociationMemberSignUp(associationId, Arrays.asList(departmentId))),
                HttpMethod.GET, pageQuery, "/applications");
    }

    @Override
    public ApiResponse<PagedData<Application<ActivitySignUp, NormalReceipt>>> listActivitySignUp(String activityId, String state, PageQuery pageQuery) {
        return request(new TypeToken<PagedData<Application<ActivitySignUp, NormalReceipt>>>() { },
                new ApplicationFilter<>(null, TYPE_ACTIVITY_SIGN_UP, state, new ActivitySignUp(activityId)),
                HttpMethod.GET, pageQuery, "/applications");
    }

    @Override
    public ApiResponse<Application<AssociationInfo, NormalReceipt>> createAssociation(Long applicantId, AssociationInfo info) {
        return request(new TypeToken<Application<AssociationInfo, NormalReceipt>>() { },
                Application.createAssociation(applicantId, info),
                HttpMethod.POST, "/applications");
    }

    @Override
    public ApiResponse<Application<AssociationMemberSignUp, AssociationMemberReceipt>> signUpAssociationMember(Long applicantId, AssociationMemberSignUp signUp) {
        return request(new TypeToken<Application<AssociationMemberSignUp, AssociationMemberReceipt>>() { },
                Application.signUpAssociationMember(applicantId, signUp),
                HttpMethod.POST, "/applications");
    }

    @Override
    public ApiResponse<Application<ActivitySignUp, NormalReceipt>> signUpActivity(Long applicantId, ActivitySignUp signUp) {
        return request(new TypeToken<Application<ActivitySignUp, NormalReceipt>>() { },
                Application.signUpActivity(applicantId, signUp),
                HttpMethod.POST, "/applications");
    }

    @Override
    public ApiResponse<Application> operate(Long id, Long operator, String operation, Object receipt) {
        if (receipt == null) {
            receipt = new NormalReceipt("");
        }
        ApiResponse<Application> res = request(new TypeToken<Application>() { },
                new Operation(operator, operation, receipt),
                HttpMethod.PUT, "/applications/{id}/state", id);
        Application application = res.getData();
        if (application != null && getType(application) == null) {
            return new ApiResponse<>("apiError", "Unknown type for application");
        }
        return res;
    }

    @Override
    public ApiResponse<Application> accept(Long id, Long operator, Object receipt) {
        return operate(id, operator, ACCEPT, receipt);
    }

    @Override
    public ApiResponse<Application> refuse(Long id, Long operator, Object receipt) {
        return operate(id, operator, REFUSE, receipt);
    }

    @Override
    public ApiResponse<Application> cancel(Long id, Long operator, Object receipt) {
        return operate(id, operator, CANCEL, receipt);
    }

}
