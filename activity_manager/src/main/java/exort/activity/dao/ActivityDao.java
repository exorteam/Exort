package exort.activity.dao;

import exort.activity.entity.Activity;
import exort.activity.entity.PageList;
import exort.activity.entity.Response;
import exort.activity.entity.Select;

import java.util.List;

public interface ActivityDao {

    Activity update(Activity activity);

    Activity getActivity(String id);

    PageList<Integer> getActivityUserIds(int pagesize, int pagenum, String activityid, int type);

    List<Integer> checkUserId(String activityid, int userId, int type);

    PageList<Activity> selectActivities(Select select, int pagesize, int pagenum, int sortby);
}
