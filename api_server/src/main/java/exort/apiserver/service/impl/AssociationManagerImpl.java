

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.AuthResponse;
import exort.apiserver.service.AuthService;

import lombok.Data;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@Service
public class AssociationManagerImpl implements AuthService {

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
    private static class PatchAssociationInfo {
        private String description;
        private String type;
    }

    @Data
    private static class ApplicationAssociationInfo {
        private Integer userId;
        private String event;
        private Application application;
    }

    @Data
    public class ResponseBody<T> {
        private T data;
        private String error;
        private String message;
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
        private  String logo;
        private AssociationInfo data;
    }



	private RestTemplate restTemplate = new RestTemplate();

	public ResponseBody createAssociation(AssociationInfo requestBody){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/associations",method,requestEntity,ResponseBody.class);

		return response.getBody();
	}

	public ResponseBody listAssociations(AssociationFilterParams requestBody,Integer pageNum,Integer pageSize ){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssociationFilterParams> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/associations?pageNum="+pageNum.toString()+"&pageSize="+pageSize.toString(),method,requestEntity,ResponseBody.class);

		return response.getBody();
	}

	public ResponseBody getAssociation(String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();
    }

    public ResponseBody deleteAssociation(String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.DELETE;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();
    }

    public ResponseBody editAssociation(AssociationInfo requestBody,String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PUT;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();
    }

    public ResponseBody patchAssociation( PatchAssociationInfo requestBody,String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PATCH;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatchAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();

    }

    public ResponseBody handleAssociationApplication( ApplicationAssociationInfo requestBody){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ApplicationAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30728/callback",method,requestEntity,ResponseBody.class);
        return response.getBody();

	}
}
