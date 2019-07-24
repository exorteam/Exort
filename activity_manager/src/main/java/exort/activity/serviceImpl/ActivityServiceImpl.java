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
    public Activity upsertActivity(Activity activity){
        return ad.update(activity);
    }

    @Override
    public PageList<Activity> getActivities(Select select, int pagesize, int pagenum, int sortby){
         return ad.selectActivities(select, pagesize, pagenum, sortby);
    }

    @Override
    public boolean changeActivityState(String activityid, String type){
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
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUserIds(String activityid, List<Integer> userIds, int type){
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
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeParticipants(String activityid, List<Integer> participantIds){
        try{
            Activity activity = ad.getActivity(activityid);
            if(activity != null){
                List<Integer> oldp = activity.getParticipantIds();
                if(oldp!=null){
                    oldp.removeAll(participantIds);
                    activity.setParticipantIds(oldp);
                    ad.update(activity);
                    return true;
                }else{
                    return false;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PageList<Integer> getActivityUserIds(int pagesize, int pagenum, String activityid, int userId, int type){
        try{
            if(userId==0){
                return ad.getActivityUserIds( pagesize, pagenum, activityid, type);
            }{
                List<Integer> result = ad.checkUserId(activityid, userId, type);
                return new PageList<>(pagesize, pagenum, 1, result);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity getActivity(String acticityid){
        try{
            System.out.println(ad);
            return ad.getActivity(acticityid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
