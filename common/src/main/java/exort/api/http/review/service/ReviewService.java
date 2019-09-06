package exort.api.http.review.service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.details.ActivitySignUp;
import exort.api.http.review.entity.details.AssociationInfo;
import exort.api.http.review.entity.details.AssociationMemberSignUp;
import exort.api.http.review.entity.receipt.AssociationMemberReceipt;
import exort.api.http.review.entity.receipt.NormalReceipt;

public interface ReviewService {

    String STATE_PENDING = "pending";
    String STATE_ACCEPTED = "accepted";
    String STATE_REFUSED = "refused";
    String STATE_CANCELED = "canceled";

    String TYPE_ACTIVITY_SIGN_UP = "ActivitySignUp";
    String TYPE_ASSOCIATION_MEMBER_SIGN_UP = "AssociationMemberSignUp";
    String TYPE_ASSOCIATION_INFO = "AssociationInfo";

    String ACCEPT = "accept";
    String REFUSE = "refuse";
    String CANCEL = "cancel";

    ApiResponse<Application> getApplication(Long id);

    ApiResponse<PagedData<Application>> listApplication(Long applicantId, String type, String state, PageQuery pageQuery);

    ApiResponse<PagedData<Application<AssociationInfo, NormalReceipt>>>
    listAssociationInfo(String state, PageQuery pageQuery);

    ApiResponse<PagedData<Application<AssociationMemberSignUp, AssociationMemberReceipt>>>
        listAssociationMemberSignUp(String associationId, Integer departmentId, String state, PageQuery pageQuery);

    ApiResponse<PagedData<Application<ActivitySignUp, NormalReceipt>>>
        listActivitySignUp(String activityId, String state, PageQuery pageQuery);

    ApiResponse<Application<AssociationInfo, NormalReceipt>>
        createAssociation(Long applicantId, AssociationInfo info);

    ApiResponse<Application<AssociationMemberSignUp, AssociationMemberReceipt>>
        signUpAssociationMember(Long applicantId, AssociationMemberSignUp signUp);

    ApiResponse<Application<ActivitySignUp, NormalReceipt>>
        signUpActivity(Long applicantId, ActivitySignUp signUp);

    ApiResponse<Application> operate(Long id, Long operator, String operation, Object receipt);

    ApiResponse<Application> accept(Long id, Long operator, Object receipt);

    ApiResponse<Application> refuse(Long id, Long operator, Object receipt);

    ApiResponse<Application> cancel(Long id, Long operator, Object receipt);

}
