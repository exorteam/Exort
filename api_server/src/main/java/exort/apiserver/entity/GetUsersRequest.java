package exort.apiserver.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetUsersRequest {
    private String scope;
    private String role;
    private Integer pageSize;
    private Integer pageNum;
}
