package exort.activity.service;

import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.Activity;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

import java.util.List;

public interface ActivityService {

    Activity upsertActivity(Activity activity);

    PagedData<Activity> getActivities(Filter filter, PageQuery pageQuery);

    boolean changeActivityState(String activityid, String type);

    boolean addUserIds(String activityid, List<Integer> userIds, int type);

    boolean removeParticipants(String activityid, List<Integer> participantIds);

    PagedData<Integer> getActivityUserIds(String activityid,  PageQuery pageQuery, Integer userId, int type);

    Activity getActivity(String acticityid);
}
