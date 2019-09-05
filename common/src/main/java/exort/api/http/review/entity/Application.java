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
    private Date createdTime;
    private Date handledTime;
    private String state;

    private Long applicantId;
    private String type;
    private T details;
    private List<String> materialIds;

    public Application(Long applicantId, String type, T details) {
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
    }

    public Application(Long applicantId, String type, T details, List<String> materialIds) {
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
        this.materialIds = materialIds;
    }

}
