package exort.apiserver.service;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface AuthService {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	class AuthRequest {

		private String username;
		private String password;

	}


	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	class AuthResponse {

		private int id;
		private String username;

	}


	Map          login(AuthRequest req);
	String       register(AuthRequest req);
	AuthResponse auth(String token);

}
