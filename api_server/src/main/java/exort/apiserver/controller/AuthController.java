package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.apiserver.service.AuthService;
import exort.apiserver.service.AuthService.AuthRequest;
import exort.apiserver.service.AuthService.AuthResponse;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody AuthRequest req){
		return authService.login(req);
	}

	@PostMapping("/register")
	public ApiResponse<Integer> register(@RequestBody AuthRequest req){
		return authService.register(req);
	}

	@PostMapping("/auth")
	public ApiResponse<AuthResponse> auth(@RequestBody String token){
		return authService.parseToken(token);
	}

}
