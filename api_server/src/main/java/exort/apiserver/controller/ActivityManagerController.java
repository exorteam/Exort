package exort.apiserver.controller;

import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
import exort.api.http.activity.service.ActivityService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.CallbackParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import exort.apiserver.service.ActivityManagerService;
import exort.apiserver.service.ActivityManagerService.*;

@RestController
@RequestMapping(path="/activities")
public class ActivityManagerController {


    @Autowired
    private ActivityManagerService service;

//    @PostMapping
//    public Response createNewActivity(@RequestAttribute("id") int operatorId, @RequestBody Activity activity){
//		if(!checkPermissionOnActivity(operatorId,activity,PERM_CREATE)){
//			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission create activity");
//		}
//		return activitySvc.createNewActivity(activity);
//    }

    @PutMapping("/{id}")
    public Response updateActivity(@RequestBody Activity activity, @PathVariable("id") String id){
        return service.updateActivity(activity,id);
    }

    @GetMapping
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby") int sortby){
        return service.getActivities(select,pagesize,pagenum,sortby);
    }

    @PutMapping("/{id}/state")
    public Response publishActivity(@PathVariable("id")String id, @RequestBody Request request){
//        System.out.println(request.getType());
        return service.publishActivity(id, request);
    }

    @PostMapping("/{id}/participants")
    public Response addParticipants(@PathVariable("id") String id, @RequestBody Request request){
        System.out.println(request.getParticipantIds());
        return service.addParticipants(id, request);
    }

    @PostMapping("/{id}/realparticipants")
    public Response addRealParticipants(@PathVariable("id")String id, @RequestBody Request request){
        return service.addRealParticipants(id, request);
    }

    @DeleteMapping("/{id}/participants")
    public Response deleteParticipants(@PathVariable("id")String id, @RequestBody Request request){
        return service.deleteParticipants(id, request);
    }

    @GetMapping("/{id}/participants")
    public Response getActivityParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody Request request){
        return service.getActivityParticipants(pagesize,pagenum,id, request);
    }

    @GetMapping("/{id}/realparticipants")
    public Response getActivityRealParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody Request request){
        return service.getActivityRealParticipants(pagesize,pagenum,id, request);
    }

    @PostMapping(value = "/callback/acceptsignup")
    public Response acceptSignup(@RequestBody Operation operation){
        return service.acceptSignup(operation);
    }

    @GetMapping(value = "/{id}")
    public Response getActivity(@PathVariable("id")String id){
        return service.getActivity(id);
    }

    private boolean checkPermissionOnActivity(int operatorId, Activity activity, String permission) {
        System.out.println(operatorId);
        System.out.println(activity.getTitle());
        System.out.println(permission);
        for (int i : activity.getAssociationIds()) {
            final String assoScope = "asso-" + String.valueOf(i);
            if (permSvc.hasPermission(Long.valueOf(operatorId), assoScope, permission) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPermissionByActivityId(int operatorId, String activityId, String permission) {
        Activity activity = (Activity) activitySvc.getActivity(activityId).getData();
        if (activity == null)
            return false;
        return checkPermissionOnActivity(operatorId, activity, permission);
    }

}
