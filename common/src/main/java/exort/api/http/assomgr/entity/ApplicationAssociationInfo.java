package exort.api.http.assomgr.entity;

import exort.api.http.review.entity.Application;
import lombok.Data;

@Data
public class ApplicationAssociationInfo {
    private String userId;
    private String event;
    private Application application;

}