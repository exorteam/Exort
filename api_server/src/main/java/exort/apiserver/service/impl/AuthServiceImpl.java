package exort.apiserver.service.impl;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import exort.apiserver.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class AuthServiceImpl implements AuthService {

	private RestTemplate rt = new RestTemplate();
	
	public Map login(AuthRequest req){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(req,headers);
		ResponseEntity<Map> response = rt.exchange("http://202.120.40.8:30728/login",method,requestEntity,Map.class);
		return response.getBody();
	}

	public String register(AuthRequest req){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(req,headers);
		ResponseEntity<String> response = rt.exchange("http://202.120.40.8:30728/register",method,requestEntity,String.class);
		return response.getBody();
	}

	public AuthResponse auth(String token){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(token,headers);
		ResponseEntity<AuthResponse> response = rt.exchange("http://202.120.40.8:30728/auth",method,requestEntity,AuthResponse.class);
		return response.getBody();
	}

	private String parseToken(String token){
		return null;
		//final Claims claims = Jwts.parser().setSigningKey(jwtKeyUtil.getKey()).parseClaimsJws(token).getBody();
		//AuthResponse response = new AuthResponse();
		//response.setUsername(claims.getSubject());
		//response.setId(claims.get("id",Integer.class));
		//return response;
	}
}
