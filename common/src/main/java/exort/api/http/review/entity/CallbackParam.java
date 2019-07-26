package exort.api.http.review.entity;

import lombok.Data;

@Data
public class CallbackParam<InfoClass> {
    private Long userId;
    private String event;
    private Application<InfoClass> application;
}
