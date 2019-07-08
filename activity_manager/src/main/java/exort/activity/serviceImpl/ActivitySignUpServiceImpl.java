package exort.activity.serviceImpl;

import exort.activity.dao.ActivityDao;
import exort.activity.dao.ActivitySignUpDao;
import exort.activity.entity.ActivitySignUp;
import exort.activity.service.ActivitySignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivitySignUpServiceImpl implements ActivitySignUpService {

    @Autowired
    private ActivityDao ad;

    @Autowired
    private ActivitySignUpDao asd;

    @Override
    public ActivitySignUp createActivitySignUp(Map<String, String> maps){

        Long user_id = Long.parseLong(maps.get("user_id"));
        Long activity_id = Long.parseLong(maps.get("activity_id"));
//        this.material_ids = material_ids;
        String create_time = maps.get("create_time");
        String close_time = maps.get("close_time");
        int state = Integer.parseInt(maps.get("state"));

        ActivitySignUp activitySignUp = new ActivitySignUp(user_id, activity_id, create_time, close_time, state);

        asd.update(activitySignUp);

        return activitySignUp;
    }

    @Override
    public void acceptSignUp(Long activity_signup_id){
        ActivitySignUp activitySignUp = asd.getActivitySignUp(activity_signup_id);

        activitySignUp.setState(2);

        asd.update(activitySignUp);
    }

    @Override
    public void refuseSignUp(Long activity_signup_id) {
        ActivitySignUp activitySignUp = asd.getActivitySignUp(activity_signup_id);

        activitySignUp.setState(3);

        asd.update(activitySignUp);
    }

    @Override
    public void cancelSignUp(Long activity_signup_id){
        ActivitySignUp activitySignUp = asd.getActivitySignUp(activity_signup_id);

        activitySignUp.setState(4);

        asd.update(activitySignUp);
    }

    @Override
    public ActivitySignUp getActivitySignUp(Long activity_signup_id){
        return asd.getActivitySignUp(activity_signup_id);
    }

    @Override
    public List<ActivitySignUp> getActivitySignUps(Long activity_id, int state){
        return asd.getActivitySignUps(activity_id, state);
    }
}
