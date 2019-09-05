package exort.api.http.finance.entity;

import exort.api.http.activity.entity.TimeRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Filters {
    private String associationId;
    private String keyword;
    private TimeRange timeRange;
}
