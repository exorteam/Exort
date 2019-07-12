package exort.activity.dao;

import exort.activity.entity.Activity;
import exort.activity.entity.Response;
import exort.activity.entity.Select;

public interface ActivityDao {

    Response update(Activity activity);

    Activity getActivity(Long id);

    Response getActivityUserIds(int pagesize, int pagenum, Long activityid, Long userId, int type);

    Response checkUserId(Long activityid, Long userId, int type);

    Response selectActivities(Select select, int pagesize, int pagenum, int sortby);
}
