package exort.auth.service;

import java.util.Map;

import exort.auth.entity.AuthResponse;

public interface AuthService {

	public Map          login(String usr,String pwd);
	public int          register(String usr,String pwd);
	public AuthResponse auth(String token);
}
