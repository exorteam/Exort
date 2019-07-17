package exort.apiserver.service;

import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.AuthResponse;

public interface AuthService {

	public String       login(AuthRequest req);
	public int          register(AuthRequest req);
	public AuthResponse auth(String token);

}
