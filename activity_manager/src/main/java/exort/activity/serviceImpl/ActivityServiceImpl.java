package exort.activity.serviceImpl;

import exort.activity.entity.ActivityInfo;
import exort.activity.repository.ActivityRepository;
import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.Filter;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ActivityInfo createActivity(ActivityInfo activityInfo) {

        ActivityInfo ret = activityRepository.save(activityInfo);

        return ret;
    }

    @Override
    public ActivityInfo updateActivity(String id, ActivityInfo activityInfo) {

        activityInfo.setId(id);

        ActivityInfo ret = activityRepository.save(activityInfo);

        return ret;
    }

    @Override
    public ActivityInfo getActivity(String id) {
        if (activityRepository.findById(id).isPresent()) {
            return activityRepository.findById(id).get();
        }

        return null;
    }

    @Override
    public PagedData<ActivityInfo> getActivities(Filter filter, PageQuery pageQuery) {
        Query query = new Query();

        if (filter.getCreateTime() != null) {
            query.addCriteria(Criteria.where("createTime").gte(filter.getCreateTime().getStart())
                    .lte(filter.getCreateTime().getEnd()));
        }
        if (filter.getSignupTime() != null) {
            query.addCriteria(Criteria.where("signupTime.time.start").gte(filter.getSignupTime().getStart())
                    .lte(filter.getSignupTime().getEnd()));
        }
        if (filter.getStartTime() != null) {
            query.addCriteria(Criteria.where("time.time.start").gte(filter.getStartTime().getStart())
                    .lte(filter.getStartTime().getEnd()));
        }
        if (filter.getPublishState() != 0) {
            query.addCriteria(Criteria.where("publishState").is(filter.getPublishState()));
        }
        if (filter.getSignupState() != 0) {
            query.addCriteria(Criteria.where("signupState").is(filter.getSignupState()));
        }
        if (filter.getState() != 0) {
            query.addCriteria(Criteria.where("state").is(filter.getState()));
        }
        if (filter.getIfReview() != 0) {
            query.addCriteria(Criteria.where("ifReview").is(filter.getIfReview() == 2));
        }
        if (filter.getIfOnlyMem() != 0) {
            query.addCriteria(Criteria.where("ifOnlyMem").is(filter.getIfOnlyMem() == 2));
        }
        if (filter.getKeyword() != null) {
            query.addCriteria(Criteria.where("content").regex(filter.getKeyword()));
        }

        if (filter.getTags() != null && !filter.getTags().equals(new ArrayList<>())) {
            query.addCriteria(Criteria.where("tags").in(filter.getTags()));
        }

        List<ActivityInfo> activities = mongoTemplate.find(query, ActivityInfo.class, "activity");
        if (activities.size() == 0) {
            return new PagedData<>(pageQuery.getPageNum(), pageQuery.getPageSize(), 0L, null);
        }
        int totalsize = activities.size();
        List<ActivityInfo> result = activities.subList(pageQuery.getPageNum() * pageQuery.getPageSize(), min((pageQuery.getPageNum() + 1) * pageQuery.getPageSize(), totalsize));

        return new PagedData<ActivityInfo>(pageQuery.getPageNum(), pageQuery.getPageSize(), (long) totalsize, result);
    }

    @Override
    public PagedData<Integer> fromList2Paged(List<Integer> list, PageQuery pageQuery) {
        int pageNum = pageQuery.getPageNum(), pageSize = pageQuery.getPageSize(), totalSize = list.size();

        List<Integer> result = list.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalSize));

        return new PagedData<Integer>(pageNum, pageSize, (long) totalSize, result);
    }

}
