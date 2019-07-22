package exort.apiserver.serviceimpl;

import exort.apiserver.service.AssociationManagerService;
import lombok.Data;

import java.util.List;

public class AssociationServiceImpl implements AssociationManagerService {
    @Data
    public static class PatchAssociationInfo {
        private String description;
        private String type;
    }

    @Data
    public static class ApplicationAssociationInfo {
        private String userId;
        private String event;
        private Application application;

    }

    @Data
    public class Application<T> {
        Integer Id;
        Integer applicantId;
        String type;
        MyObject object;
        String materials[];
        String createTime;
        String handleTime;
        String state;//0 unhandled,1 accept,2 refused,3 canceled
    }
    @Data
    public class MyObject {
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
        private  Integer assoId;
        private  String reason;
    }
}
