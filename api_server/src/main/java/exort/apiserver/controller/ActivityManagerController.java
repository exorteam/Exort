package exort.apiserver.controller;

import javax.websocket.server.PathParam;

import exort.apiserver.service.ActivityManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import exort.api.http.perm.service.PermService;

import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
import exort.api.http.activity.service.ActivityService;
import exort.api.http.review.entity.CallbackParam;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.member.service.AssoMemService;

@RestController
@RequestMapping(path = "/activities")
public class ActivityManagerController {
	// Get operations are open to every one
	// Update operations require specific permission
	public static final String PERM_UPDATE = "update-activity";
	// Delete operations require specific permission
	public static final String PERM_DELETE = "delete-activity";
	// Create operations require specific permission
	public static final String PERM_CREATE = "create-activity";

	// @Autowired
	// private ActivityService activitySvc;

	@Autowired
	private ActivityManagerService activitySvc;

	@Autowired
	private PermService permSvc;
	@Autowired
	private AssoMemService amSvc;

	@PostMapping
	public ApiResponse<Activity> createNewActivity(@RequestAttribute("id") int operatorId,
			@RequestBody Activity activity) {
		if (!checkPermissionOnActivity(operatorId, activity, PERM_CREATE)) {
			return new ApiResponse<>("PermErr",
					"Operator[" + String.valueOf(operatorId) + "] does not have such permission create activity");
		}
		return activitySvc.createNewActivity(activity);
	}

	@PutMapping("/{id}")
	public ApiResponse<Activity> updateActivity(@RequestAttribute("id") int operatorId, @RequestBody Activity activity,
			@PathVariable("id") String activityId) {
		if (!activity.getId().equals(activityId)) {
			return new ApiResponse<>("OptErr", "Entity id differs from path param when updating");
		}
		if (!checkPermissionOnActivity(operatorId, activity, PERM_UPDATE)) {
			return new ApiResponse<>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to update activity[" + activityId + "]");
		}
		return activitySvc.updateActivity(activity, activityId);
	}

	@PostMapping("/filter")
	public ApiResponse<PagedData<Activity>> getActivities(@RequestBody Filter filter, @RequestParam int pagesize,
			@RequestParam int pagenum, @RequestParam String sortby) {
		ApiResponse<PagedData<Activity>> response = activitySvc.getActivities(filter,
				new PageQuery(pagenum, pagesize, sortby));
		return response;
	}

	@PutMapping("/{id}/state")
	public ApiResponse<Object> publishActivity(@RequestAttribute("id") int operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<Object>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.publishActivity(activityId, request);
	}

	@PostMapping("/{id}/participants")
	public ApiResponse<Object> addParticipants(@RequestAttribute("id") int operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<Object>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.addParticipants(activityId, request);
	}

	@PostMapping("/{id}/realparticipants")
	public ApiResponse<Object> addRealParticipants(@RequestAttribute("id") int operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<Object>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.addRealParticipants(activityId, request);
	}

	@DeleteMapping("/{id}/participants")
	public ApiResponse<Object> deleteParticipants(@RequestAttribute("id") int operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<Object>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.deleteParticipants(activityId, request);
	}

	@PostMapping("/{id}/participants/message")
	public ApiResponse<PagedData<Integer>> getActivityParticipants(@RequestAttribute("id") int operatorId,
			@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.getActivityParticipants(new PageQuery(pagesize, pagenum), activityId, request);
	}

	@GetMapping("/{id}/realparticipants")
	public ApiResponse<PagedData<Integer>> getActivityRealParticipants(@RequestAttribute("id") int operatorId,
			@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.getActivityRealParticipants(new PageQuery(pagesize, pagenum), activityId, request);
	}

	@PostMapping(value = "/callback/acceptsignup")
	public ApiResponse<Object> acceptSignup(@RequestAttribute("id") int operatorId,
			@RequestBody CallbackParam<Signup> operation) {
		String activityId = operation.getApplication().getObject().getActivityId();
		if (!checkPermissionByActivityId(operatorId, activityId, PERM_UPDATE)) {
			return new ApiResponse<>("PermErr", "Operator[" + String.valueOf(operatorId)
					+ "] does not have such permission to publish activity[" + activityId + "]");
		}
		return activitySvc.acceptSignup(operation);
	}

	@GetMapping(value = "/{id}")
	public ApiResponse<Activity> getActivity(@PathVariable("id") String id) {
		return activitySvc.getActivity(id);
	}

	private boolean checkPermissionOnActivity(int operatorId, Activity activity, String permission) {
		for (String i : activity.getAssociationIds()) {
			final String assoScope = amSvc.scope(i);
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
