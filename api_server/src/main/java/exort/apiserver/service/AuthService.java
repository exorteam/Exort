package exort.apiserver.service;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface AuthService {

	@Data
	@AllArgsConstructor
	public class AuthRequest {

		private String username;
		private String password;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class AuthResponse {

		private int id;
		private String username;

	}


	public Map          login(AuthRequest req);
	public String       register(AuthRequest req);
	public AuthResponse auth(String token);

}
