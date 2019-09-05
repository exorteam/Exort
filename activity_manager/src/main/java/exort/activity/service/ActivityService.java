package exort.activity.service;

import exort.activity.entity.ActivityInfo;
import exort.api.http.activity.entity.Filter;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

import java.util.List;

public interface ActivityService {

    ActivityInfo createActivity(ActivityInfo activityInfo);

    ActivityInfo updateActivity(String id, ActivityInfo activityInfo);

    ActivityInfo getActivity(String id);

    PagedData<ActivityInfo> getActivities(Filter filter, PageQuery pageQuery);

    PagedData<Integer> fromList2Paged(List<Integer> list,PageQuery pageQuery);

}
