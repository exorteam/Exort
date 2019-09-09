package exort.apiserver.controller;

import java.util.Map;

import exort.apiserver.component.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.apiserver.service.AuthService;
import exort.apiserver.service.AuthService.AuthRequest;
import exort.apiserver.service.AuthService.AuthResponse;
import exort.apiserver.service.AuthService.LoginResponse;
import exort.apiserver.service.AuthService.RefreshResponse;
import exort.apiserver.service.AuthService.TokenPair;

@RestController
@RequestMapping(path="/auth")
public class AuthController {

	@Autowired
	private AuthService authSvc;

	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@RequestBody AuthRequest req){
		return authSvc.login(req);
	}

	@PostMapping("/register")
	public ApiResponse<Integer> register(@RequestBody AuthRequest req){
		return authSvc.register(req);
	}

	@PostMapping("/auth")
	public ApiResponse<AuthResponse> auth(@RequestBody String token){
		return authSvc.parseToken(token);
	}

	@PostMapping("/token")
	public ApiResponse<RefreshResponse> refreshToken(@RequestBody Map<String,String> req){
		final String rtoken = req.get("rtoken");
		if(rtoken == null){
			throw new ApiError(401,"RequestErr","Cannot find arg \"rtoken\" in request");
		}
		final AuthResponse jwtRes = JwtResolver.parseToken(rtoken);
		if (jwtRes == null) {
			throw new ApiError(401, "badToken", "Token invalid or expired.");
		}
		TokenPair tokenPair = authSvc.refreshToken(rtoken).getData();
		return new ApiResponse<>(new RefreshResponse(jwtRes.getId(),jwtRes.getUsername(),tokenPair.getToken(),tokenPair.getRtoken()));
	}

}
