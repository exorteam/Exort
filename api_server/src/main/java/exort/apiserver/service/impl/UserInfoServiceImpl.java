package exort.apiserver.service.impl;

import java.util.List;
import java.util.HashMap;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import exort.apiserver.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	private RestTemplate rt = new RestTemplate();
	private final String urlBase = "http://202.120.40.8:30728/users/info/";

	public UserInfo getUserInfo(int id){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(null,headers);
		ResponseEntity<RestResponse<UserInfo>> response = rt.exchange(urlBase+String.valueOf(id),method,requestEntity,new ParameterizedTypeReference<RestResponse<UserInfo>>(){});
		return (UserInfo)response.getBody().getData();
	}

	public UserInfo updateUserInfo(int id,UserInfo info){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserInfo> requestEntity = new HttpEntity<>(info,headers);
		ResponseEntity<RestResponse<UserInfo>> response = rt.exchange(urlBase+String.valueOf(id),method,requestEntity,new ParameterizedTypeReference<RestResponse<UserInfo>>(){});
		return response.getBody().getData();
	}

	public boolean disableUser(int id,boolean disabled){
		HashMap<String,Boolean> param = new HashMap<>();
		param.put("disabled",disabled);
		RestResponse<Object> response = rt.patchForObject(urlBase+String.valueOf(id),param,RestResponse.class);
		return response.getData() != null;
	}

	public List getUserInfoInBatch(List<Integer> ids){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.GET;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List> requestEntity = new HttpEntity<>(ids,headers);
		ResponseEntity<RestResponse<List<UserInfo>>> response = rt.exchange(urlBase,method,requestEntity,new ParameterizedTypeReference<RestResponse<List<UserInfo>>>(){});
		return response.getBody().getData();
	}

	public List getUserInfoByPage(int pageNum,int pageSize,String sortBy){
		HashMap<String,Object> params = new HashMap<>();
		params.put("pageNum",Integer.valueOf(pageNum));
		params.put("pageSize",Integer.valueOf(pageSize));
		params.put("sortBy",sortBy);
		return (List)rt.getForEntity(urlBase+"/page",RestResponse.class,params).getBody().getData();
	}

}

