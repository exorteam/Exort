package exort.activity.controller;


import exort.activity.entity.ActivityInfo;
import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
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

//1

    @RequestMapping(value = "/activities", method = RequestMethod.POST)
    public ApiResponse<Activity> createNewActivity(@RequestBody Activity activity) {
        try {

            ActivityInfo activityInfo = new ActivityInfo(activity.getAssociationIds(), activity.getSignupTime(), activity.getTime(), activity.getTitle(), activity.getContent(), activity.isIfReview(), activity.isIfOnlyMem(), activity.getMaxParticipants(), activity.getMaterialTemplateIds(), activity.getTags(), activity.getImage());

            ActivityInfo ret = as.createActivity(activityInfo);

            if (ret == null) {
                return new ApiResponse<>("create new activity failed", "创建活动失败");
            } else {
                Activity newActivity = changeActivityInfo2Activity(activityInfo);

                return new ApiResponse<>(newActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "create new activity failed", "创建活动失败");
        }
    }
//2

    @RequestMapping(value = "/activities/{activityid}", method = RequestMethod.PUT)
    public ApiResponse updateActivity(@RequestBody Activity activity, @PathVariable(value = "activityid") String activityId) {
        try {
            ActivityInfo activityInfo = new ActivityInfo(activity.getAssociationIds(), activity.getSignupTime(), activity.getTime(), activity.getTitle(), activity.getContent(), activity.isIfReview(), activity.isIfOnlyMem(), activity.getMaxParticipants(), activity.getMaterialTemplateIds(), activity.getTags(), activity.getImage());

            ActivityInfo ret = as.updateActivity(activityId, activityInfo);


            if (ret == null) {
                return new ApiResponse<>("update activity " + activityId + " failed", "修改活动失败");
            } else {

                Activity newActivity = changeActivityInfo2Activity(activityInfo);

                return new ApiResponse<>(newActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "update activity " + activityId + " failed", "修改活动失败");
        }
    }
//3

    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    public ApiResponse<PagedData<Activity>> getActivities(@RequestBody Filter filter, PageQuery pageQuery) {
        try {
            PageQuery page = PageQuery.relocate(pageQuery, 6, 100);
            PagedData<ActivityInfo> ret = as.getActivities(filter, page);

            PagedData<Activity> result = new PagedData<>();
            result.setPageNum(ret.getPageNum());
            result.setPageSize(ret.getPageSize());
            result.setTotalSize(ret.getTotalSize());
            List<Activity> resultList = new ArrayList<>();

            if (ret.getContent() != null) {

                for (int i = 0; i < ret.getContent().size(); i++) {
                    ActivityInfo item = ret.getContent().get(i);
                    Activity activity = changeActivityInfo2Activity(item);
                    resultList.add(activity);
                }

            }
            result.setContent(resultList);

            return new ApiResponse<PagedData<Activity>>(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "get activities failed2.", "查询多个活动失败");
        }
    }
//4

    @RequestMapping(value = "/activities/{activityid}/state", method = RequestMethod.PUT)
    public ApiResponse changeActivityState(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        try {
            ActivityInfo theActivity = as.getActivity(activityId);

            if (requestActivity.getType().equals("publish")) {
                theActivity.setPublishState(2);
            } else {
                theActivity.setPublishState(1);
            }

            ActivityInfo activityInfo = as.updateActivity(activityId, theActivity);
            if (activityInfo.getPublishState() == 2 || activityInfo.getPublishState() == 1) {
                return new ApiResponse<>(new HashMap());
            } else {
                return new ApiResponse<>("change activity " + activityId + " failed", "修改活动发布状态失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "change activity " + activityId + " failed", "修改活动发布状态失败");
        }
    }
//5

    @RequestMapping(value = "/activities/{activityid}/participants", method = RequestMethod.POST)
    public ApiResponse addParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        try {
            ActivityInfo theActivity = as.getActivity(activityId);
            Set<Integer> set = new HashSet<>();
            if (theActivity.getParticipantIds() != null) {
                set.addAll(theActivity.getParticipantIds());
            }
            set.addAll(requestActivity.getParticipantIds());
            List<Integer> pars = new ArrayList<>(set);
            theActivity.setParticipantIds(pars);

            ActivityInfo activityInfo = as.updateActivity(activityId, theActivity);

            if (activityInfo.getParticipantIds().size() != 0 || requestActivity.getParticipantIds().size() == 0) {
                return new ApiResponse<>(new HashMap());
            } else {
                return new ApiResponse<>("add activity participants failed", "增加活动参加者失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "add activity participants failed", "增加活动参加者失败");
        }
    }
//6

    @PostMapping(value = "/activities/{activityid}/realparticipants")
    public ApiResponse addRealParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        try {
            ActivityInfo theActivity = as.getActivity(activityId);
            Set<Integer> set = new HashSet<>();
            if (theActivity.getRealParticipantIds() != null) {
                set.addAll(theActivity.getRealParticipantIds());
            }
            set.addAll(requestActivity.getRealParticipantIds());
            List<Integer> pars = new ArrayList<>(set);
            theActivity.setRealParticipantIds(pars);

            ActivityInfo activityInfo = as.updateActivity(activityId, theActivity);

            if (activityInfo.getRealParticipantIds().size() != 0 || requestActivity.getParticipantIds().size() == 0) {
                return new ApiResponse<>(new HashMap());
            } else {
                return new ApiResponse<>("add activity real participants failed", "增加活动实际参加者失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "add activity real participants failed", "增加活动实际参加者失败");
        }
    }

    //7
    @RequestMapping(value = "/activities/{activityid}/participants", method = RequestMethod.DELETE)
    public ApiResponse deleteParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity) {
        try {
            ActivityInfo theActivity = as.getActivity(activityId);
            List<Integer> pars;
            if (theActivity.getParticipantIds() == null) {
                pars = new ArrayList<>();
            } else {
                pars = theActivity.getParticipantIds();
            }
            boolean result = pars.removeAll(requestActivity.getParticipantIds());
            as.updateActivity(activityId, theActivity);

            if (result) {
                return new ApiResponse<>(new HashMap());
            } else {
                return new ApiResponse<>("remove activity participants failed", "删除活动参加者失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "remove activity participants failed", "删除活动参加者失败");
        }
    }
//8

    @RequestMapping(value = "/activities/{activityid}/participants", method = RequestMethod.GET)
    public ApiResponse getActivityParticipants(
            @PathVariable(value = "activityid") String activityId,
            PageQuery pageQuery,
            @RequestBody RequestActivity requestActivity
    ) {
        try {
//            PagedData<Integer> result = as.getActivityUserIds(activityId, pageQuery, requestActivity.getUserId(), 1);

            ActivityInfo activityInfo = as.getActivity(activityId);
            List<Integer> pars;
            if (activityInfo.getParticipantIds() == null) {
                pars = new ArrayList<>();
            } else {
                pars = activityInfo.getParticipantIds();
            }

            PagedData<Integer> result = as.fromList2Paged(pars, pageQuery);

            if (result.getContent() == null) {
                return new ApiResponse<>("get activity" + activityId + " participants failed.", "查询活动参加者失败");
            } else {
                return new ApiResponse<>(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "get activity" + activityId + " participants failed.", "查询活动参加者失败");
        }
    }

    //9
    @RequestMapping(value = "/activities/{activityid}/realparticipants", method = RequestMethod.GET)
    public ApiResponse getActivityRealParticipants(
            @PathVariable(value = "activityid") String activityId,
            @RequestBody RequestActivity requestActivity,
            PageQuery pageQuery
    ) {
        try {
//            PageQuery page = PageQuery.relocate(pageQuery, 9, 100);
//            PagedData<Integer> result = as.getActivityUserIds(activityId, pageQuery, requestActivity.getUserId(), 2);
            ActivityInfo activityInfo = as.getActivity(activityId);
            List<Integer> pars;
            if (activityInfo.getRealParticipantIds() == null) {
                pars = new ArrayList<>();
            } else {
                pars = activityInfo.getRealParticipantIds();
            }

            PagedData<Integer> result = as.fromList2Paged(pars, pageQuery);

            if (result.getContent() == null) {
                return new ApiResponse<>("get activity" + activityId + " participants failed.", "查询活动参加者失败");
            } else {
                return new ApiResponse<>(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "get activity" + activityId + " participants failed.", "查询活动参加者失败");
        }
    }

//    //10
//    @RequestMapping(value = "/callback/acceptsignup", method = RequestMethod.POST)
//    public ApiResponse acceptSignup(@RequestBody CallbackParam<Signup> callbackParam) {
//        try {
//            String activityId = callbackParam.getApplication().getObject().getActivityId();
//            Long userId = callbackParam.getApplication().getApplicantId();
//            List<Integer> user = new ArrayList<>();
//            user.add(userId.intValue());
////            boolean result = as.addUserIds(activityId, user, 1);
//
//            ActivityInfo theActivity = as.getActivity(activityId);
//
//            Set<Integer> set = new HashSet<>();
//            set.addAll(theActivity.getParticipantIds());
//            set.addAll(user);
//            List<Integer> pars = new ArrayList<Integer>(set);
//
//            theActivity.setParticipantIds(pars);
//
//            ActivityInfo activityInfo = as.updateActivity(activityId, theActivity);
//
//            if (activityInfo.getParticipantIds().size() != 0 || user.size() == 0) {
//                return new ApiResponse<>(new HashMap());
//            } else {
//                return new ApiResponse<>("callback accept signup failed.", "接受申请回调失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApiError(401, "callback accept signup failed.", "接受申请回调失败");
//        }
//    }

    // 11
    @RequestMapping(value = "/activities/{activityid}", method = RequestMethod.GET)
    public ApiResponse getActivity(@PathVariable(value = "activityid") String activityId) {
        try {

            ActivityInfo activityInfo = as.getActivity(activityId);

            if (activityInfo == null) {
                return new ApiResponse<>("get activity " + activityId + " failed1", "查询单个活动失败");
            } else {
                Activity activity = changeActivityInfo2Activity(activityInfo);
                return new ApiResponse<>(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(401, "get activity " + activityId + " failed2", "查询单个活动失败");
        }
    }
}
