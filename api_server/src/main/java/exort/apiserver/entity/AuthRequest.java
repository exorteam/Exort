package exort.apiserver.entity;

import lombok.Data;

@Data
public class AuthRequest {

	private String username;
	private String password;

}
