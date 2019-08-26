package exort.apiserver.service;

import exort.api.http.common.entity.ApiResponse;
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

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class LoginResponse {

		private int uid;
		private String token;
		private String rtoken;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class RefreshResponse {

		private int uid;
		private String username;
		private String token;
		private String rtoken;

	}


	public ApiResponse<LoginResponse>	login(AuthRequest req);
	public ApiResponse<Integer> 		register(AuthRequest req);
	public ApiResponse<AuthResponse>	parseToken(String token);
	public ApiResponse<RefreshResponse> refreshToken(String token);

}
