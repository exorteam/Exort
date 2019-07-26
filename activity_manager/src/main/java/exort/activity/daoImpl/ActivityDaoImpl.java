package exort.activity.daoImpl;

import exort.activity.entity.PageList;

import exort.activity.dao.ActivityDao;
import exort.activity.entity.Activity;
import exort.activity.entity.Filter;
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
                    .set("realParticipantIds", activity.getRealParticipantIds()).set("tags", activity.getTags());

            mongoTemplate.upsert(query, update, Activity.class);
            return mongoTemplate.findById(activity.getId(), Activity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PageList<Activity> selectActivities(Filter select, int pagesize, int pagenum, int sortby) {
        try {
            Query query = new Query();
            if (select.getCreateTime() != null) {
                query.addCriteria(Criteria.where("createTime").gte(select.getCreateTime().getStart()).and("createTime")
                        .lte(select.getCreateTime().getEnd()));
            }
            if (select.getSignupTime() != null) {
                query.addCriteria(Criteria.where("SignupTime").gte(select.getSignupTime().getStart()).and("signupTime")
                        .lte(select.getSignupTime().getEnd()));
            }
            if (select.getStartTime() != null) {
                query.addCriteria(Criteria.where("createTime").gte(select.getStartTime().getStart()).and("createTime")
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
            if(activities==null){
                return new PageList<>(pagesize, pagenum, 0, null);
            }
            System.out.println(activities.size());
            int totalsize = activities.size();
            int pageSize = min(9, pagesize);
            int pageNum = pagenum * pagesize / pageSize;
            List<Activity> result = activities.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalsize));

            return new PageList<>(pageSize, pageNum, totalsize, result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity getActivity(String id) {
        try {
            System.out.println(id);
            Activity activity =  mongoTemplate.findById(id, Activity.class);
            System.out.println(activity.getId());
            return activity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PageList<Integer> getActivityUserIds(int pagesize, int pagenum, String activityid, int type) {
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
                int pageSize = min(100, pagesize);
                int pageNum = pagenum * pagesize / pageSize;
                List<Integer> result;
                if (userids.size() < pageNum * pageSize) {
                    result = new ArrayList<>();
                } else {
                    result = userids.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalsize));
                }
                return new PageList<Integer>(pageSize, pageNum, totalsize, result);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Integer> checkUserId(String activityid, int userId, int type) {
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
