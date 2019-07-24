package exort.activity.service;

import exort.activity.entity.Activity;
import exort.activity.entity.PageList;
import exort.activity.entity.Response;
import exort.activity.entity.Select;

import java.util.List;

public interface ActivityService {

    Activity upsertActivity(Activity activity);

    PageList<Activity> getActivities(Select select, int pagesize, int pagenum, int sortby);

    boolean changeActivityState(String activityid, String type);

    boolean addUserIds(String activityid, List<Integer> userIds, int type);

    boolean removeParticipants(String activityid, List<Integer> participantIds);

    PageList<Integer> getActivityUserIds(int pagesize, int pagenum, String activityid, int userId, int type);

    Activity getActivity(String acticityid);
}
