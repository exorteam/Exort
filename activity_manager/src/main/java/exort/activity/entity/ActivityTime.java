package exort.activity.entity;

import java.util.ArrayList;
import java.util.List;

public class ActivityTime {

    private int type;

    private List<TimeRange> time;

    public ActivityTime(int type, List<TimeRange> time){
        this.type = type;
        this.time = time;
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

    public void setTime(List<TimeRange> time) {
        this.time = time;
    }

}
