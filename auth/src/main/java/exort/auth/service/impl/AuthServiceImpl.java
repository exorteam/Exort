package exort.auth.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.auth.component.AutoIncIdGenerator;
import exort.auth.entity.CommunityMessage;
import exort.auth.entity.UserAccount;
import exort.auth.entity.UserCommunityEntity;
import exort.auth.entity.UserInfo;
import exort.auth.repository.AccountRepository;
import exort.auth.repository.CommunityRepository;
import exort.auth.repository.InfoRepository;
import exort.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AccountRepository accRepo;
	@Autowired
	private InfoRepository infoRepo;
	@Autowired
	private CommunityRepository cmRepo;
	@Autowired
	private AutoIncIdGenerator autoId;

	public ApiResponse<Integer> register(String usr,String pwd){
		if(usr == null || pwd == null){
			throw new ApiError(403,"RegisterationErr","Username or password empty");
		}
		if(accRepo.existsByUsername(usr)){
			throw new ApiError(403,"RegisterationErr","Username already exists");
		}

		int id = autoId.getNextId("userId");
		UserAccount account = new UserAccount();
		account.setUsername(usr);
		account.setPassword(pwd);
		account.setId(id);
		accRepo.save(account);

		UserInfo info = new UserInfo();
		info.setId(id);
		info.setEnabled(true);
		infoRepo.save(info);

		UserCommunityEntity e = new UserCommunityEntity();
		e.setId(id);
		e.setSubscribed(new ArrayList<String>());
		e.setMessages(new ArrayList<CommunityMessage>());
		cmRepo.save(e);

		return new ApiResponse<Integer>(account.getId());
	}

	public ApiResponse<Integer> validateAccount(String usr,String pwd){
		final UserAccount e = accRepo.findByUsername(usr);
		if(e == null){
			throw new ApiError(403,"ValidationErr","Cannot find such username.");
		}

		if(!e.getPassword().equals(pwd)){
			throw new ApiError(403,"ValidationErr","Password doesnot match account.");
		}

		return new ApiResponse<Integer>(e.getId());
	}

	//public ApiResponse login(String usr,String pwd){
	//    if(usr == null || pwd == null){
	//        return new HashMap<>();
	//    }
	//    if(!accRepo.existsByUsername(usr)){
	//        return new HashMap<>();
	//    }
	//    if(!accRepo.findByUsername(usr).getPassword().equals(pwd)){
	//        return new HashMap<>();
	//    }

	//    // generate jwt
	//    final UserAccount user = accRepo.findByUsername(usr);
	//    String jwtToken = Jwts.builder()
	//        .setSubject(user.getUsername())
	//        .claim("id", user.getId())
	//        .setIssuedAt(new Date())
	//        .signWith(SignatureAlgorithm.HS256, jwtKeyUtil.getKey())
	//        .compact();

	//    HashMap<String,String> response = new HashMap<>();
	//    response.put("token",jwtToken);

	//    return response;
	//}

	//public ApiResponse auth(String token){
	//    final Claims claims = Jwts.parser().setSigningKey(jwtKeyUtil.getKey()).parseClaimsJws(token).getBody();
	//    AuthResponse response = new AuthResponse();
	//    response.setUsername(claims.getSubject());
	//    response.setId(claims.get("id",Integer.class));
	//    return response;
	//}

}
