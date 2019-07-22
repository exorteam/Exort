package exort.apiserver.service;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public interface AssoManagerService{

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
    public class AssociationFilterParams {
        private String keyword;
        private List<String> tags;
        private Integer state;
    }
    @Data
    public class  AssociationInfo{
        private String name;
        private String description;
        private List<String> tags;
        private String logo;
        private AssociationInfo data;
    }
    @Data
    public class Application<T> {
        private String Id;
        private String applicantId;
        private String type;
        private MyObject object;
        private List<String> materialIds;
        private String createdTime;
        private String handledTime;
        private String state;//0 unhandled,1 accept,2 refused,3 canceled
    }
    @Data
    public class MyObject {
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
        private  String associationId;
        private  String reason;
    }
    @Data
    public class ResponseBody<T> {
        private T data;
        private String error;
        private String message;
    }

    public ResponseBody listAssociations(AssociationFilterParams body,Integer pageNum,Integer pageSize );

    public ResponseBody getAssociation(String assoId);

    public ResponseBody createAssociation(AssociationInfo body);

    public ResponseBody deleteAssociation(String assoId );

    public ResponseBody editAssociation(AssociationInfo body,String assoId );

    public ResponseBody patchAssociation(PatchAssociationInfo body,String assoId );

    public ResponseBody handleAssociationApplication(ApplicationAssociationInfo body);

}
