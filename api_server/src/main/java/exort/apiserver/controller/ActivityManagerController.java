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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ActivityService service;

    @PostMapping("/")
    public ApiResponse<Activity> createNewActivity(@RequestBody Activity activity) {
        return service.createNewActivity(activity);
    }

    @PutMapping("/{id}")
    public ApiResponse<Activity> updateActivity(@RequestBody Activity activity, @PathVariable("id") String id) {
        return service.updateActivity(activity, id);
    }

    @GetMapping("/")
    public ApiResponse<PagedData<Activity>> getActivities(@RequestBody Filter filter, PageQuery pageQuery) {
        return service.getActivities(filter, pageQuery);
    }

    @PutMapping("/{id}/state")
    public ApiResponse<Object> publishActivity(@PathVariable("id") String id,
            @RequestBody RequestActivity requestActivity) {
        return service.publishActivity(id, requestActivity);
    }

    @PostMapping("/{id}/participants")
    public ApiResponse<Object> addParticipants(@PathVariable("id") String id,
            @RequestBody RequestActivity requestActivity) {
        return service.addParticipants(id, requestActivity);
    }

    @PostMapping("/{id}/realparticipants")
    public ApiResponse<Object> addRealParticipants(@PathVariable("id") String id,
            @RequestBody RequestActivity requestActivity) {
        return service.addRealParticipants(id, requestActivity);
    }

    @DeleteMapping("/{id}/participants")
    public ApiResponse<Object> deleteParticipants(@PathVariable("id") String id,
            @RequestBody RequestActivity requestActivity) {
        return service.deleteParticipants(id, requestActivity);
    }

    @GetMapping("/{id}/participants")
    public ApiResponse<PagedData<Integer>> getActivityParticipants(PageQuery pageQuery, @PathVariable("id") String id,
            @RequestBody RequestActivity requestActivity) {
        return service.getActivityParticipants(pageQuery, id, requestActivity);
    }

    @GetMapping("/{id}/realparticipants")
    public ApiResponse<PagedData<Integer>> getActivityRealParticipants(PageQuery pageQuery,
            @PathVariable("id") String id, @RequestBody RequestActivity requestActivity) {
        return service.getActivityRealParticipants(pageQuery, id, requestActivity);
    }

    @PostMapping(value = "/callback/acceptsignup")
    public ApiResponse<Object> acceptSignup(@RequestBody CallbackParam<Signup> operation) {
        return service.acceptSignup(operation);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Activity> getActivity(@PathVariable("id") String id) {
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
