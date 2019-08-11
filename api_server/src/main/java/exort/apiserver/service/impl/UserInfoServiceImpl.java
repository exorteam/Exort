package exort.apiserver.service.impl;

import java.util.HashMap;
import java.util.List;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
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


	public ApiResponse<UserInfo> getUserInfo(int id){
		return request(new TypeToken<UserInfo>(){},
				HttpMethod.GET,"/users/info",Integer.valueOf(id));
	}

	public ApiResponse<UserInfo> updateUserInfo(int id,UserInfo info){
		return request(new TypeToken<UserInfo>(){},
				info,HttpMethod.POST,"/users/info",Integer.valueOf(id));
	}

	public ApiResponse<Boolean> disableUser(int id,boolean disabled){
		HashMap<String,Boolean> param = new HashMap<>();
		param.put("disabled",disabled);
		return patchForObject(urlBase+"/"+String.valueOf(id),param,new ParameterizedTypeReference<ApiResponse<Boolean>>(){});
		this.pathfo
	}

	public ApiResponse<List<UserInfo>> getUserInfoInBatch(List<Integer> ids){
		return request(new TypeToken<UserInfo>(){},
				ids,HttpMethod.GET,"/users/info");
	}

	public ApiResponse<List<UserInfo>> getUserInfoByPage(int pageNum,int pageSize,String sortBy){
		HashMap<String,Object> params = new HashMap<>();
		params.put("pageNum",Integer.valueOf(pageNum));
		params.put("pageSize",Integer.valueOf(pageSize));
		params.put("sortBy",sortBy);
		return getForEntity(urlBase+"/page",new ParameterizedTypeReference<ApiResponse<List<UserInfo>>>(){},params);
	}

}

