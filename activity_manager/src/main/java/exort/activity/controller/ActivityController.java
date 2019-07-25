package exort.activity.controller;

import exort.activity.entity.*;
import exort.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

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
    public Response updateActivity(@RequestBody Activity activity, @PathVariable(value = "activityid") String activityid){
        activity.setId(activityid);
        return as.upsertActivity(activity);
    }
//3 待测
    @ResponseBody
    @GetMapping(value = "/activities")
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize")int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby")int sortby){
        return as.getActivities(select, pagesize, pagenum, sortby);
    }
//4
    @ResponseBody
    @PatchMapping(value = "/activities/{activityid}")
    public Response publishActivity(@PathVariable(value = "activityid")String activityid, @RequestBody Request request){
        return as.changeActivityState(activityid, request.getType());
    }
//5
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/participants")
    public Response addParticipants(@PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        System.out.println(request.getParticipantIds());
        return as.addUserIds(activityid, request.getParticipantIds(), 1);
    }
//6
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/realparticipants")
    public Response addRealParticipants(@PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        return as.addUserIds(activityid, request.getRealParticipantIds(), 2);
    }
//7
    @ResponseBody
    @DeleteMapping(value = "/activities/{activityid}/participants")
    public Response deleteParticipants(@PathVariable(value = "activityid")String activityid, @RequestBody Request request){
        return as.removeParticipants(activityid, request.getParticipantIds());
    }
//8
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/participants")
    public Response getActivityParticipants(@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum, @PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        return as.getActivityUserIds(pagesize, pagenum, activityid, request.getUserId(), 1);
    }
//9
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/realparticipants")
    public Response getActivityRealParticipants(@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum, @PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        return as.getActivityUserIds(pagesize, pagenum, activityid, request.getUserId(), 2);
    }
//10
    @ResponseBody
    @PostMapping(value = "/callback/acceptsignup")
    public Response acceptSignup(@RequestBody Operation operation){
        System.out.println(operation.getApplication().getCreateTime());
        String activityid = operation.getApplication().getSignup().getActivityId();
        int userId = operation.getApplication().getApplicantId();
        List<Integer> user = new ArrayList<>();
        user.add(userId);
        return as.addUserIds(activityid,user,1);
    }
// 11
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}")
    public Response getActivity(@PathVariable(value = "activityid") String acticityid){
        return as.getActivity(acticityid);
    }
}
