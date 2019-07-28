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

@RestController
@RequestMapping(path="/activities")
public class ActivityManagerController {
	// Get operations are open to every one
	// Update operations require specific permission
	public static final String PERM_UPDATE = "update-activity";
	// Delete operations require specific permission
	public static final String PERM_DELETE = "delete-activity";
	// Create operations require specific permission
	public static final String PERM_CREATE = "create-activity";

    @Autowired
    private ActivityManagerService activitySvc;
	@Autowired
	private PermService permSvc;

    @PostMapping("/")
    public Response createNewActivity(@RequestAttribute("id") int operatorId,@RequestBody Activity activity){
		if(!checkPermissionOnActivity(operatorId,activity,PERM_CREATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission create activity");
		}
		return activitySvc.createNewActivity(activity);
    }

    @PutMapping("/{id}")
    public Response updateActivity(@RequestAttribute("id") int operatorId,@RequestBody Activity activity, @PathVariable("id") String activityId){
		if(!activity.getId().equals(activityId)){
			return new Response<Object>(null,"OptErr","Entity id differs from path param when updating");
		}
		if(!checkPermissionOnActivity(operatorId,activity,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to update activity["+activityId+"]");
		}
		return activitySvc.updateActivity(activity,activityId);
    }

    @GetMapping("/")
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby") int sortby){
		// open to every one
        return activitySvc.getActivities(select,pagesize,pagenum,sortby);
    }

    @PutMapping("/{id}/state")
    public Response publishActivity(@RequestAttribute("id") int operatorId,@PathVariable("id")String activityId, @RequestBody Request request){
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
        return activitySvc.publishActivity(activityId, request);
    }

    @PostMapping("/{id}/participants")
    public Response addParticipants(@RequestAttribute("id") int operatorId,@PathVariable("id") String activityId, @RequestBody Request request){
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
        return activitySvc.addParticipants(activityId, request);
    }

    @PostMapping("/{id}/realparticipants")
    public Response addRealParticipants(@RequestAttribute("id") int operatorId,@PathVariable("id")String activityId, @RequestBody Request request){
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
        return activitySvc.addRealParticipants(activityId, request);
    }

    @DeleteMapping("/{id}/participants")
    public Response deleteParticipants(@RequestAttribute("id") int operatorId,@PathVariable("id")String activityId, @RequestBody Request request){
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
        return activitySvc.deleteParticipants(activityId, request);
    }

    @GetMapping("/{id}/participants")
    public Response getActivityParticipants(@RequestAttribute("id") int operatorId,@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String activityId,@RequestBody Request request){
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
        return activitySvc.getActivityParticipants(pagesize,pagenum,activityId, request);
    }

    @GetMapping("/{id}/realparticipants")
    public Response getActivityRealParticipants(@RequestAttribute("id") int operatorId,@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String activityId,@RequestBody Request request){
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
        return activitySvc.getActivityRealParticipants(pagesize,pagenum,activityId, request);
    }

    @PostMapping(value = "/callback/acceptsignup")
    public Response acceptSignup(@RequestAttribute("id") int operatorId,@RequestBody Operation operation){
		String activityId = operation.getApplication().getSignup().getActivityId();
		if(!checkPermissionByActivityId(operatorId,activityId,PERM_UPDATE)){
			return new Response<Object>(null,"PermErr","Operator["+String.valueOf(operatorId)+"] does not have such permission to publish activity["+activityId+"]");
		}
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

	private boolean checkPermissionByActivityId(int operatorId,String activityId,String permission){
		Activity activity = (Activity)activitySvc.getActivity(activityId).getData();
		if(activity == null)return false;
		return checkPermissionOnActivity(operatorId,activity,permission);
	}
}
