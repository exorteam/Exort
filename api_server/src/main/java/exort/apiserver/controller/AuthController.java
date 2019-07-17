package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.AuthResponse;
import exort.apiserver.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest req){
		return authService.login(req);
	}

	@PostMapping("/register")
	public int register(@RequestBody AuthRequest req){
		return authService.register(req);
	}

	@PostMapping("/auth")
	public AuthResponse auth(@RequestBody String token){
		return authService.auth(token);
	}

}
