package exort.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.auth.entity.AuthResponse;
import exort.auth.entity.UserAccount;
import exort.auth.service.AuthService;

@RestController
public class AuthController {
	@Autowired
	private AuthService service;

	@PostMapping("/login")
	public Map login(@RequestBody UserAccount account){
		return service.login(account.getUsername(),account.getPassword());
	}

	@PostMapping("/register")
	public String register(@RequestBody UserAccount account){
		int res = service.register(account.getUsername(),account.getPassword());
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
