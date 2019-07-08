package exort.activity.controller;

import exort.activity.entity.Activity;
import exort.activity.entity.ActivitySignUp;
import exort.activity.service.ActivityService;
import exort.activity.service.ActivitySignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ActivitySignUpController {

    @Autowired
    private ActivityService as;

    @Autowired
    private ActivitySignUpService ass;

    @ResponseBody
    @PostMapping(value = "/create_activitysignup")
    public ActivitySignUp create_Activity_signup(@RequestBody Map<String, String> maps){
        return ass.createActivitySignUp(maps);
    }

    @ResponseBody
    @PostMapping(value = "/accept_signup")
    public void acceptSignup(Long activity_signup_id){
        ass.acceptSignUp(activity_signup_id);
    }

    @ResponseBody
    @PostMapping(value = "/refuse_signup")
    public void refuseSignup(Long activity_signup_id){
        ass.refuseSignUp(activity_signup_id);
    }

    @ResponseBody
    @PostMapping(value = "/cancel_signup")
    public void cancelSignup(Long activity_signup_id){
        ass.cancelSignUp(activity_signup_id);
    }

    @ResponseBody
    @PostMapping(value = "/get_activity_signup")
    public ActivitySignUp getActivitySignUp(Long activity_signup_id){
        return ass.getActivitySignUp(activity_signup_id);
    }

    @ResponseBody
    @PostMapping(value = "/get_activity_signups")
    public List<ActivitySignUp> listActivitySignups(Long activity_id, int state){
        return ass.getActivitySignUps(activity_id, state);
    }
}

