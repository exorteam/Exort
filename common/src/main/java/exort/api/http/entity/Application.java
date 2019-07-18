package exort.api.http.entity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Application<T> {
    private int id;
    private int applicantId;
    private String type;
    private T object;
    private List<String> materialIds;
    private Date createdTime;
    private Date handledTime;
    private String state;
}
