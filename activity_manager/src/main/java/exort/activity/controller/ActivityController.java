package exort.activity.controller;

import exort.activity.entity.Activity;
import exort.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService as;

    @Autowired
    private ActivitySignUpService ass;

    @ResponseBody
    @PostMapping(value = "/create_activity")
    public Activity createNewActivity(@RequestBody Map<String, String> maps){
        return as.createActivity(maps);
    }

    @ResponseBody
    @PostMapping(value = "/modify_activity")
    public boolean updateActivity(@RequestBody Activity activity){
        return as.modifyActivity(activity);
    }

    @ResponseBody
    @PostMapping(value = "/get_activity")
    public Activity getActivity(@RequestBody Long activity_id){
        return as.getActivity(activity_id);
    }

    @ResponseBody
    @PostMapping(value = "/publish_activity")
    public boolean publishActivity(@RequestBody Long activity_id){
        return as.publish(activity_id);
    }

    @ResponseBody
    @PostMapping(value = "/withdraw_activity")
    public boolean withdrawActivity(@RequestBody Long activity_id){
        return as.withdraw(activity_id);
    }

    @ResponseBody
    @PostMapping(value = "/add_participants")
    public boolean addParticipants(@RequestBody List<Long> ids){
        Long activity_id = ids.get(0);
        List<Long> participant_ids = ids.subList(1, ids.size());
        return as.addParticipants(activity_id, participant_ids);
    }

    @ResponseBody
    @PostMapping(value = "/remove_participants")
    public boolean removeParticipants(@RequestBody List<Long> ids){
        Long activity_id = ids.get(0);
        List<Long> participant_ids = ids.subList(1, ids.size());
        return as.removeParticipants(activity_id, participant_ids);
    }
}
