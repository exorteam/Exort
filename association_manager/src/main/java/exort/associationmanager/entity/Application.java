package exort.associationmanager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
public class Application<T> {
    @Id
    String Id;
    String applicantId;
    String type;
    MyObject object;
    List<String> materialIds;
    String createdTime;
    String handledTime;
    String state;//0 unhandled,1 accept,2 refused,3 canceled
}
