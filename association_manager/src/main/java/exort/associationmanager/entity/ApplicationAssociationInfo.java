package exort.associationmanager.entity;

import lombok.Data;

@Data
public class ApplicationAssociationInfo {
    Application application;
    Long userId;
    String event;
}
