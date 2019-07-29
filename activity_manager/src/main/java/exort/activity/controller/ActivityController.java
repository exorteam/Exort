package exort.activity.controller;

import exort.activity.entity.*;
import exort.activity.service.ActivityService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
    public ApiResponse updateActivity(@RequestBody Activity activity, @PathVariable(value = "activityid") String activityid){
        try{
            activity.setId(activityid);
            Activity newActivity = as.upsertActivity(activity);
            if(newActivity==null){
                return new ApiResponse<>("update activity "+activityid+" failed", "修改活动失败");
            }else{
                return new ApiResponse<>(newActivity);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "update activity "+activityid+" failed", "修改活动失败");
        }
    }
//3
    @ResponseBody
    @GetMapping(value = "/activities")
    public ApiResponse getActivities(@RequestBody Filter select, @PathParam(value = "pagesize")int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby")int sortby){
        try{
            PageList<Activity> result = as.getActivities(select, pagesize, pagenum, sortby);
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
    public ApiResponse publishActivity(@PathVariable(value = "activityid")String activityid, @RequestBody Request request){
        try{
            boolean result = as.changeActivityState(activityid, request.getType());
            if(result){
                return new ApiResponse<>(new HashMap());
            }else{
                return new ApiResponse<>("change activity "+activityid+" failed","修改活动发布状态失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "change activity "+activityid+" failed","修改活动发布状态失败");
        }
    }
//5
    @ResponseBody
    @PostMapping(value = "/activities/{activityid}/participants")
    public ApiResponse addParticipants(@PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        try{
            boolean result = as.addUserIds(activityid, request.getParticipantIds(), 1);
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
    public ApiResponse addRealParticipants(@PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        try{
            boolean result = as.addUserIds(activityid, request.getParticipantIds(), 2);
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
    public ApiResponse deleteParticipants(@PathVariable(value = "activityid")String activityid, @RequestBody Request request){
        try{
            boolean result = as.removeParticipants(activityid, request.getParticipantIds());
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
    public ApiResponse getActivityParticipants(@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum, @PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        try{
            PageList<Integer> result = as.getActivityUserIds(pagesize, pagenum, activityid, request.getUserId(), 1);
            if(result==null){
                return new ApiResponse<>("get activity"+activityid+" participants failed.","查询活动参加者失败");
            }else{
                return new ApiResponse<>(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activity"+activityid+" participants failed.","查询活动参加者失败");
        }
    }
//9
    @ResponseBody
    @GetMapping(value = "/activities/{activityid}/realparticipants")
    public ApiResponse getActivityRealParticipants(@PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum") int pagenum, @PathVariable(value = "activityid") String activityid, @RequestBody Request request){
        try{
            PageList<Integer> result = as.getActivityUserIds(pagesize, pagenum, activityid, request.getUserId(), 2);
            if(result==null){
                return new ApiResponse<>("get activity"+activityid+" participants failed.","查询活动参加者失败");
            }else{
                return new ApiResponse<>(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activity"+activityid+" participants failed.","查询活动参加者失败");
        }
    }
//10
    @ResponseBody
    @PostMapping(value = "/callback/acceptsignup")
    public ApiResponse acceptSignup(@RequestBody Operation operation){
        try{
            System.out.println(operation.getApplication().getCreateTime());
            String activityid = operation.getApplication().getSignup().getActivityId();
            int userId = operation.getApplication().getApplicantId();
            List<Integer> user = new ArrayList<>();
            user.add(userId);
            boolean result = as.addUserIds(activityid,user,1);
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
    public ApiResponse getActivity(@PathVariable(value = "activityid") String activityid){
        try{
            Activity activity = as.getActivity(activityid);
            if(activity==null){
                return new ApiResponse<>("get activity "+activityid+" failed","查询单个活动失败");
            }else{
                return new ApiResponse<>(activity);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiError(401, "get activity "+activityid+" failed","查询单个活动失败");
        }
    }
}
