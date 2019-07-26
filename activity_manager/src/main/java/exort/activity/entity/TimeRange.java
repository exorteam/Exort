package exort.activity.entity;

import java.util.Date;

public class TimeRange{

    private Date start;

    private Date end;

    public TimeRange(Date start, Date end){
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
};