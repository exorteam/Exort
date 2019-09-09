package exort.apiserver.service.impl;

import java.util.Date;
import java.util.UUID;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.apiserver.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
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
		final ApiResponse<Integer> res = request(new TypeToken<Integer>(){},
				HttpMethod.POST,"/validate",req);
		if(res.getData() == null){
			throw new ApiError(403,"LoginErr","Null user ID when login");
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
