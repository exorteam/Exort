package exort.api.http.activity.entity;

import exort.api.http.activity.service.ActivityService;
import java.util.List;

import lombok.Data;

@Data
public class Select {

    private String keyword;
    private List<String> tags;
    private ActivityService.TimeRange createTime;
    private ActivityService.TimeRange startTime;
    private ActivityService.TimeRange signupTime;
    private int publishState;
    private int signupState;
    private int state;
    private int ifReview;
    private int ifOnlyMem;
}