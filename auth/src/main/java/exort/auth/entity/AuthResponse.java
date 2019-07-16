package exort.auth.entity;

import lombok.Data;

@Data
public class AuthResponse {
	private int    id;
	private int    type;
	private String username;
}
