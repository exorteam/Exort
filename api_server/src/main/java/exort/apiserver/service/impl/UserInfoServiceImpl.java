package exort.apiserver.service.impl;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

import exort.apiserver.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	private RestTemplate rt = new RestTemplate();

	public UserInfo getUserInfo(int id){
		ResponseEntity<RestResponse> response = rt.getForEntity("http://202.120.40.8:30728/users/info/"+String.valueOf(id),RestResponse.class);
		return (UserInfo)response.getBody().getData();
	}

	public UserInfo updateUserInfo(UserInfo info){
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserInfo> requestEntity = new HttpEntity<>(info,headers);
		ResponseEntity<RestResponse> response = rt.exchange("http://202.120.40.8:30728/users/info",method,requestEntity,RestResponse.class);
		return (UserInfo)response.getBody().getData();
	}

	public boolean disableUser(int id,boolean disabled){
		HashMap<String,Boolean> param = new HashMap<>();
		param.put("disabled",disabled);
		RestResponse<Object> response = rt.patchForObject("http://202.120.40.8:30728/users/info/"+String.valueOf(id),param,RestResponse.class);
		return response.getData() != null;
	}

}
