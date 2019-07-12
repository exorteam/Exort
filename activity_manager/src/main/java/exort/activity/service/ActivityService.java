package exort.activity.service;

import exort.activity.entity.Activity;
import exort.activity.entity.Response;
import exort.activity.entity.Select;

import java.util.List;

public interface ActivityService {

    Response changeActivityState(Long activityid, String type);

    Response addUserIds(Long activityid, List<Long> userIds, int type);

    Response removeParticipants(Long activityid, List<Long> participantIds);

    Response getActivity(Long acticityid);

    Response getActivityUserIds(int pagesize, int pagenum, Long activityid, Long userId, int type);

    Response upsertActivity(Activity activity);

    Response getActivities(Select select, int pagesize, int pagenum, int sortby);
}
