package exort.api.http.activity.entity;

import exort.api.http.review.entity.Application;
import lombok.Data;

@Data
public class Operation {

    private int operatorId;
    private String action;
    private Application<Signup> application;

    @Data
    private class Signup {
        private String activityId;
    }
}