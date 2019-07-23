package exort.apiserver.service;

import java.util.Map;
import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.AuthResponse;

public interface AuthService {

	public Map          login(AuthRequest req);
	public String       register(AuthRequest req);
	public AuthResponse auth(String token);

}
