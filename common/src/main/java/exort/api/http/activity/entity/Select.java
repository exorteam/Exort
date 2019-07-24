package exort.api.http.activity.entity;

import java.util.List;

import lombok.Data;

@Data
public class Select {

    private String keyword;
    private List<String> tags;
    private TimeRange createTime;
    private TimeRange startTime;
    private TimeRange signupTime;
    private int publishState;
    private int signupState;
    private int state;
    private int ifReview;
    private int ifOnlyMem;
}