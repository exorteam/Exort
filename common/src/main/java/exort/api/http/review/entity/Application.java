package exort.api.http.review.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application<T> {
    private Long id;
    private Long applicantId;
    private String type;
    private T object;
    private List<String> materialIds;
    private Date createdTime;
    private Date handledTime;
    private String state;

    public Application(Long applicantId, String type, T object) {
        this.applicantId = applicantId;
        this.type = type;
        this.object = object;
    }
}
