package exort.activity.dao;

import exort.activity.entity.ActivitySignUp;

import java.util.List;

public interface ActivitySignUpDao {

    void update(ActivitySignUp activitySignUp);

    ActivitySignUp getActivitySignUp(Long activity_signup_id);

    List<ActivitySignUp> getActivitySignUps(Long activity_id, int state);
}
