package exort.api.http.activity.service;

import com.google.common.reflect.TypeToken;
import exort.api.http.activity.entity.*;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface ActivityService {

    public ApiResponse<Activity> createNewActivity(Activity activity);

    public ApiResponse<Activity> updateActivity(Activity activity, String activityid);

    public ApiResponse<PageList<Activity>> getActivities(Select select, PageQuery pageQuery);

    public ApiResponse<Object> publishActivity(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> addParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> addRealParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> deleteParticipants(String activityid, RequestActivity requestActivity);

    public ApiResponse<PageList<Integer>> getActivityParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity);

    public ApiResponse<PageList<Integer>> getActivityRealParticipants(PageQuery pageQuery, String activityid, RequestActivity requestActivity);

    public ApiResponse<Object> acceptSignup(Operation operation);

    public ApiResponse<Activity> getActivity(String activityid);

    public class Response<T> {

        private T data;
        private String error;
        private String message;

    }
}
