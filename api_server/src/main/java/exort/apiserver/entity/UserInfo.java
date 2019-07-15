package exort.apiserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserInfo {

	@Id
	private int id;

	private int type;
	private String username;
	private String password;
}
