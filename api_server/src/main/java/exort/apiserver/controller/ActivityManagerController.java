package exort.apiserver.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.perm.service.PermService;
import exort.apiserver.service.ActivityManagerService;
import exort.apiserver.service.ActivityManagerService.Activity;
import exort.apiserver.service.ActivityManagerService.Operation;
import exort.apiserver.service.ActivityManagerService.Request;
import exort.apiserver.service.ActivityManagerService.Response;
import exort.apiserver.service.ActivityManagerService.Select;
import exort.apiserver.service.AssoManagerService;

@RestController
@RequestMapping(path="/activities")
public class ActivityManagerController {

    @Autowired
    private ActivityManagerService activitySvc;
	@Autowired
	private AssoManagerService assoSvc;
	@Autowired
	private PermService permSvc;

    @PostMapping("/")
    public Response createNewActivity(@RequestAttribute("id") int operatorId,@RequestBody Activity activity){
		if(!checkPermissionOnActivity(operatorId,activity,"create-activity")){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission create activity");
		}
		return activitySvc.createNewActivity(activity);
    }

    @PutMapping("/{id}")
    public Response updateActivity(@RequestAttribute("id") int operatorId,@RequestBody Activity activity, @PathVariable("id") String activityId){
		if(!activity.getId().equals(activityId)){
			return new Response<Object>(null,"OptErr","Entity id differs from path param when updating");
		}
		if(!checkPermissionOnActivity(operatorId,activity,"update-activity")){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to update activity["+activityId+"]");
		}
		return activitySvc.updateActivity(activity,activityId);
    }

    @GetMapping("/")
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby") int sortby){
        return activitySvc.getActivities(select,pagesize,pagenum,sortby);
    }

    @PutMapping("/{id}/state")
    public Response publishActivity(@PathVariable("id")String id, @RequestBody Request request){
//        System.out.println(request.getType());
        return activitySvc.publishActivity(id, request);
    }

    @PostMapping("/{id}/participants")
    public Response addParticipants(@PathVariable("id") String id, @RequestBody Request request){
        System.out.println(request.getParticipantIds());
        return activitySvc.addParticipants(id, request);
    }

    @PostMapping("/{id}/realparticipants")
    public Response addRealParticipants(@PathVariable("id")String id, @RequestBody Request request){
        return activitySvc.addRealParticipants(id, request);
    }

    @DeleteMapping("/{id}/participants")
    public Response deleteParticipants(@PathVariable("id")String id, @RequestBody Request request){
        return activitySvc.deleteParticipants(id, request);
    }

    @GetMapping("/{id}/participants")
    public Response getActivityParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody Request request){
        return activitySvc.getActivityParticipants(pagesize,pagenum,id, request);
    }

    @GetMapping("/{id}/realparticipants")
    public Response getActivityRealParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody Request request){
        return activitySvc.getActivityRealParticipants(pagesize,pagenum,id, request);
    }

    @PostMapping(value = "/callback/acceptsignup")
    public Response acceptSignup(@RequestBody Operation operation){
        return activitySvc.acceptSignup(operation);
    }

    @GetMapping(value = "/{id}")
    public Response getActivity(@PathVariable("id")String id){
        return activitySvc.getActivity(id);
    }

	private boolean checkPermissionOnActivity(int operatorId,Activity activity,String permission){
		for(int i:activity.getAssociationIds()){
			final String assoScope = "asso-" + String.valueOf(i);
			if(permSvc.hasPermission(Long.valueOf(operatorId),assoScope,permission) != null){
				return true;
			}
		}
		return false;
	}

}
