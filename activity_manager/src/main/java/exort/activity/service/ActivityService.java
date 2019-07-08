package exort.activity.service;

import exort.activity.entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Activity createActivity(Map<String, String> maps);

    boolean modifyActivity(Activity activity);

    Activity getActivity(Long activity_id);

    boolean publish(Long activity_id);

    boolean withdraw(Long activity_id);

    boolean addParticipants(Long activity_id, List<Long> participant_ids);

    boolean removeParticipants(Long activity_id, List<Long> participant_ids);
}
