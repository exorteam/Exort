package exort.api.http.activity.entity;

import lombok.Data;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeRange {

    private Date start;
    private Date end;

}
