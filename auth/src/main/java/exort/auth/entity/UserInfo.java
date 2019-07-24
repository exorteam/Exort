package exort.auth.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserInfo {

	@Id
	private int    id;

	private String username;
	private String password;

}
