package exort.activity.dao;

import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.Activity;
import exort.api.http.common.entity.PagedData;

import java.util.List;

public interface ActivityDao {

    Activity update(Activity activity);

    Activity getActivity(String id);

    PagedData<Integer> getActivityUserIds(String activityid, Integer pagesize, Integer pagenum, int type);

    List<Integer> checkUserId(String activityid, Integer userId, int type);

    PagedData<Activity> selectActivities(Filter filter, Integer pagesize, Integer pagenum, String sortBy);
}
