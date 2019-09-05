package exort.apiserver.service;

import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

public interface ActivityManagerService {

    public ApiResponse<Activity> createNewActivity(Activity activity);

    public ApiResponse<Activity> updateActivity(Activity activity, String activityid);

    public ApiResponse<PagedData<Activity>> getActivities(Filter select, PageQuery pageQuery);

    public ApiResponse<Object> publishActivity(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> addParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> addRealParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> deleteParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<PagedData<Integer>> getActivityParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity);

    public ApiResponse<PagedData<Integer>> getActivityRealParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity);

    //public ApiResponse<Object> acceptSignup(CallbackParam<Signup> operation);

    public ApiResponse<Activity> getActivity(String activityid);
}
