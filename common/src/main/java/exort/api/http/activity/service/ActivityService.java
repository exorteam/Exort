package exort.api.http.activity.service;

import exort.api.http.activity.entity.*;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.CallbackParam;

public interface ActivityService {

    public ApiResponse<Activity> createNewActivity(Activity activity);

    public ApiResponse<Activity> updateActivity(Activity activity, String activityid);

    public ApiResponse<PagedData<Activity>> getActivities(Select select, PageQuery pageQuery);

    public ApiResponse<Object> publishActivity(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> addParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> addRealParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> deleteParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<PagedData<Integer>> getActivityParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity);

    public ApiResponse<PagedData<Integer>> getActivityRealParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> acceptSignup(CallbackParam<Signup> operation);

    public ApiResponse<Activity> getActivity(String activityid);

}
