package exort.activity.entity;

import java.util.ArrayList;
import java.util.List;

public class ActivityTime {

    private int type;
    private List<TimeRange> time;

    public ActivityTime(int type, TimeRange timeRange){
        this.type = type;
        List<TimeRange> tr = new ArrayList<>();
        tr.add(timeRange);
        this.time = tr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<TimeRange> getTime() {
        return time;
    }

    public void setTime(TimeRange time) {
        List<TimeRange> tr = new ArrayList<>();
        tr.add(time);
        this.time = tr;
    }

}
