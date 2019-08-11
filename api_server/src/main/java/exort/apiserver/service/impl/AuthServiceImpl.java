package exort.apiserver.service.impl;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.apiserver.component.JwtResolver;
import exort.apiserver.service.AuthService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthServiceImpl extends RestTemplate implements AuthService {

	@Value("${exort.auth.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.auth.endpoint:localhost}")
    public void setEndpoint(String endpoint) { super.setEndpoint(endpoint); }

	public ApiResponse<String> login(AuthRequest req){
		log.info("Login: "+req.getUsername()+"/"+req.getPassword());

		final ApiResponse<Integer> res = request(new TypeToken<Integer>(){},
				req,HttpMethod.POST,"/validate");

		if(res.getData() == null){
			return new ApiResponse("LoginFailed","Wrong username or password");
		}

		return new ApiResponse(JwtResolver.generateToken(res.getData(),req.getUsername()));
	}

	public ApiResponse<Integer> register(AuthRequest req){
		return request(new TypeToken<Integer>(){},
				req,HttpMethod.POST,"/register");
	}

	public AuthResponse parseToken(String token){
		return JwtResolver.parseToken(token);
	}

}
