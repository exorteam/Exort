package exort.apiserver.service.impl;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.AuthResponse;
import exort.apiserver.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private RestTemplate rt = new RestTemplate();
	
	public String login(AuthRequest req){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(req,headers);
		ResponseEntity<String> response = rt.exchange("http://202.120.40.8:30728/login",method,requestEntity,String.class);
		return response.getBody();
	}

	public int register(AuthRequest req){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(req,headers);
		ResponseEntity<Integer> response = rt.exchange("http://202.120.40.8:30728/register",method,requestEntity,Integer.class);
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
}
