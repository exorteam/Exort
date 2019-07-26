package exort.api.http.activity.service;

import com.google.common.reflect.TypeToken;
import exort.api.http.activity.entity.*;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.CallbackParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpMethod;

@Service
public class ActivityServiceImpl extends RestTemplate implements ActivityService {

    @Value("${exort.activity.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.activity.endpoint:localhost}")
    public void setEndpoint(String endpoint) { super.setEndpoint(endpoint); }

    @Override
    public ApiResponse<Activity> createNewActivity(Activity activity) {
        return request(new TypeToken<Activity>(){}, activity, HttpMethod.POST, "/activities");
    }

    @Override
    public ApiResponse<Activity> updateActivity(Activity activity, String activityid) {
        return request(new TypeToken<Activity>(){}, activity, HttpMethod.PUT, "/activities/{activityid}", activityid);
    }

    @Override
    public ApiResponse<PagedData<Activity>> getActivities(Filter select, PageQuery pageQuery) {
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