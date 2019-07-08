package exort.activity.serviceImpl;

import exort.activity.dao.ActivityDao;
import exort.activity.dao.ActivitySignUpDao;
import exort.activity.entity.Activity;
import exort.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao ad;

    @Autowired
    private ActivitySignUpDao asd;

    @Override
    public Activity createActivity(Map<String, String> maps){
        String create_time = maps.get("create_time");
        String publish_time = maps.get("publish_time");
        String last_publish_time = maps.get("last_publish_time");
        String last_modify_time = maps.get("last_modify_time");
        String signup_start_time = maps.get("signup_start_time");
        String signup_end_time = maps.get("signup_end_time");
        String start_time = maps.get("start_time");
        String end_time = maps.get("end_time");
        String title = maps.get("title");
        String content = maps.get("content");
        int state = Integer.parseInt(maps.get("state"));
        boolean review = false;
        boolean only_members = false;
        int max_participants = Integer.parseInt(maps.get("max_participants"));

        Activity activity = new Activity(create_time, publish_time, last_publish_time,
                last_modify_time, signup_start_time, signup_end_time, start_time, end_time,
                title, content, state, review, only_members, max_participants);

        ad.update(activity);
        return activity;
    }

    @Override
    public boolean modifyActivity(Activity activity){
        Activity oldActivity = ad.getActivity(activity.getId());

        oldActivity.setCreate_time(activity.getCreate_time());
        oldActivity.setPublish_time(activity.getPublish_time());
        oldActivity.setLast_publish_time(activity.getLast_publish_time());
        oldActivity.setLast_modify_time(activity.getLast_modify_time());
        oldActivity.setSignup_start_time(activity.getSignup_start_time());
        oldActivity.setSignup_end_time(activity.getSignup_end_time());
        oldActivity.setStart_time(activity.getStart_time());
        oldActivity.setEnd_time(activity.getEnd_time());
        oldActivity.setTitle(activity.getTitle());
        oldActivity.setContent(activity.getContent());
        oldActivity.setState(activity.getState());
        oldActivity.setReview(activity.isReview());
        oldActivity.setOnly_members(activity.isOnly_members());
        oldActivity.setMax_participants(activity.getMax_participants());

        ad.update(oldActivity);

        return true;
    }

    @Override
    public Activity getActivity(Long activity_id){
        return ad.getActivity(activity_id);
    }

    @Override
    public boolean publish(Long activity_id){
        try{
            Activity activity = ad.getActivity(activity_id);
            if(activity != null) {
                activity.setState(3);
                ad.update(activity);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean withdraw(Long activity_id){
        try{
            Activity activity = ad.getActivity(activity_id);
            if(activity != null) {
                activity.setState(4);
                ad.update(activity);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean addParticipants(Long activity_id, List<Long> participant_ids){
        Activity activity = ad.getActivity(activity_id);
        if(activity!=null){
            List<Long> pids = activity.getParticipants_ids();
            pids.addAll(participant_ids);
            activity.setParticipants_ids(pids);
            ad.update(activity);

            return true;
        }
        return false;
    }

    @Override
    public boolean removeParticipants(Long activity_id, List<Long> participant_ids){
        try{
            Activity activity = ad.getActivity(activity_id);
            if(activity!=null){
                List<Long> pids = activity.getParticipants_ids();
                for(Long participant:participant_ids){
                    for(Long pid:pids){
                        if(Objects.equals(participant, pid)){
                            pids.remove(pid);
                        }
                    }
                }
                activity.setParticipants_ids(pids);
                ad.update(activity);
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
