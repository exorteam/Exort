package exort.activity.serviceImpl;

import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.Activity;
import exort.api.http.activity.entity.ActivityTime;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.TimeRange;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ActivityServiceImplTest {

    @Autowired
    private ActivityService as;

    String globalid;

    @Test
    public void upsertActivity() {
        List<String> associationIds = new ArrayList<>();
        associationIds.add("32");

        List<TimeRange> timeList = new ArrayList<>();
        timeList.add(new TimeRange(new Date(), new Date()));
        ActivityTime time = new ActivityTime(0, timeList);

        List<Integer> materialTemplateIds = new ArrayList<>();
        materialTemplateIds.add(3);
        materialTemplateIds.add(2);

        List<Integer> participantIds = new ArrayList<>();
        participantIds.add(342);
        participantIds.add(42);

        List<Integer> realParticipantIds = new ArrayList<>();
        realParticipantIds.add(342);
        realParticipantIds.add(412);

        List<String> tags = new ArrayList<>();
        tags.add("运动");

        Activity activity = new Activity(associationIds, time, time, "demo", "hope to run test successfully", 1, 2, 1,
                true, false, 10, materialTemplateIds, participantIds, realParticipantIds, tags, "");

        activity.setId((new ObjectId()).toString());
        globalid = activity.getId();

        Activity activity1 = as.upsertActivity(activity);
        assertEquals(Activity.class, activity1.getClass());
    }

    @Test
    public void changeActivityState() {
        upsertActivity();

        String type1 = "publish";
        String type2 = "withdraw";
        boolean response = as.changeActivityState(globalid, type2);
        Activity activity = as.getActivity(globalid);
        assertEquals(0, activity.getPublishState());
        boolean response1 = as.changeActivityState(globalid, type1);
        activity = as.getActivity(globalid);
        assertEquals(1, activity.getPublishState());
    }

    @Test
    public void getActivities() {
        Filter filter = new Filter();
        filter.setKeyword(null);
        filter.setTags(null);
        filter.setCreateTime(null);
        filter.setStartTime(null);
        filter.setSignupTime(null);
        filter.setPublishState(0);
        filter.setSignupState(0);
        filter.setState(0);
        filter.setIfReview(1);
        filter.setIfOnlyMem(1);

        PageQuery page = new PageQuery(0,9,"time");
        PagedData<Activity> response = as.getActivities(filter, page);
        assertTrue(response.getPageSize() >= 0);
    }

    @Test
    public void addUserIds() {
        upsertActivity();

        List<Integer> userIds = new ArrayList<>();
        userIds.add(35);
        userIds.add(42);
        boolean response1 = as.addUserIds(globalid, userIds, 1);
        boolean response2 = as.addUserIds(globalid, userIds, 2);
        assertTrue(response1);
        assertTrue(response2);
    }

    @Test
    public void removeParticipants() {
        upsertActivity();

        List<Integer> userIds = new ArrayList<>();
        userIds.add(35);
        boolean ifok = as.removeParticipants(globalid, userIds);
        assertTrue(ifok);
    }

    @Test
    public void getActivityUserIds() {
        upsertActivity();

        PageQuery page = new PageQuery(0, 9);
        PageQuery page1 = new PageQuery(0, 0);
        PagedData<Integer> pageList1 = as.getActivityUserIds(globalid, page, 0, 1);
        PagedData<Integer> pageList2 = as.getActivityUserIds(globalid, page1, 32, 1);
        assertTrue(pageList1.getPageSize() >= 0);
        assertEquals(ArrayList.class, pageList2.getContent().getClass());
    }

    @Test
    public void getActivity() {
        upsertActivity();

        Activity activity = as.getActivity(globalid);
        assertEquals(Activity.class, activity.getClass());
    }

}