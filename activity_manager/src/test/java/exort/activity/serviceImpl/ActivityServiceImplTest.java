package exort.activity.serviceImpl;

import exort.activity.entity.*;
import exort.activity.service.ActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Autowired
    private ActivityService as;

    @Test
    public void upsertActivity() {
        List<Integer> associationIds = new ArrayList<>();
        associationIds.add(32);

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

        Activity activity = new Activity(associationIds, time, time, "demo", "hope to run test successfully", 1, 2, 0,
                true, false, 10, materialTemplateIds, participantIds, realParticipantIds, tags, "");

        Response response = as.upsertActivity(activity);
        System.out.println(response.getData());
        assertEquals(Activity.class, response.getData().getClass());
    }

    @Test
    public void getActivities() {
        Select select = new Select();
        select.setKeyword(null);
        select.setTags(null);
        select.setCreateTime(null);
        select.setStartTime(null);
        select.setSignupTime(null);
        select.setPublishState(-1);
        select.setSignupState(-1);
        select.setState(-1);
        select.setIfReview(1);
        select.setIfOnlyMem(1);

        Response response = as.getActivities(select, 9, 0, 0);
        assertEquals("", response.getError());
    }

    @Test
    public void changeActivityState() {
        String id = "5d312f49a5fabe2b278b82be";
        String type1 = "publish";
        String type2 = "withdraw";
        Response response = as.changeActivityState(id, type2);
        assertEquals(response.getError(), "");
        Response response1 = as.changeActivityState(id, type1);
        assertEquals(response1.getError(), "");
    }

    @Test
    public void addUserIds() {
        String activityid = "5d312f49a5fabe2b278b82be";
        List<Integer> userIds = new ArrayList<>();
        userIds.add(35);
        userIds.add(42);
        Response response1 = as.addUserIds(activityid, userIds, 1);
        Response response2 = as.addUserIds(activityid, userIds, 2);
        assertEquals("", response1.getError());
        assertEquals("", response2.getError());
    }

    @Test
    public void removeParticipants() {
        String activityid = "5d312f49a5fabe2b278b82be";
        List<Integer> userIds = new ArrayList<>();
        userIds.add(35);
        Response response = as.removeParticipants(activityid, userIds);
        assertEquals("", response.getError());
    }

    @Test
    public void getActivityUserIds() {
        String activityid = "5d312f49a5fabe2b278b82be";
        Response response1 = as.getActivityUserIds(9, 0, activityid, 0, 1);
        Response response2 = as.getActivityUserIds(0, 0, activityid, 32, 1);
        assertEquals("", response1.getError());
        assertEquals(new HashMap(), response2.getData());
    }

    @Test
    public void getActivity() {
        String id = "5d312f49a5fabe2b278b82be";
        Response response = as.getActivity(id);
        assertEquals("", response.getError());
    }
}