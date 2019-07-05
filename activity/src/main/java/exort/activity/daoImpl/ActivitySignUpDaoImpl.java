package exort.activity.daoImpl;

import exort.activity.dao.ActivitySignUpDao;
import exort.activity.entity.ActivitySignUp;
import exort.activity.repository.ActivityRepository;
import exort.activity.repository.ActivitySignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActivitySignUpDaoImpl implements ActivitySignUpDao {

    @Autowired
    private ActivityRepository ar;

    @Autowired
    private ActivitySignUpRepository asr;

    @Override
    public void update(ActivitySignUp activitySignUp){
        asr.saveAndFlush(activitySignUp);
    }

    @Override
    public ActivitySignUp getActivitySignUp(Long activity_signup_id){
        return asr.findActivitySignUpById(activity_signup_id);
    }

    @Override
    public List<ActivitySignUp> getActivitySignUps(Long activity_id, int state){
        return asr.findAllByActivity_idAndState(activity_id, state);
    }

}
