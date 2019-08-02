package exort.auth.service;

import exort.api.http.common.entity.ApiResponse;

public interface AuthService {

	public ApiResponse<Integer> register(String usr,String pwd);
	public ApiResponse<Integer> validateAccount(String usr,String pwd);

	//public ApiResponse login(String usr,String pwd);
	//public ApiResponse auth(String token);
}
