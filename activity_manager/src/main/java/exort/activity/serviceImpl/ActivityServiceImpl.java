package exort.activity.serviceImpl;

import exort.activity.entity.ActivityInfo;
import exort.activity.repository.ActivityRepository;
import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.TimeRange;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;
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

        ActivityInfo ret = activityRepository.insert(activityInfo);

        return ret;
    }

    @Override
    public ActivityInfo updateActivity(ActivityInfo activityInfo) {

        ActivityInfo ret = activityRepository.save(activityInfo);

        return ret;
    }

    @Override
    public ActivityInfo getActivity(String id) {
        return activityRepository.findById(id).orElse(null);
    }

    private Criteria inTimeRange(String field, TimeRange timeRange) {
        Criteria criteria = Criteria.where(field);
        if (timeRange.getStart() != null) {
            criteria.gte(timeRange.getStart());
        }
        if (timeRange.getEnd() != null) {
            criteria.lte(timeRange.getEnd());
        }
        return criteria;
    }

    @Override
    public PagedData<ActivityInfo> getActivities(Filter filter, PageQuery pageQuery) {
        Query query = new Query();
        if (filter.getCreateTime() != null) {
            query.addCriteria(inTimeRange("createTime", filter.getCreateTime()));
        }
        if (filter.getSignupTime() != null) {
            query.addCriteria(inTimeRange("signupTime.time.start", filter.getSignupTime()));
        }
        if (filter.getStartTime() != null) {
            query.addCriteria(inTimeRange("time.time.start", filter.getStartTime()));
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
        if (filter.getTags() != null && !filter.getTags().isEmpty()) {
            query.addCriteria(Criteria.where("tags").in(filter.getTags()));
        }
        if (filter.getAssociationId() != null && !filter.getAssociationId().isEmpty()) {
            query.addCriteria(Criteria.where("associationIds").in(filter.getAssociationId()));
        }

        query.skip(pageQuery.getPageSize()*pageQuery.getPageNum());
        query.limit(pageQuery.getPageSize());
        String sortBy = pageQuery.getSortBy() == "publishTime" ? "publishTime" : "createTime";
        query.with(Sort.by(Sort.Direction.DESC, sortBy));

        long totalsize = mongoTemplate.count(query, ActivityInfo.class, "activity");
        List<ActivityInfo> activities = mongoTemplate.find(query, ActivityInfo.class, "activity");

        return new PagedData<ActivityInfo>(pageQuery.getPageNum(), pageQuery.getPageSize(), totalsize, activities);
    }

}
