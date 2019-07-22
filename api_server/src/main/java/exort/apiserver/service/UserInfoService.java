package exort.apiserver.service;

import lombok.Data;
import java.util.Date;

public interface UserInfoService {

	@Data
	public class UserInfo {

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

	public UserInfo getUserInfo(int id);
	public UserInfo updateUserInfo(UserInfo info);
	public boolean  disableUser(int id,boolean disabled);
}

