package exort.api.http.review.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApplicationFilter {

    private Long applicantId;

    private String type;

    private String state;

    private Map<String, Object> details;

}
