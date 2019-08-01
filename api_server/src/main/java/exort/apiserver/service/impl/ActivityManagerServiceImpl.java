package exort.apiserver.service.impl;

import com.google.common.reflect.TypeToken;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
//import exort.api.http.activity.service.ActivityService;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.CallbackParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.apiserver.service.ActivityManagerService;

@Service
public class ActivityManagerServiceImpl extends RestTemplate implements ActivityManagerService {

    @Value("${exort.activity.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.activity.endpoint:localhost}")
    public void setEndpoint(String endpoint) { super.setEndpoint(endpoint); }

    @Override
    public ApiResponse<Activity> createNewActivity(Activity activity) {
//        System.out.println("api service:");
//        System.out.println(activity.getImage());

//        System.out.println(request(new TypeToken<Activity>(){}, activity, HttpMethod.POST, "/activities"));
        return request(new TypeToken<Activity>(){}, activity, HttpMethod.POST, "/activities");
    }

    @Override
    public ApiResponse<Activity> updateActivity(Activity activity, String activityid) {
        return request(new TypeToken<Activity>(){}, activity, HttpMethod.PUT, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<PagedData<Activity>> getActivities(Filter select, PageQuery pageQuery) {
        System.out.println(pageQuery.toString());
        return request(new TypeToken<PagedData<Activity>>(){}, select, HttpMethod.GET, pageQuery, "/activities");
    }

    @Override
    public ApiResponse<Object> publishActivity(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.PUT, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<Object> addParticipants(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.POST, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<Object> addRealParticipants(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.POST, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<Object> deleteParticipants(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.DELETE, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<PagedData<Integer>> getActivityParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<PagedData<Integer>>(){}, requestActivity, HttpMethod.GET, pageQuery, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<PagedData<Integer>> getActivityRealParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<PagedData<Integer>>(){}, requestActivity, HttpMethod.GET, pageQuery, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<Object> acceptSignup(CallbackParam<Signup> operation) {
        return request(new TypeToken<Object>(){}, operation, HttpMethod.POST, "/callback/acceptsignup");
    }

    @Override
    public ApiResponse<Activity> getActivity(String activityid) {
        return request(new TypeToken<Activity>(){}, HttpMethod.GET, "/activities/{activityid}", activityid);
    }
}