package exort.api.http.activity.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Signup {

    private String activityId;

    public Signup(String activityId){
        this.activityId = activityId;
    }
}
