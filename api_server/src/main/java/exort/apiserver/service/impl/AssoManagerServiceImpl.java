package exort.apiserver.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import exort.apiserver.service.AssoManagerService;
import exort.apiserver.service.AssoManagerService.ApplicationAssociationInfo;
import exort.apiserver.service.AssoManagerService.AssociationFilterParams;
import exort.apiserver.service.AssoManagerService.AssociationInfo;
import exort.apiserver.service.AssoManagerService.PatchAssociationInfo;

@Service
public class AssoManagerServiceImpl implements AssoManagerService {

	private RestTemplate restTemplate = new RestTemplate();

	public ResponseBody createAssociation(AssociationInfo requestBody){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/associations",method,requestEntity,ResponseBody.class);

		return response.getBody();
	}

	public ResponseBody listAssociations(AssociationFilterParams requestBody,Integer pageNum,Integer pageSize ){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssociationFilterParams> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/associations?pageNum="+pageNum.toString()+"&pageSize="+pageSize.toString(),method,requestEntity,ResponseBody.class);

		return response.getBody();
	}

	public ResponseBody getAssociation(String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();
    }

    public ResponseBody deleteAssociation(String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.DELETE;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();
    }

    public ResponseBody editAssociation(AssociationInfo requestBody,String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PUT;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();
    }

    public ResponseBody patchAssociation( PatchAssociationInfo requestBody,String assoId){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PATCH;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatchAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/associations/"+assoId,method,requestEntity,ResponseBody.class);
        return response.getBody();

    }

    public ResponseBody handleAssociationApplication( ApplicationAssociationInfo requestBody){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ApplicationAssociationInfo> requestEntity = new HttpEntity<>(requestBody,headers);
		ResponseEntity<ResponseBody> response = restTemplate.exchange("http://202.120.40.8:30725/callback",method,requestEntity,ResponseBody.class);
        return response.getBody();

	}
}
