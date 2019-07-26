package exort.api.http.activity.service;

import exort.api.http.activity.entity.*;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.CallbackParam;

public interface ActivityService {

    public ApiResponse<Activity> createNewActivity(Activity activity);

    public ApiResponse<Activity> updateActivity(Activity activity, String activityId);

    public ApiResponse<PagedData<Activity>> getActivities(Filter filter, PageQuery pageQuery);

    public ApiResponse<Object> publishActivity(String activityId, RequestActivity requestActivity);

    public ApiResponse<Object> addParticipants(String activityId, RequestActivity requestActivity);

    public ApiResponse<Object> addRealParticipants(String activityId, RequestActivity requestActivity);

    public ApiResponse<Object> deleteParticipants(String activityId, RequestActivity requestActivity);

    public ApiResponse<PagedData<Integer>> getActivityParticipants(PageQuery pageQuery, String activityId, RequestActivity requestActivity);

    public ApiResponse<PagedData<Integer>> getActivityRealParticipants(PageQuery pageQuery, String activityId, RequestActivity requestActivity);

    public ApiResponse<Object> acceptSignup(CallbackParam<Signup> operation);

    public ApiResponse<Activity> getActivity(String activityId);

}
