package exort.activity.daoImpl;

import exort.activity.dao.ActivityDao;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.Filter;
import exort.api.http.common.entity.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.min;

@Component
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Activity update(Activity activity) {
        try {
            Query query = Query.query(Criteria.where("_id").is(activity.getId()));



            Update update = new Update().set("associationIds", activity.getAssociationIds())
                    .set("createTime", new Date()).set("publishTime", new Date()).set("lastPublishTime", new Date())
                    .set("lastModifyTime", new Date()).set("signupTime", activity.getSignupTime())
                    .set("time", activity.getTime()).set("title", activity.getTitle())
                    .set("content", activity.getContent()).set("publishState", activity.getPublishState())
                    .set("signupState", activity.getSignupState()).set("state", activity.getState())
                    .set("ifReview", activity.isIfReview()).set("ifOnlyMem", activity.isIfOnlyMem())
                    .set("maxParticipants", activity.getMaxParticipants())
                    .set("materialTemplateIds", activity.getMaterialTemplateIds())
                    .set("participantIds", activity.getParticipantIds())
                    .set("realParticipantIds", activity.getRealParticipantIds()).set("tags", activity.getTags())
                    .set("image", activity.getImage());

            mongoTemplate.upsert(query, update, Activity.class);
            return mongoTemplate.findById(activity.getId(), Activity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateActivityPublishState(String activityId, String type) {
        try{
            Query query = Query.query(Criteria.where("_id").is(activityId));
            Update update;
            if(type.equals("publish")){
                update = new Update().set("publishState", 1);
            }else{
                update = new Update().set("publishState", 0);
            }
            System.out.println(type);
            mongoTemplate.upsert(query, update, Activity.class);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PagedData<Activity> selectActivities(Filter select, Integer pageSize, Integer pageNum, String sortBy) {
        try {
            Query query = new Query();
            System.out.println(select.toString());
            if (select.getCreateTime() != null) {
                query.addCriteria(Criteria.where("createTime").gte(select.getCreateTime().getStart())
                        .lte(select.getCreateTime().getEnd()));
            }
            if (select.getSignupTime() != null) {
                query.addCriteria(Criteria.where("signupTime").gte(select.getSignupTime().getStart())
                        .lte(select.getSignupTime().getEnd()));
            }
            if (select.getStartTime() != null) {
                query.addCriteria(Criteria.where("startTime").gte(select.getStartTime().getStart())
                        .lte(select.getStartTime().getEnd()));
            }
            if (select.getPublishState() != 0) {
                query.addCriteria(Criteria.where("publishState").is(select.getPublishState()));
            }
            if (select.getSignupState() != 0) {
                query.addCriteria(Criteria.where("signupState").is(select.getSignupState()));
            }
            if (select.getState() != 0) {
                query.addCriteria(Criteria.where("state").is(select.getState()));
            }
            if (select.getIfReview() != 0) {
                query.addCriteria(Criteria.where("ifReview").is(select.getIfReview() == 2));
            }
            if (select.getIfOnlyMem() != 0) {
                query.addCriteria(Criteria.where("ifOnlyMem").is(select.getIfOnlyMem() == 2));
            }
            if (select.getKeyword() != null) {
                query.addCriteria(Criteria.where("content").regex(select.getKeyword()));
            }

            if (select.getTags() != null && !select.getTags().equals(new ArrayList<>())) {
                query.addCriteria(Criteria.where("tags").in(select.getTags()));
            }
            System.out.println(query);

            List<Activity> activities = mongoTemplate.find(query, Activity.class, "activity");
            if (activities == null) {
                return new PagedData<>(pageNum, pageSize, 0L, null);
            }
            int totalsize = activities.size();
            List<Activity> result = activities.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalsize));

            return new PagedData<Activity>(pageNum, pageSize, (long) totalsize, result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity getActivity(String id) {
        try {
            return mongoTemplate.findById(id, Activity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PagedData<Integer> getActivityUserIds(String activityid, Integer pageSize, Integer pageNum, int type) {
        try {
            Activity activity = mongoTemplate.findById(activityid, Activity.class);
            if (activity != null) {
                List<Integer> userids;
                if (type == 1) {
                    userids = activity.getParticipantIds();
                } else {
                    userids = activity.getRealParticipantIds();
                }
                int totalsize = userids.size();
                List<Integer> result;
                if (userids.size() < pageNum * pageSize) {
                    result = new ArrayList<>();
                } else {
                    result = userids.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalsize));
                }
                return new PagedData<Integer>(pageNum, pageSize, (long) totalsize, result);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Integer> checkUserId(String activityid, Integer userId, int type) {
        try {
            Activity activity = mongoTemplate.findById(activityid, Activity.class);
            if (activity != null) {
                boolean exist;
                if (type == 1) {
                    List<Integer> ids = activity.getParticipantIds();
                    exist = ids.contains(userId);
                } else {
                    List<Integer> ids = activity.getRealParticipantIds();
                    exist = ids.contains(userId);
                }
                if (exist) {
                    List<Integer> userarray = new ArrayList<>();
                    userarray.add(userId);
                    return userarray;
                } else {
                    return new ArrayList<>();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
