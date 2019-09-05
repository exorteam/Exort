package exort.activity.serviceImpl;

import exort.activity.dao.ActivityDao;
import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public PagedData<Activity> getActivities(Filter filter, PageQuery pageQuery){
         return ad.selectActivities(filter, pageQuery.getPageSize(), pageQuery.getPageNum(), pageQuery.getSortBy());
    }

    @Override
    public boolean changeActivityState(String activityid, String type){
        try{
            System.out.println(type);
            return ad.updateActivityPublishState(activityid, type);
       }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUserIds(String activityid, List<Integer> userIds, int type){
        System.out.println(userIds);
        Activity activity = ad.getActivity(activityid);
        if (activity != null) {
            if (type==1) {
                List<Integer> oldp = activity.getParticipantIds();
                if (oldp!=null) {
                    List<Integer> temp = new ArrayList<>(oldp);
                    temp.retainAll(userIds);
                    oldp.removeAll(temp);
                    oldp.addAll(userIds);
                } else {
                    oldp = userIds;
                }
                activity.setParticipantIds(oldp);
            } else {
                List<Integer> oldp = activity.getRealParticipantIds();
                if (oldp != null) {
                    oldp.addAll(userIds);
                } else {
                    oldp = userIds;
                }
                activity.setRealParticipantIds(oldp);
            }
            ad.update(activity);
            return true;
        }
        return false;
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
    public PagedData<Integer> getActivityUserIds(String activityid, PageQuery pageQuery, Integer userId, int type){
        try{
            if(userId==0){
                return ad.getActivityUserIds(activityid, pageQuery.getPageSize(), pageQuery.getPageNum(), type);
            }{
                List<Integer> result = ad.checkUserId(activityid, userId, type);
                System.out.println(result);
                return new PagedData<Integer>(pageQuery.getPageSize(), pageQuery.getPageNum(), 1L, result);
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
