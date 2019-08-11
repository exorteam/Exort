package exort.apiserver.service.impl;

import java.util.HashMap;
import java.util.List;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.apiserver.service.UserInfoService;

@Service
public class UserInfoServiceImpl extends RestTemplate implements UserInfoService {

	@Value("${exort.auth.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.auth.endpoint:localhost}")
    public void setEndpoint(String endpoint) {super.setEndpoint(endpoint);}


	public ApiResponse<UserInfo> getUserInfo(int id){
		return request(new TypeToken<UserInfo>(){},
				HttpMethod.GET,"/users/info/"+String.valueOf(id));
	}

	public ApiResponse<UserInfo> updateUserInfo(int id,UserInfo info){
		return request(new TypeToken<UserInfo>(){},
				info,HttpMethod.POST,"/users/info/"+String.valueOf(id));
	}

	public ApiResponse<Boolean> disableUser(int id,boolean disabled){
		HashMap<String,Boolean> param = new HashMap<>();
		param.put("disabled",disabled);
		return request(new TypeToken<Boolean>(){},
				param,HttpMethod.POST,"/users/info/disable/"+String.valueOf(id));
	}

	public ApiResponse<List<UserInfo>> getUserInfoInBatch(List<Integer> ids){
		return request(new TypeToken<List<UserInfo>>(){},
				ids,HttpMethod.GET,"/users/info");
	}

	public ApiResponse<List<UserInfo>> getUserInfoByPage(int pageNum,int pageSize,String sortBy){
		return null;
	}

}

