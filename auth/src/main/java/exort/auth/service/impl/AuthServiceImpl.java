package exort.auth.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.auth.component.JwtKeyUtil;
import exort.auth.entity.UserInfo;
import exort.auth.entity.UserType;
import exort.auth.repository.UserRepository;
import exort.auth.service.AuthService;
import io.jsonwebtoken.*;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private JwtKeyUtil jwtKeyUtil;

	public String login(String usr,String pwd){
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
			.claim("id", user.getId())
			.claim("type", user.getType())
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS256, jwtKeyUtil.getKey())
			.compact();

		return jwtToken;
	}

	public String register(String usr,String pwd){
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
		UserInfo info = new UserInfo();
		info.setUsername(usr);
		info.setPassword(pwd);
		info.setId(id);
		info.setType(UserType.USER); // Normal user for default
		repository.save(info);

		return "success";
	}
}
