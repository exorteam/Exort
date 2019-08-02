package exort.auth.service;

import exort.api.http.common.entity.ApiResponse;

public interface AuthService {

	public ApiResponse register(String usr,String pwd);
	public ApiResponse validateAccount(String usr,String pwd);

	//public ApiResponse login(String usr,String pwd);
	//public ApiResponse auth(String token);
}
