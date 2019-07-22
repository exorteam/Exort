package exort.apiserver.serviceimpl;

import exort.apiserver.service.AssociationManagerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

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

    @Data
    public class Association {
        private Integer id;
        private String name;
        private String description;
        private String logo;
        private List<String> tags;
        private Integer state; //0 blocked,1 active
        private String reason;
        public Association(String _body){

            byte[] b = _body.getBytes();
            Base64 base64 = new Base64();
            b = base64.decode(b);
            String s = new String(b);
            Association association  = JSONObject.parseObject(str, Invoice.class);


        }
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
    }
    @Data
    public class AssociationFilterParams {
        private String keyword;
        private List<String> tags;
        private Integer state;
    }




    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public Response<Association> createAssociation(String _body){
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssociationInfo requestBody = AssociationInfo(_body);
        HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/associations",method,requestEntity,Response.class);

        return response.getBody();
    }

    public Response<AssociationList> listAssociations(String _body, Integer pageNum, Integer pageSize){
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssociationFilterParams requestBody = AssociationFilterParams(_body);
        HttpEntity<AssociationFilterParams> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/associations?pageNum="+pageNum.toString()+"&pageSize="+pageSize.toString(),method,requestEntity,Response.class);

        return response.getBody();
    }

    public Response<Association> getAssociation(String assoId){
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Object> deleteAssociation(String assoId ){
        HttpMethod method = HttpMethod.DELETE;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Association> editAssociation(String assoId, String _body){
        HttpMethod method = HttpMethod.PUT;
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssociationInfo requestBody = AssociationInfo(_body);
        HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Object> patchAssociation(String assoId, String _body){
        HttpMethod method = HttpMethod.PATCH;
        headers.setContentType(MediaType.APPLICATION_JSON);
        PatchAssociationInfo requestBody = PatchAssociationInfo(_body);
        HttpEntity<PatchAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,Response.class);
        return response.getBody();

    }

    public Response<Object> handleAsoociationApplication(String _body){
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        ApplicationAssociationInfo requestBody = ApplicationAssociationInfo(_body);
        HttpEntity<ApplicationAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30728/callback",method,requestEntity,Response.class);
        return response.getBody();

    }
}
