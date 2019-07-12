package exort.activity.serviceImpl;

import exort.activity.dao.ActivityDao;
import exort.activity.entity.Activity;
import exort.activity.entity.ActivityTime;
import exort.activity.entity.Response;
import exort.activity.entity.Select;
import exort.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.RescaleOp;
import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao ad;

    @Override
    public Response upsertActivity(Activity activity){
        return ad.update(activity);
    }

    @Override
    public Response getActivities(Select select, int pagesize, int pagenum, int sortby){
        return ad.selectActivities(select, pagesize, pagenum, sortby);
    }

    @Override
    public Response changeActivityState(Long activityid, String type){
        try{
            Activity activity = ad.getActivity(activityid);
            if(activity != null){
                if(type.equals("publish")){
                    activity.setPublishState(1);
                }else{
                    activity.setPublishState(0);
                }
                ad.update(activity);
                return new Response<>(new HashMap(), "", "");
            }
            return new Response<>(null, "invalid error","invallid message");
        }catch(Exception e){
            e.printStackTrace();
            return new Response<>(null, "invalid error","invallid message");
        }
    }

    @Override
    public Response addUserIds(Long activityid, List<Long> userIds, int type){
        try{
            Activity activity = ad.getActivity(activityid);
            if(activity!=null){
                if(type==1){
                    List<Long> oldp = activity.getParticipantIds();
                    oldp.addAll(userIds);
                    activity.setParticipantIds(oldp);
                } else{
                    List<Long> oldp = activity.getRealParticipantIds();
                    oldp.addAll(userIds);
                    activity.setRealParticipantIds(oldp);
                }
                return new Response<>(new HashMap(), "","");
            }
            return new Response<>(null,"invalid error","invalid message");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null,"invalid error","invalid message");
        }
    }

    @Override
    public Response removeParticipants(Long activityid, List<Long> participantIds){
        try{
            Activity activity = ad.getActivity(activityid);
            if(activity!=null){
                List<Long> oldp = activity.getParticipantIds();
                oldp.removeAll(participantIds);
                activity.setParticipantIds(oldp);
                return new Response<>(new HashMap(), "","");
            }
            return new Response<>(null,"invalid error","invalid message");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null,"invalid error","invalid message");
        }
    }

    @Override
    public Response getActivityUserIds(int pagesize, int pagenum, Long activityid, Long userId, int type){
        try{
            if(userId==0){
                return ad.getActivityUserIds( pagesize, pagenum, activityid, userId, type);
            }{
                return ad.checkUserId(activityid, userId, type);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null, "invalid error", "invalid message");
        }
    }

    @Override
    public Response getActivity(Long acticityid){
        try{
            Activity activity = ad.getActivity(acticityid);
            if(acticityid!=null){
                return new Response<>(activity, "","");
            }else{
                return new Response<>(null, "invalid error","invalid message");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null, "invalid error","invalid message");
        }
    }
}
