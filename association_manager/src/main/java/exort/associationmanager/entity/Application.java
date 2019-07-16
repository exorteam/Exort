package exort.associationmanager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

@Data
public class Application<T> {
    @Id
    String Id;
    String applicantId;
    String type;
    MyObject object;
    String materialIds[];
    String createdTime;
    String handledTime;
    String state;//0 unhandled,1 accept,2 refused,3 canceled
}
