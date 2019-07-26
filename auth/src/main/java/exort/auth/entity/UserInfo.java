package exort.auth.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserInfo {

	@Id
	private int 	id;

	private String 	nickname;
	private String 	description;
	private int 	gender;
	private Date 	birthday;
	private String 	name;
	private String 	studentId;
	private String 	phone;
	private String 	email;
	private String 	qqId;
	private String 	wechatId;
	private boolean enabled;

}
