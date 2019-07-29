package exort.apiserver.service.impl;

import exort.api.http.common.RestTemplate;
import exort.apiserver.service.AssociationManagerService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


@Service
public class AssociationServiceImpl implements AssociationManagerService {

//    @Value("exort.assomanager.endpoint")
//    @Setter
//    @Getter
//    String endpoint;

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public Response<AssociationManagerService.Association> createAssociation(AssociationInfo body){
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(body,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/associations",HttpMethod.POST,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<AssociationList> listAssociations(AssociationFilterParams body, Integer pageNum, Integer pageSize){
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(body);
        HttpEntity<AssociationFilterParams> requestEntity = new HttpEntity<>(body,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/associations?pageNum="+pageNum.toString()+"&pageSize="+pageSize.toString(),HttpMethod.GET,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Association> getAssociation(String assoId){
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        System.out.println("http://202.120.40.8:30725/associations/"+assoId);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,HttpMethod.GET,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Object> deleteAssociation(String assoId ){
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,HttpMethod.DELETE,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Association> editAssociation(String assoId, AssociationInfo body){

        HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(body);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,HttpMethod.PUT,requestEntity,Response.class);
        return response.getBody();
    }

    public Response<Object> patchAssociation(String assoId, PatchAssociationInfo body){
        HttpMethod method = HttpMethod.PUT;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PatchAssociationInfo> requestEntity = new HttpEntity<>(body,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId+"/state",method,requestEntity,Response.class);
        return response.getBody();

    }

    public Response<Object> handleAsoociationApplication(ApplicationAssociationInfo body){
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ApplicationAssociationInfo> requestEntity = new HttpEntity<>(body,headers);
        ResponseEntity<Response> response = restTemplate.exchange("http://202.120.40.8:30725/callback",HttpMethod.POST,requestEntity,Response.class);
        return response.getBody();
    }
}
