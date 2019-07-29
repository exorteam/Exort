package exort.apiserver.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface AssociationManagerService {

    public Response<Association> getAssociation(String assoId);

    public Response<AssociationList> listAssociations(AssociationFilterParams body, Integer pageNum, Integer pageSize);

    public Response<Association> createAssociation(AssociationInfo body);

    public Response<Object> deleteAssociation(String assoId );

    public Response<Association> editAssociation(String assoId, AssociationInfo body);

    public Response<Object> patchAssociation(String assoId, PatchAssociationInfo body);

    public Response<Object> handleAsoociationApplication(ApplicationAssociationInfo body);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchAssociationInfo {
        private String description;
        private String type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplicationAssociationInfo {
        private String userId;
        private String event;
        private Application application;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
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
    @AllArgsConstructor
    @NoArgsConstructor
    public class MyObject {
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
        private  Integer assoId;
        private  String reason;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Association {
        private Integer id;
        private String name;
        private String description;
        private String logo;
        private List<String> tags;
        private Integer state; //0 blocked,1 active
        private String reason;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AssociationList {
        List<Association> content;
        Integer totalSize;
        Integer pageSize;
        Integer pageNumber;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Response<T> {
        private T data;
        private String error;
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class  AssociationInfo{
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AssociationFilterParams {
        private String keyword;
        private List<String> tags;
        private Integer state;
    }


}
