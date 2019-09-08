package exort.apiserver.service.impl;

import com.google.common.reflect.TypeToken;

import exort.api.http.common.errorhandler.ApiError;
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

	public ApiResponse<LoginResponse> login(AuthRequest req){
		log.info("Login: "+req.getUsername()+"/"+req.getPassword());

		final ApiResponse<Integer> res = request(new TypeToken<Integer>(){},
				req,HttpMethod.POST,"/validate");

		if(res.getData() == null){
			return new ApiResponse("LoginFailed","Wrong username or password");
		}

		final Integer uid = res.getData();
		final String token = JwtResolver.generateToken(uid,req.getUsername());

		return new ApiResponse(new LoginResponse(uid,token,token));
	}

	public ApiResponse<Integer> register(AuthRequest req){
		return request(new TypeToken<Integer>(){},
				req,HttpMethod.POST,"/register");
	}

	public ApiResponse<AuthResponse> parseToken(String token){
		return new ApiResponse(JwtResolver.parseToken(token));
	}

	public ApiResponse<TokenPair> refreshToken(String rtoken){
		return new ApiResponse<>(new TokenPair(rtoken, rtoken));
	}
}
