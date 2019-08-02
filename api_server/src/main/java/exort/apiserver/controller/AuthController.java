package exort.apiserver.controller;

import java.util.Map;

import exort.apiserver.entity.AuthTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.service.AuthService;
import exort.apiserver.service.AuthService.AuthRequest;
import exort.apiserver.service.AuthService.AuthResponse;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/auth/login")
	public ResponseEntity login(@RequestBody AuthRequest req){
		String token = (String)authService.login(req).get("token");
		if (token == null) {
			return new ResponseEntity<>("{\"error\":\"LoginFailed\",\"message\":\"Wrong username or password.\"}", HttpStatus.UNAUTHORIZED);
		} else {
			AuthResponse r = authService.auth(token);
			return new ResponseEntity<>(new AuthTokenResponse(token, token, r.getId(), r.getUsername()), HttpStatus.OK);
		}
	}

	@PostMapping("/auth/token")
	public AuthTokenResponse refreshToken(@RequestBody Map<String, String> body) {
		String rtoken = body.get("rtoken");
		AuthResponse r = authService.auth(rtoken);
		return new AuthTokenResponse(rtoken, rtoken, r.getId(), r.getUsername());
	}

	@PostMapping("/auth/register")
	public String register(@RequestBody AuthRequest req){
		return authService.register(req);
	}

}
