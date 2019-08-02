package exort.apiserver.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.apiserver.service.UserInfoService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserInfoServiceImpl extends RestTemplate implements UserInfoService {

	private String urlBase = "http://202.120.40.8:30728/users/info";

	@Value("${exort.users.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.users.endpoint:localhost}")
    public void setEndpoint(String endpoint) {
	   	super.setEndpoint(endpoint);
		urlBase = "http://" + endpoint + "/users/info";
	}


	public UserInfo getUserInfo(int id){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(null,headers);
		ResponseEntity<RestResponse<UserInfo>> response = exchange(urlBase+"/"+String.valueOf(id),method,requestEntity,new ParameterizedTypeReference<RestResponse<UserInfo>>(){});
		return (UserInfo)response.getBody().getData();
	}

	public UserInfo updateUserInfo(int id,UserInfo info){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserInfo> requestEntity = new HttpEntity<>(info,headers);
		ResponseEntity<RestResponse<UserInfo>> response = exchange(urlBase+"/"+String.valueOf(id),method,requestEntity,new ParameterizedTypeReference<RestResponse<UserInfo>>(){});
		return response.getBody().getData();
	}

	public boolean disableUser(int id,boolean disabled){
		HashMap<String,Boolean> param = new HashMap<>();
		param.put("disabled",disabled);
		System.out.println(urlBase+"/"+String.valueOf(id));
		ResponseEntity<RestResponse> response = exchange(urlBase+"/"+String.valueOf(id)+"/state",HttpMethod.PUT,new HttpEntity<>(Boolean.valueOf(disabled)),RestResponse.class);
		System.out.println(response);
		return response.getBody().getData() != null;
	}

	public List getUserInfoInBatch(List<Integer> ids){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(ids,headers);
		log.info(ids.size());
		ResponseEntity<RestResponse<List<UserInfo>>> response = exchange(urlBase,method,requestEntity,new ParameterizedTypeReference<RestResponse<List<UserInfo>>>(){});
		System.out.println(response.getBody());
		return response.getBody().getData();
	}

	public List getUserInfoByPage(int pageNum,int pageSize,String sortBy){
		HashMap<String,Object> params = new HashMap<>();
		params.put("pageNum",Integer.valueOf(pageNum));
		params.put("pageSize",Integer.valueOf(pageSize));
		params.put("sortBy",sortBy);
		return (List)getForEntity(urlBase+"/page",RestResponse.class,params).getBody().getData();
	}

}

