package exort.apiserver.controller;

import exort.apiserver.service.CommunityService;
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

import exort.api.http.activity.service.ActivityService;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

import java.util.List;

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

	@Autowired
	private ActivityService activitySvc;

	@Autowired
	private CommunityService cs;

	@PostMapping
	public ApiResponse<Activity> createNewActivity(@RequestAttribute("id") Integer operatorId,
			@RequestBody Activity activity) {
		return activitySvc.createNewActivity(activity);
	}

	@PutMapping("/{id}")
	public ApiResponse<Activity> updateActivity(@RequestAttribute("id") Integer operatorId, @RequestBody Activity activity,
			@PathVariable("id") String activityId) {
		if (!activity.getId().equals(activityId)) {
			return new ApiResponse<>("OptErr", "Entity id differs from path param when updating");
		}
		return activitySvc.updateActivity(activity, activityId);
	}

	@PostMapping("/filter")
	public ApiResponse<PagedData<Activity>> getActivities(@RequestBody Filter filter, PageQuery pageQuery) {
		return activitySvc.getActivities(filter, pageQuery);
	}

	@GetMapping("/user")
	public ApiResponse<PagedData<Activity>> getActivitiesThisUser(@RequestAttribute("id") Integer operatorId, PageQuery pageQuery) {
		Filter filter = new Filter();
		List<String> associationIds = (List<String>)cs.listSubscribed(operatorId).getData();
		filter.setAssociationId(associationIds);
		filter.setPublishState(2);
		pageQuery.setSortBy("publishTime");
		return activitySvc.getActivities(filter, pageQuery);
	}

	@PutMapping("/{id}/state")
	public ApiResponse<Activity> publishActivity(@RequestAttribute("id") Integer operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		return activitySvc.publishActivity(activityId, request);
	}

	@PostMapping("/{id}/participants")
	public ApiResponse<Activity> addParticipants(@RequestAttribute("id") Integer operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		return activitySvc.addParticipants(activityId, request);
	}

	@PostMapping("/{id}/realparticipants")
	public ApiResponse<Activity> addRealParticipants(@RequestAttribute("id") Integer operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		return activitySvc.addRealParticipants(activityId, request);
	}

	@DeleteMapping("/{id}/participants")
	public ApiResponse<Activity> deleteParticipants(@RequestAttribute("id") Integer operatorId,
			@PathVariable("id") String activityId, @RequestBody RequestActivity request) {
		return activitySvc.deleteParticipants(activityId, request);
	}

	@GetMapping(value = "/{id}")
	public ApiResponse<Activity> getActivity(@PathVariable("id") String id) {
		return activitySvc.getActivity(id);
	}

}
