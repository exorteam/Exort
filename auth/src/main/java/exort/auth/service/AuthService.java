package exort.auth.service;

import exort.auth.entity.AuthResponse;

public interface AuthService {

	public String       login(String usr,String pwd);
	public int          register(String usr,String pwd);
	public int  		register(String usr,String pwd,int type);
	public AuthResponse auth(String token);
}
