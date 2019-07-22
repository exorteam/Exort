package exort.apiserver.service;

import exort.apiserver.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface AssociationManagerService {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ResponseBody<T> {
        private T data;
        private String error;
        private String message;
    }

    public  ResponseBody getAssociation(String assoId);

    public ResponseBody listAssociations(String _body,Integer pageNum,Integer pageSize);

    public ResponseBody createAssociation(String _body);

    public ResponseBody deleteAssociation(String assoId );

    public ResponseBody editAssociation(String assoId, String _body);

    public  ResponseBody patchAssociation(String assoId,String _body);

    public  ResponseBody handleAsoociationApplication(String user_id, String type, Application app );
    //public int auth(String username,String password); // return code { -1:error, 0:user, 1:admin }
}
