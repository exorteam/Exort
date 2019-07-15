package exort.activity.service;

import exort.activity.entity.Activity;
import exort.activity.entity.Response;
import exort.activity.entity.Select;

import java.util.List;

public interface ActivityService {

    Response changeActivityState(String activityid, String type);

    Response addUserIds(String activityid, List<Long> userIds, int type);

    Response removeParticipants(String activityid, List<Long> participantIds);

    Response getActivity(String acticityid);

    Response getActivityUserIds(int pagesize, int pagenum, String activityid, Long userId, int type);

    Response upsertActivity(Activity activity);

    Response getActivities(Select select, int pagesize, int pagenum, int sortby);
}
