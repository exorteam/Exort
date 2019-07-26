package exort.api.http.activity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TimeRange {

    private Date start;
    private Date end;


    public TimeRange(Date start, Date end){
        this.start = start;
        this.end = end;
    }

}
