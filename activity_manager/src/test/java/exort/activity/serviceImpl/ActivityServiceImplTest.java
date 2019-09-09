package exort.activity.serviceImpl;

<<<<<<< HEAD
import exort.activity.entity.*;
import exort.activity.service.ActivityService;
=======
import exort.activity.entity.ActivityInfo;
import exort.activity.repository.ActivityRepository;
import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.ActivityTime;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.TimeRange;
import exort.api.http.common.entity.PageQuery;
import org.junit.After;
import org.junit.Before;
>>>>>>> 8c3c95f92dd16ba2fc280a6b4d6844bf534ab1e7
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
=======
import org.springframework.test.context.ActiveProfiles;
>>>>>>> 8c3c95f92dd16ba2fc280a6b4d6844bf534ab1e7
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityService activityService;

<<<<<<< HEAD
    @Test
    public void upsertActivity() {
        List<Integer> associationIds = new ArrayList<>();
        associationIds.add(32);
=======
    String globalId;

    @Before
    public void before() {
        List<String> associationIds = new ArrayList<>();
        associationIds.add("32");
>>>>>>> 8c3c95f92dd16ba2fc280a6b4d6844bf534ab1e7

        List<TimeRange> timeList = new ArrayList<>();
        timeList.add(new TimeRange(new Date(), new Date()));
        ActivityTime time = new ActivityTime(0, timeList);

        List<Integer> materialTemplateIds = new ArrayList<>();
        materialTemplateIds.add(3);
        materialTemplateIds.add(2);

        List<String> tags = new ArrayList<>();
        tags.add("运动");

<<<<<<< HEAD
        Activity activity = new Activity(associationIds, time, time, "demo", "hope to run test successfully", 1, 2, 1,
                true, false, 10, materialTemplateIds, participantIds, realParticipantIds, tags, "");

        globalid = activity.getId();
=======
        String image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAmkAAAGyCAIAAAAwAsT0AAAACXBIWXMAAA7EAAAO";
        ActivityInfo activity = new ActivityInfo(associationIds, time, time, "first", "hope to run test successfully", true, false, 10, materialTemplateIds, tags, image);
>>>>>>> 8c3c95f92dd16ba2fc280a6b4d6844bf534ab1e7

        ActivityInfo activityInfo = activityRepository.save(activity);
        globalId = activityInfo.getId();
    }

<<<<<<< HEAD
    @Test
    public void changeActivityState() {
        upsertActivity();

        String type1 = "publish";
        String type2 = "withdraw";
        boolean response = as.changeActivityState(globalid, type2);
        assertTrue(response);
        boolean response1 = as.changeActivityState(globalid, type1);
        assertTrue(response1);
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

        PageList<Activity> response = as.getActivities(filter, 9, 0, 0);
        assertTrue(response.getPageSize() > 0);
    }
=======
    @After
    public void after() {
//        activityRepository.deleteAll();
    }

>>>>>>> 8c3c95f92dd16ba2fc280a6b4d6844bf534ab1e7

    @Test
    public void getAcitivities() {
        List<String> associationIds = new ArrayList<>();
        associationIds.add("32");

        List<TimeRange> timeList = new ArrayList<>();
        timeList.add(new TimeRange(new Date(), new Date()));
        ActivityTime time = new ActivityTime(0, timeList);

        List<Integer> materialTemplateIds = new ArrayList<>();
        materialTemplateIds.add(3);
        materialTemplateIds.add(2);

        List<String> tags = new ArrayList<>();
        tags.add("运动");

        String image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAmkAAAGyCAIAAAAwAsT0AAAACXBIWXMAAA7EAAAO";
        ActivityInfo activity = new ActivityInfo(associationIds, time, time, "first", "hope to run test successfully", true, false, 10, materialTemplateIds, tags, image);


        Filter filter1 = new Filter();
        System.out.println(filter1.getCreateTime());
        assertNull(filter1.getCreateTime());

        filter1.setIfOnlyMem(2);
        System.out.println(activityService.getActivities(filter1, new PageQuery(0, 9, "time")));
        filter1.setState(1);
        System.out.println(activityService.getActivities(filter1, new PageQuery(0, 9, "time")));
        filter1.setState(0);
        System.out.println(activityService.getActivities(filter1, new PageQuery(0, 9, "time")));
        filter1.setIfOnlyMem(0);
        System.out.println(activityService.getActivities(filter1, new PageQuery(0, 9, "time")));

       // assertEquals(1, activityService.getActivities(filter1, new PageQuery(0, 9, "time")).getContent().size());

    }


    @Test
    public void createActivity() {
        List<String> associationIds = new ArrayList<>();
        associationIds.add("32");

        List<TimeRange> timeList = new ArrayList<>();
        timeList.add(new TimeRange(new Date(), new Date()));
        ActivityTime time = new ActivityTime(0, timeList);

        List<Integer> materialTemplateIds = new ArrayList<>();
        materialTemplateIds.add(3);
        materialTemplateIds.add(2);

        List<String> tags = new ArrayList<>();
        tags.add("运动");

        String image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAmkAAAGyCAIAAAAwAsT0AAAACXBIWXMAAA7EAAAO";
        ActivityInfo activity = new ActivityInfo(associationIds, time, time, "demo", "hope to run test successfully", true, false, 10, materialTemplateIds, tags, image);

        ActivityInfo activity1 = activityService.createActivity(activity);
        assertEquals("demo", activity1.getTitle());
    }

    @Test
<<<<<<< HEAD
    public void getActivityUserIds() {
        upsertActivity();

        PageList<Integer> pageList1 = as.getActivityUserIds(9, 0, globalid, 0, 1);
        PageList<Integer> pageList2 = as.getActivityUserIds(0, 0, globalid, 32, 1);
        assertTrue(pageList1.getPageSize()>0);
        assertEquals(ArrayList.class, pageList2.getContent().getClass());
=======
    public void updateActivity() {
        ActivityInfo activityInfo = activityRepository.findById(globalId).get();
        activityInfo.setContent("asaedawdasd");

        ActivityInfo ret = activityService.updateActivity(activityInfo);

        assertEquals("asaedawdasd", ret.getContent());
>>>>>>> 8c3c95f92dd16ba2fc280a6b4d6844bf534ab1e7
    }

    @Test
    public void getAcitivity() {
        ActivityInfo activityInfo = activityService.getActivity(globalId);

        assertEquals("first", activityInfo.getTitle());
    }
}
