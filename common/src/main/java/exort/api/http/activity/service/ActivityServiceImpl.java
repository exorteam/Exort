package exort.api.http.activity.service;

import com.google.common.reflect.TypeToken;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
import exort.api.http.activity.service.ActivityService;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

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
        System.out.println(pageQuery.toString());
        return request(new TypeToken<PagedData<Activity>>(){}, select, HttpMethod.GET, pageQuery, "/activities");
    }

    @Override
    public ApiResponse<Object> publishActivity(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.PUT, "/activities/{activityid}/state", activityid);
    }

    @Override
    public ApiResponse<Object> addParticipants(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.POST, "/activities/{activityid}/participants", activityid);
    }

    @Override
    public ApiResponse<Object> addRealParticipants(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.POST, "/activities/{activityid}/realparticipants", activityid);
    }

    @Override
    public ApiResponse<Object> deleteParticipants(String activityid, RequestActivity requestActivity) {
        return request(new TypeToken<Object>(){}, requestActivity, HttpMethod.DELETE, "/activities/{activityid}/participants", activityid);
    }

    @Override
    public ApiResponse<Activity> getActivity(String activityid) {
        return request(new TypeToken<Activity>(){}, HttpMethod.GET, "/activities/{activityid}", activityid);
    }
}
