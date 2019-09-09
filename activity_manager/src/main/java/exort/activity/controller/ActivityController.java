package exort.activity.controller;


import exort.activity.entity.ActivityInfo;
import exort.activity.service.ActivityService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService as;

    private Activity changeActivityInfo2Activity(ActivityInfo activityInfo) {
        return new Activity(
                activityInfo.getId(),
                activityInfo.getAssociationIds(),
                activityInfo.getCreateTime(),
                activityInfo.getPublishTime(),
                activityInfo.getLastPublishTime(),
                activityInfo.getLastModifyTime(),
                activityInfo.getTitle(),
                activityInfo.getContent(),
                activityInfo.getSignupTime(),
                activityInfo.getTime(),
                activityInfo.getPublishState(),
                activityInfo.getSignupState(),
                activityInfo.getState(),
                activityInfo.isIfReview(),
                activityInfo.isIfOnlyMem(),
                activityInfo.getMaxParticipants(),
                activityInfo.getMaterialTemplateIds(),
                activityInfo.getParticipantIds(),
                activityInfo.getRealParticipantIds(),
                activityInfo.getTags(),
                activityInfo.getImage()
        );
    }


    @PostMapping(value = "/activities")
    public ApiResponse<Activity> createNewActivity(@RequestBody Activity activity) {
        ActivityInfo activityInfo = new ActivityInfo(activity.getAssociationIds(), activity.getSignupTime(), activity.getTime(), activity.getTitle(), activity.getContent(), activity.isIfReview(), activity.isIfOnlyMem(), activity.getMaxParticipants(), activity.getMaterialTemplateIds(), activity.getTags(), activity.getImage());
        activityInfo = as.createActivity(activityInfo);
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }

    @PutMapping(value = "/activities/{activityid}")
    public ApiResponse updateActivity(@RequestBody Activity activity, @PathVariable(value = "activityid") String activityId) {
        ActivityInfo activityInfo = as.getActivity(activityId);
        if (activityInfo == null) {
            throw new ApiError(404, "notFound", "活动不存在");
        }

        activityInfo.setAssociationIds(activity.getAssociationIds());
        activityInfo.setSignupTime(activity.getSignupTime());
        activityInfo.setTime(activity.getTime());
        activityInfo.setTitle(activity.getTitle());
        activityInfo.setContent(activity.getContent());
        activityInfo.setIfReview(activity.isIfReview());
        activityInfo.setIfOnlyMem(activity.isIfOnlyMem());
        activityInfo.setMaxParticipants(activity.getMaxParticipants());
        activityInfo.setMaterialTemplateIds(activity.getMaterialTemplateIds());
        activityInfo.setTags(activity.getTags());
        activityInfo.setImage(activity.getImage());

        activityInfo.setLastModifyTime(new Date());

        activityInfo = as.updateActivity(activityInfo);
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }
//3

    @GetMapping(value = "/activities")
    public ApiResponse<PagedData<Activity>> getActivities(@RequestBody Filter filter, PageQuery pageQuery) {
        pageQuery = PageQuery.relocate(pageQuery, 6, 100);
        PagedData<ActivityInfo> ret = as.getActivities(filter, pageQuery);

        List<Activity> resultList = new ArrayList<>();
        for (ActivityInfo activityInfo: ret.getContent()) {
            resultList.add(changeActivityInfo2Activity(activityInfo));
        }
        PagedData<Activity> result = new PagedData<>(ret.getPageNum(), ret.getPageSize(), ret.getTotalSize(), resultList);

        return new ApiResponse<PagedData<Activity>>(result);
    }
//4

    @PutMapping(value = "/activities/{activityid}/state")
    public ApiResponse changeActivityState(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        ActivityInfo activityInfo = as.getActivity(activityId);
        if (activityInfo == null) {
            throw new ApiError(404, "notFound", "活动不存在");
        }

        if (requestActivity.getType().equals("publish")) {
            activityInfo.setPublishState(2);
            activityInfo.setPublishTime(new Date());
        } else {
            activityInfo.setPublishState(1);
        }

        activityInfo = as.updateActivity(activityInfo);
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }
//5

    @PostMapping(value = "/activities/{activityid}/participants")
    public ApiResponse addParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        ActivityInfo activityInfo = as.getActivity(activityId);
        if (activityInfo == null) {
            throw new ApiError(404, "notFound", "活动不存在");
        }

        Set<Integer> set = new HashSet<>();
        if (activityInfo.getParticipantIds() != null) {
            set.addAll(activityInfo.getParticipantIds());
        }
        set.addAll(requestActivity.getParticipantIds());
        List<Integer> pars = new ArrayList<>(set);
        activityInfo.setParticipantIds(pars);

        activityInfo = as.updateActivity(activityInfo);
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }
//6

    @PostMapping(value = "/activities/{activityid}/realparticipants")
    public ApiResponse addRealParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        ActivityInfo activityInfo = as.getActivity(activityId);
        if (activityInfo == null) {
            throw new ApiError(404, "notFound", "活动不存在");
        }

        Set<Integer> set = new HashSet<>();
        if (activityInfo.getRealParticipantIds() != null) {
            set.addAll(activityInfo.getRealParticipantIds());
        }
        set.addAll(requestActivity.getRealParticipantIds());
        List<Integer> pars = new ArrayList<>(set);
        activityInfo.setRealParticipantIds(pars);

        activityInfo = as.updateActivity(activityInfo);
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }
    //7
    @DeleteMapping(value = "/activities/{activityid}/participants")
    public ApiResponse deleteParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        ActivityInfo activityInfo = as.getActivity(activityId);
        if (activityInfo == null) {
            throw new ApiError(404, "notFound", "活动不存在");
        }

        List<Integer> pars;
        if (activityInfo.getParticipantIds() == null) {
            pars = new ArrayList<>();
        } else {
            pars = activityInfo.getParticipantIds();
        }
        pars.removeAll(requestActivity.getParticipantIds());
        activityInfo.setParticipantIds(pars);

        activityInfo = as.updateActivity(activityInfo);
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }

    // 10
    @RequestMapping(value = "/activities/{activityid}", method = RequestMethod.GET)
    public ApiResponse getActivity(@PathVariable(value = "activityid") String activityId) {
        ActivityInfo activityInfo = as.getActivity(activityId);
        if (activityInfo == null) {
            throw new ApiError(404, "notFound", "活动不存在");
        }
        return new ApiResponse<>(changeActivityInfo2Activity(activityInfo));
    }
}
