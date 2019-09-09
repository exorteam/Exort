package exort.activity.serviceImpl;

import exort.activity.entity.ActivityInfo;
import exort.activity.repository.ActivityRepository;
import exort.activity.service.ActivityService;
import exort.api.http.activity.entity.ActivityTime;
import exort.api.http.activity.entity.Filter;
import exort.api.http.activity.entity.TimeRange;
import exort.api.http.common.entity.PageQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityService activityService;

    String globalId;

    @Before
    public void before() {
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

        ActivityInfo activityInfo = activityRepository.save(activity);
        globalId = activityInfo.getId();
    }

    @After
    public void after() {
//        activityRepository.deleteAll();
    }


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
    public void updateActivity() {
        ActivityInfo activityInfo = activityRepository.findById(globalId).get();
        activityInfo.setContent("asaedawdasd");

        ActivityInfo ret = activityService.updateActivity(activityInfo);

        assertEquals("asaedawdasd", ret.getContent());
    }

    @Test
    public void getAcitivity() {
        ActivityInfo activityInfo = activityService.getActivity(globalId);

        assertEquals("first", activityInfo.getTitle());
    }
}
