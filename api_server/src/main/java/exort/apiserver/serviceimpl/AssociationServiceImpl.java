package exort.apiserver.serviceimpl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import exort.apiserver.service.AssociationManagerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.*;



import java.util.List;


@Service
public class AssociationServiceImpl implements AssociationManagerService {

    @Data
    public static class PatchAssociationInfo {
        private String description;
        private String type;
        public PatchAssociationInfo(String _body){
            byte[] b = _body.getBytes();
            Base64 base64 = new Base64();
            b = base64.decode(b);
            String s = new String(b);
            Gson gson = new Gson();
            PatchAssociationInfo association = gson.fromJson(s, PatchAssociationInfo.class);//对于javabean直接给出class实例
            this.description = association.description;
            this.type = association.type;
        }
    }

    @Data
    public static class ApplicationAssociationInfo {
        private String userId;
        private String event;
        private Application application;
        public ApplicationAssociationInfo(String _body){
            byte[] b = _body.getBytes();
            Base64 base64 = new Base64();
            b = base64.decode(b);
            String s = new String(b);
            Gson gson = new Gson();
            ApplicationAssociationInfo association = gson.fromJson(s, ApplicationAssociationInfo.class);//对于javabean直接给出class实例
            this.userId = association.userId;
            this.event = association.event;
            this.application = association.application;
        }

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

    @Data
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
    public class  AssociationInfo{
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
        public AssociationInfo(String _body){
            byte[] b = _body.getBytes();
            Base64 base64 = new Base64();
            b = base64.decode(b);
            String s = new String(b);
            Gson gson = new Gson();
            AssociationInfo association = gson.fromJson(s, AssociationInfo.class);//对于javabean直接给出class实例
            this.name = association.name;
            this.description = association.description;
            this.logo = association.logo;
            this.tags = association.tags;
        }
    }
    @Data
    public class AssociationFilterParams {
        private String keyword;
        private List<String> tags;
        private Integer state;
        public AssociationFilterParams(String _body){
            byte[] b = _body.getBytes();
            Base64 base64 = new Base64();
            b = base64.decode(b);
            String s = new String(b);
            Gson gson = new Gson();
            AssociationFilterParams association = gson.fromJson(s, AssociationFilterParams.class);//对于javabean直接给出class实例
            this.keyword = association.keyword;
            this.tags = association.tags;
            this.state = association.state;
        }
    }




    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public Response<Association> createAssociation(String _body){
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssociationInfo requestBody = new AssociationInfo(_body);
        HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/associations",method,requestEntity,Response.class);

        return response.getBody();
    }

    public Response<AssociationList> listAssociations(String _body, Integer pageNum, Integer pageSize){
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssociationFilterParams requestBody = new AssociationFilterParams(_body);
        HttpEntity<AssociationFilterParams> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/associations?pageNum="+pageNum.toString()+"&pageSize="+pageSize.toString(),method,requestEntity,Response.class);

        return response.getBody();
    }

    public Response<Association> getAssociation(String assoId){
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Object> deleteAssociation(String assoId ){
        HttpMethod method = HttpMethod.DELETE;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Association> editAssociation(String assoId, String _body){
        HttpMethod method = HttpMethod.PUT;
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssociationInfo requestBody = new AssociationInfo(_body);
        HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Object> patchAssociation(String assoId, String _body){
        HttpMethod method = HttpMethod.PATCH;
        headers.setContentType(MediaType.APPLICATION_JSON);
        PatchAssociationInfo requestBody = new PatchAssociationInfo(_body);
        HttpEntity<PatchAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();

    }

    public Response<Object> handleAsoociationApplication(String _body){
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        ApplicationAssociationInfo requestBody = new ApplicationAssociationInfo(_body);
        HttpEntity<ApplicationAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:8080/callback",method,requestEntity,Response.class);
        return response.getBody();

    }
}
