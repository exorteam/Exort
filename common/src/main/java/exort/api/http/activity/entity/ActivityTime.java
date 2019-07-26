package exort.api.http.activity.entity;

import java.util.List;

import lombok.Data;

@Data
public class ActivityTime {

    private int type;
    private List<TimeRange> time;

}