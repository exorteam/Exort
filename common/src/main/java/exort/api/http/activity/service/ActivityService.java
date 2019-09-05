package exort.api.http.activity.service;

import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

public interface ActivityService {

    public ApiResponse<Activity> createNewActivity(Activity activity);

    public ApiResponse<Activity> updateActivity(Activity activity, String activityid);

    public ApiResponse<PagedData<Activity>> getActivities(Filter select, PageQuery pageQuery);

    public ApiResponse<Activity> publishActivity(String activityid, RequestActivity requestActivity);

    public ApiResponse<Activity> addParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Activity> addRealParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Activity> deleteParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Activity> getActivity(String activityid);
}
