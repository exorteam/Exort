package exort.apiserver.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.apiserver.service.AuthService;
import exort.apiserver.service.AuthService.AuthRequest;
import exort.apiserver.service.AuthService.AuthResponse;
import exort.apiserver.service.AuthService.LoginResponse;
import exort.apiserver.service.AuthService.RefreshResponse;

@RestController
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
			throw new ApiError(403,"RequestErr","Cannot find arg \"rtoken\" in request");
		}
		return authSvc.refreshToken(rtoken);
	}

}
