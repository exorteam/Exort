package exort.activity.service;

import exort.activity.entity.ActivitySignUp;

import java.util.List;
import java.util.Map;

public interface ActivitySignUpService {
    ActivitySignUp createActivitySignUp(Map<String, String> maps);

    void acceptSignUp(Long activity_signup_id);

    void refuseSignUp(Long activity_signup_id);

    void cancelSignUp(Long activity_signup_id);

    ActivitySignUp getActivitySignUp(Long activity_signup_id);

    List<ActivitySignUp> getActivitySignUps(Long activity_id, int state);
}
