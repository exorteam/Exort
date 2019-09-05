package exort.api.http.review.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ApplicationFilter {

    private Long applicantId;

    private String type;

    private String state;

    private Object details;

}
