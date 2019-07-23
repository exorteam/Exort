package exort.activity.serviceImpl;

import exort.activity.dao.ActivityDao;
import exort.activity.entity.*;
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
        Activity newActivity=  ad.update(activity);
        if(newActivity==null){
            return new Response<>(null, "invalid error", "invalid message");
        }else{
            return new Response<>(newActivity, "", "");
        }
    }

    @Override
    public Response getActivities(Select select, int pagesize, int pagenum, int sortby){
        PageList<Activity> result =  ad.selectActivities(select, pagesize, pagenum, sortby);
        if(result==null){
            return new Response<>(null, "invalid error", "invalid message");

        }else{
            return new Response<>(result, "","");
        }
    }

    @Override
    public Response changeActivityState(String activityid, String type){
        try{
            Activity activity = ad.getActivity(activityid);
            if(activity != null){
                if(type.equals("publish")){
                    activity.setPublishState(1);
                    System.out.println(activity.getPublishState());
                }else{
                    activity.setPublishState(0);
                    System.out.println(activity.getAssociationIds());
                }
                ad.update(activity);
                return new Response<>(new HashMap(), "", "");
            }
            return new Response<>(null, "invalid error","invalid message");
        }catch(Exception e){
            e.printStackTrace();
            return new Response<>(null, "invalid error","invalid message");
        }
    }

    @Override
    public Response addUserIds(String activityid, List<Integer> userIds, int type){
        try{
            System.out.println(userIds);
            Activity activity = ad.getActivity(activityid);
            if(activity!=null){
                if(type==1){
                    List<Integer> oldp = activity.getParticipantIds();
                    if(oldp!=null){
                        List<Integer> temp = new ArrayList<>(oldp);
                        temp.retainAll(userIds);
                        oldp.removeAll(temp);
                        oldp.addAll(userIds);
                    }else{
                        oldp = userIds;
                    }
                    activity.setParticipantIds(oldp);
                } else{
                    List<Integer> oldp = activity.getRealParticipantIds();
                    if(oldp!=null){
                        oldp.addAll(userIds);
                    }else{
                        oldp = userIds;
                    }
                    activity.setRealParticipantIds(oldp);
                }
                ad.update(activity);
                return new Response<>(new HashMap(), "","");
            }
            return new Response<>(null,"invalid error","invalid message");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null,"invalid error","invalid message");
        }
    }

    @Override
    public Response removeParticipants(String activityid, List<Integer> participantIds){
        try{
            Activity activity = ad.getActivity(activityid);
            if(activity != null){
                List<Integer> oldp = activity.getParticipantIds();
                if(oldp!=null){
                    oldp.removeAll(participantIds);
                    activity.setParticipantIds(oldp);
                    ad.update(activity);
                    return new Response<>(new HashMap(), "","");
                }else{
                    return new Response<>(null,"invalid error","invalid message");
                }
            }
            return new Response<>(null,"invalid error","invalid message");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null,"invalid error","invalid message");
        }
    }

    @Override
    public Response getActivityUserIds(int pagesize, int pagenum, String activityid, int userId, int type){
        try{
            if(userId==0){
                PageList<Integer> result =  ad.getActivityUserIds( pagesize, pagenum, activityid, type);
                if(result == null){
                    return new Response<>(null, "invalid error", "invalid message");
                }else{
                    return new Response<>(result, "", "");
                }
            }{
                List<Integer> result =  ad.checkUserId(activityid, userId, type);
                if(result==null){
                    return new Response<>(null,"invalid error", "invalid message");
                }else{
                    if(result.size()==0){
                        return new Response<>(new HashMap(), "","");
                    }else{
                        return new Response<>(result,"","");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null, "invalid error", "invalid message");
        }
    }

    @Override
    public Response getActivity(String acticityid){
        try{
            System.out.println(ad);
            Activity activity = ad.getActivity(acticityid);
            if(activity!=null){
                return new Response<>(activity, "","");
            }else{
                return new Response<>(null, "invalid error1","invalid message");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(null, "invalid error2","invalid message");
        }
    }
}
