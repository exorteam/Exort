package exort.auth.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserAccount {

	@Id
	private int    id;

	private String username;
	private String password;

}
