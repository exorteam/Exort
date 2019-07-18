package exort.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.auth.entity.AuthResponse;
import exort.auth.entity.UserInfo;
import exort.auth.service.AuthService;

@RestController
public class AuthController {
	@Autowired
	private AuthService service;

	@PostMapping("/login")
	public String login(@RequestBody UserInfo info){
		return service.login(info.getUsername(),info.getPassword());
	}

	@PostMapping("/register")
	public String register(@RequestBody UserInfo info){
		int res = service.register(info.getUsername(),info.getPassword());
		switch(res){
			case 0 :
				return "Zero register error";
			case -1:
				return "Username and password should not be empty";
			case -2:
				return "Username exists";
			default:
				return "Register success";
		}
	}

	@PostMapping("/auth")
	public AuthResponse auth(@RequestBody String token){
		return service.auth(token);
	}
}
