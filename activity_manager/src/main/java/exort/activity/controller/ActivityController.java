package exort.activity.controller;

import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.RequestActivity;
import exort.api.http.activity.entity.Signup;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.review.entity.CallbackParam;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService as;
//1
    @ResponseBody
    @PostMapping(value = "/activities")
    public ApiResponse<Activity> createNewActivity(@RequestBody Activity activity){
        try{
            activity.setId((new ObjectId()).toString());
            Activity newActivity = as.upsertActivity(activity);
            if(newActivity==null){
                return new ApiResponse<>("create new activity failed", "创建活动失败");
            }else{
                return new ApiResponse<>(newActivity);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "create new activity failed", "创建活动失败");
        }
    }
//2
    @ResponseBody
    @PutMapping(value = "/activities/{activityid}")
    public ApiResponse updateActivity(@RequestBody Activity activity, @PathVariable(value = "activityid") String activityId){
        try{
            activity.setId(activityId);
            Activity newActivity = as.upsertActivity(activity);
            if(newActivity==null){
                return new ApiResponse<>("update activity "+activityId+" failed", "修改活动失败");
            }else{
                return new ApiResponse<>(newActivity);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "update activity "+activityId+" failed", "修改活动失败");
        }
    }
//3
    @ResponseBody
    @GetMapping(value = "/activities")
    public ApiResponse getActivities(@RequestBody Filter filter, PageQuery pageQuery){
        try{
            PageQuery page = PageQuery.relocate(pageQuery, 9, 100);
            PagedData<Activity> result = as.getActivities(filter, page);
            if(result==null){
                return new ApiResponse<>("get activities failed.", "查询多个活动失败");
            }else{
                return new ApiResponse<>(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activities failed.", "查询多个活动失败");
        }
    }
//4
    @ResponseBody
    @PutMapping(value = "/activities/{activityid}/state")
    public ApiResponse publishActivity(@PathVariable(value = "activityid")String activityId, @RequestBody RequestActivity requestActivity){
        try{
            boolean result = as.changeActivityState(activityId, requestActivity.getType());
            if(result){
                return new ApiResponse<>(new HashMap());
            }else{
                return new ApiResponse<>("change activity "+activityId+" failed","修改活动发布状态失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "change activity "+activityId+" failed","修改活动发布状态失败");
        }
    }
//5
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/participants")
    public ApiResponse addParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity){
        try{
            boolean result = as.addUserIds(activityId, requestActivity.getParticipantIds(), 1);
            if(result){
                return new ApiResponse<>(new HashMap());
            }else{
                return new ApiResponse<>("add activity participants failed","增加活动参加者失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "add activity participants failed","增加活动参加者失败");
        }
    }
//6
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/realparticipants")
    public ApiResponse addRealParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity){
        try{
            boolean result = as.addUserIds(activityId, requestActivity.getParticipantIds(), 2);
            if(result){
                return new ApiResponse<>(new HashMap());
            }else{
                return new ApiResponse<>("add activity real participants failed","增加活动实际参加者失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "add activity real participants failed","增加活动实际参加者失败");
        }
    }
//7
    @ResponseBody
    @DeleteMapping(value = "/activities/{activityid}/participants")
    public ApiResponse deleteParticipants(@PathVariable(value = "activityid")String activityId, @RequestBody RequestActivity requestActivity){
        try{
            boolean result = as.removeParticipants(activityId, requestActivity.getParticipantIds());
            if(result){
                return new ApiResponse<>(new HashMap());
            }else{
                return new ApiResponse<>("remove activity participants failed","删除活动参加者失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "remove activity participants failed","删除活动参加者失败");
        }
    }
//8
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/participants")
    public ApiResponse getActivityParticipants(@PathVariable(value = "activityid") String activityId, PageQuery pageQuery, @RequestBody RequestActivity requestActivity){
        try{
            PagedData<Integer> result = as.getActivityUserIds(activityId, pageQuery, requestActivity.getUserId(), 1);
            if(result==null){
                return new ApiResponse<>("get activity"+activityId+" participants failed.","查询活动参加者失败");
            }else{
                return new ApiResponse<>(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activity"+activityId+" participants failed.","查询活动参加者失败");
        }
    }
//9
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/realparticipants")
    public ApiResponse getActivityRealParticipants(@PathVariable(value = "activityid") String activityId, @RequestBody RequestActivity requestActivity, PageQuery pageQuery){
        try{
            PageQuery page = PageQuery.relocate(pageQuery, 9, 100);
            PagedData<Integer> result = as.getActivityUserIds(activityId, pageQuery, requestActivity.getUserId(), 2);
            if(result==null){
                return new ApiResponse<>("get activity"+activityId+" participants failed.","查询活动参加者失败");
            }else{
                return new ApiResponse<>(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activity"+activityId+" participants failed.","查询活动参加者失败");
        }
    }
//10
    @ResponseBody
    @PostMapping(value = "/callback/acceptsignup")
    public ApiResponse acceptSignup(@RequestBody CallbackParam<Signup> callbackParam){
        try{
            String activityId = callbackParam.getApplication().getObject().getActivityId();
            Long userId = callbackParam.getApplication().getApplicantId();
            List<Integer> user = new ArrayList<>();
            user.add(userId.intValue());
            boolean result = as.addUserIds(activityId, user,1);
            if(result){
                return new ApiResponse<>(new HashMap());
            }else{
                return new ApiResponse<>("callback accept signup failed.","接受申请回调失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "callback accept signup failed.","接受申请回调失败");
        }
    }
// 11
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}")
    public ApiResponse getActivity(@PathVariable(value = "activityid") String activityId){
        try{
            Activity activity = as.getActivity(activityId);
            if(activity==null){
                return new ApiResponse<>("get activity "+activityId+" failed","查询单个活动失败");
            }else{
                return new ApiResponse<>(activity);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activity "+activityId+" failed","查询单个活动失败");
        }
    }
}
