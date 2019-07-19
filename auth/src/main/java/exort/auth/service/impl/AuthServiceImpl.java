package exort.auth.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.auth.component.JwtKeyUtil;
import exort.auth.entity.AuthResponse;
import exort.auth.entity.UserAccount;
import exort.auth.repository.AccountRepository;
import exort.auth.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private JwtKeyUtil jwtKeyUtil;

	public String login(String usr,String pwd){
		if(usr == null || pwd == null){
			return "Username and password should not be empty";
		}
		if(!accountRepository.existsByUsername(usr)){
			return "No such username";
		}
		if(!accountRepository.findByUsername(usr).getPassword().equals(pwd)){
			return "Wrong password";
		}

		// generate jwt
		final UserAccount user = accountRepository.findByUsername(usr);
		String jwtToken = Jwts.builder()
			.setSubject(user.getUsername())
			.claim("id", user.getId())
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS256, jwtKeyUtil.getKey())
			.compact();

		return jwtToken;
	}

	public int register(String usr,String pwd){
		if(usr == null || pwd == null){
			return -1; // Username and password should not be empty
		}
		if(accountRepository.existsByUsername(usr)){
			return -2; // Username exists
		}

		int id = 2;
		while(accountRepository.existsById(id)){
			++id;
		}
		UserAccount account = new UserAccount();
		account.setUsername(usr);
		account.setPassword(pwd);
		account.setId(id);
		accountRepository.save(account);

		return id;
	}

	public AuthResponse auth(String token){
		final Claims claims = Jwts.parser().setSigningKey(jwtKeyUtil.getKey()).parseClaimsJws(token).getBody();
		AuthResponse response = new AuthResponse();
		response.setUsername(claims.getSubject());
		response.setId(claims.get("id",Integer.class));
		return response;
	}
}
