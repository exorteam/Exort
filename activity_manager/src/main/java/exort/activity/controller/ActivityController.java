package exort.activity.controller;

import exort.activity.entity.Activity;
import exort.activity.entity.Operation;
import exort.activity.entity.Response;
import exort.activity.entity.Select;
import exort.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService as;
//1
    @ResponseBody
    @PostMapping(value = "/activities")
    public Response createNewActivity(@RequestBody Activity activity){
        return as.upsertActivity(activity);
    }
//2
    @ResponseBody
    @PutMapping(value = "/activities/{activityid}")
    public Response updateActivity(@RequestBody Activity activity){
        return as.upsertActivity(activity);
    }
//3
    @ResponseBody
    @GetMapping(value = "/activities")
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize")int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby")int sortby){
        return as.getActivities(select, pagesize, pagenum, sortby);
    }
//4
    @ResponseBody
    @PatchMapping(value = "/activities/{activityid}")
    public Response publishActivity(@PathVariable(value = "activityid") Long activityid, @RequestBody String type){
        return as.changeActivityState(activityid, type);
    }
//5
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/participants")
    public Response addParticipants(@PathVariable(value = "activityid") Long activityid, @RequestBody List<Long> participantIds){
        return as.addUserIds(activityid, participantIds, 1);
    }
//6
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/realparticipants")
    public Response addRealParticipants(@PathVariable(value = "activityid") Long activityid, @RequestBody List<Long> realParticipantIds){
        return as.addUserIds(activityid, realParticipantIds, 2);
    }
//7
    @ResponseBody
    @DeleteMapping(value = "/activities/{activityid}/participants")
    public Response deleteParticipants(@PathVariable(value = "activityid") Long activityid, @RequestBody List<Long> participantIds){
        return as.removeParticipants(activityid, participantIds);
    }
//8
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/participants")
    public Response getActivityParticipants(@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum, @PathVariable(value = "activityid") Long activityid, @RequestBody Long userId){
        return as.getActivityUserIds(pagesize, pagenum, activityid, userId, 1);
    }
//9
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/realparticipants")
    public Response getActivityRealParticipants(@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum, @PathVariable(value = "activityid") Long activityid, @RequestBody Long userId){
        return as.getActivityUserIds(pagesize, pagenum, activityid, userId, 2);
    }
//10
    @ResponseBody
    @PostMapping(value = "/callback/acceptsignup")
    public Response acceptSignup(@RequestBody Operation operation){
        Long activityid = operation.getApplication().getSignup().getActivityId();
        Long userId = operation.getApplication().getApplicantId();
        List<Long> user = new ArrayList<>();
        user.add(userId);
        return as.addUserIds(activityid,user,1);
    }
// 11
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}")
    public Response getActivity(@PathVariable(value = "activityid") Long acticityid){
        return as.getActivity(acticityid);
    }
}
