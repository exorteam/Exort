package exort.api.http.activity.entity;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityTime {

    private int type;
    private List<TimeRange> time;

    public ActivityTime(int type, List<TimeRange> time){
        this.type = type;
        this.time = time;
    }
}
