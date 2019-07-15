package exort.auth.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.auth.entity.UserInfo;
import exort.auth.repository.UserRepository;
import io.jsonwebtoken.*;

@RestController
public class AuthController {
	@Autowired
	private UserRepository repository;

	@PostMapping("/login")
	public String login(@RequestBody UserInfo info){
		final String usr = info.getUsername();
		final String pwd = info.getPassword();

		if(usr == null || pwd == null){
			return "Username and password should not be empty";
		}
		if(!repository.existsByUsername(usr)){
			return "No such username";
		}
		if(!repository.findByUsername(usr).getPassword().equals(pwd)){
			return "Wrong password";
		}

		// generate jwt
		final UserInfo user = repository.findByUsername(usr);
		String jwtToken = Jwts.builder()
			.setSubject(user.getUsername())
			.claim("type", user.getType())
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS256, "secretKey")
			.compact();

		return jwtToken;
	}

	@PostMapping("/register")
	public String register(@RequestBody UserInfo info){
		final String usr = info.getUsername();
		final String pwd = info.getPassword();

		if(usr == null || pwd == null){
			return "Username and password should not be empty";
		}
		if(repository.existsByUsername(usr)){
			return "Username exists";
		}

		int id = 0;
		while(repository.existsById(id)){
			++id;
		}
		info.setId(id);
		repository.save(info);

		return "success";
	}
}
