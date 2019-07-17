package exort.activity.daoImpl;

import exort.activity.entity.PageList;

import exort.activity.dao.ActivityDao;
import exort.activity.entity.Activity;
import exort.activity.entity.Response;
import exort.activity.entity.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.min;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Response update(Activity activity) {
        try {
            Query query = Query.query(Criteria.where("_id").is(activity.getId()));
            Update update = new Update().set("associationIds", activity.getAssociationIds())
                    .set("createTime", "2019-07-01").set("publishTime", "2019-07-01")
                    .set("lastPublishTime", "2019-07-01").set("lastModifyTime", "2019-07-01")
                    .set("signupTime", activity.getSignupTime()).set("time", activity.getTime())
                    .set("title", activity.getTitle()).set("content", activity.getContent())
                    .set("publishState", activity.getPublishState()).set("signupState", activity.getSignupState())
                    .set("state", activity.getState()).set("ifReview", activity.isIfReview())
                    .set("ifOnlyMem", activity.isIfOnlyMem()).set("maxParticipants", activity.getMaxParticipants())
                    .set("materialTemplateIds", activity.getMaterialTemplateIds())
                    .set("participantIds", activity.getParticipantIds())
                    .set("realParticipantIds", activity.getRealParticipantIds()).set("tags", activity.getTags());

            mongoTemplate.upsert(query, update, Activity.class);
            return new Response<>(activity, "", "");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(null, "invalid error", "invalid message");
        }
    }

    @Override
    public PageList<Activity> selectActivities(Select select, int pagesize, int pagenum, int sortby) {
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
            if (select.getPublishState() != -1) {
                query.addCriteria(Criteria.where("publishState").is(select.getPublishState()));
            }
            if (select.getSignupState() != -1) {
                query.addCriteria(Criteria.where("signupState").is(select.getSignupState()));
            }
            if (select.getIfReview() != -1) {
                query.addCriteria(Criteria.where("ifReview").is(select.getIfReview()));
            }
            if (select.getIfOnlyMem() != -1) {
                query.addCriteria(Criteria.where("ifOnlyMem").is(select.getIfOnlyMem()));
            }
            if (select.getKeyword() != null) {
                query.addCriteria(Criteria.where("content").regex(select.getKeyword()));
            }
            if (!select.getTags().equals(new ArrayList<>())) {
                query.addCriteria(Criteria.where("tags").in(select.getTags()));
            }

            List<Activity> activities = mongoTemplate.find(query, Activity.class);
            int totalsize = activities.size();
            int pageSize = min(9, pagesize);
            int pageNum = pagenum * pagesize / pageSize;
            List<Activity> result = activities.subList(pageNum * pageSize, (pageNum + 1) * pageSize);

            return new PageList<>(pageSize, pageNum, totalsize, result);
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
                List<Integer> result = userids.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalsize));
                return new PageList<Integer>(pageSize, pageNum, totalsize, result);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
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
