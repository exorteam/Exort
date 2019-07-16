package exort.activity.dao;

import exort.activity.entity.Activity;
import exort.activity.entity.Response;
import exort.activity.entity.Select;

public interface ActivityDao {

    Response update(Activity activity);

    Activity getActivity(String id);

    Response getActivityUserIds(int pagesize, int pagenum, String activityid, Long userId, int type);

    Response checkUserId(String activityid, Long userId, int type);

    Response selectActivities(Select select, int pagesize, int pagenum, int sortby);
}
