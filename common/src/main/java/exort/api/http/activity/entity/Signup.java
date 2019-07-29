package exort.api.http.activity.entity;

import lombok.Data;

@Data
public class Signup {

    private String activityId;

    public Signup(String activityId){
        this.activityId = activityId;
    }
}
