package exort.apiserver.service.impl;

import java.util.Date;
import java.util.UUID;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.apiserver.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthServiceImpl extends RestTemplate implements AuthService {

	@Value("${exort.auth.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.auth.endpoint:localhost}")
    public void setEndpoint(String endpoint) {
	   	super.setEndpoint(endpoint);
		urlBase = "http://"+endpoint;
	}

	private String urlBase = "http://202.120.40.8:30728";
	private final String jwtSecretKey = UUID.randomUUID().toString().substring(0,5);
	
	public ApiResponse<String> login(AuthRequest req){
		log.info("Login: "+req.getUsername()+"/"+req.getPassword());

		final ApiResponse<Integer> res = request(new TypeToken<Integer>(){},
				req,HttpMethod.POST,"/validate");

		if(res.getData() == null){
			return new ApiResponse("LoginFailed","Wrong username or password");
		}

		return new ApiResponse(generateToken(res.getData(),req.getUsername()));
	}

	public ApiResponse<Integer> register(AuthRequest req){
		return request(new TypeToken<Integer>(){},
				HttpMethod.POST,"/register",req);
	}

	private String generateToken(int id,String usr){
		return Jwts.builder()
			.setSubject(usr)
			.claim("id", id)
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
			.compact();
	}

	public AuthResponse parseToken(String token){
		final Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
		AuthResponse response = new AuthResponse();
		response.setUsername(claims.getSubject());
		response.setId(claims.get("id",Integer.class));
		return response;
	}

}
