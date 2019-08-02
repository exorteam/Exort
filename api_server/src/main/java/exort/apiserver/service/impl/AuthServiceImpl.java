package exort.apiserver.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.AuthResponse;
import exort.apiserver.service.AuthService;

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

	public Map login(AuthRequest req){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(req,headers);
		ResponseEntity<Map> response = exchange(urlBase+"/login",method,requestEntity,Map.class);
		return response.getBody();
	}

	public String register(AuthRequest req){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(req,headers);
		ResponseEntity<String> response = exchange(urlBase+"/register",method,requestEntity,String.class);
		return response.getBody();
	}

	public AuthResponse auth(String token){
		HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(token,headers);
		ResponseEntity<AuthResponse> response = exchange(urlBase+"/auth",method,requestEntity,AuthResponse.class);
		return response.getBody();
	}
}
