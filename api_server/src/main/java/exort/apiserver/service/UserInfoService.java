package exort.apiserver.service;

import java.util.List;
import java.util.Date;

import lombok.Data;

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

	@Data
	public class RestResponse<T> {

		private T	   data;
		private String error;
		private String message;

	}

	UserInfo getUserInfo(int id);
	UserInfo updateUserInfo(int id,UserInfo info);
	boolean  disableUser(int id,boolean disabled);
	List	 getUserInfoInBatch(List<Integer> ids);
	List 	 getUserInfoByPage(int pageNum,int pageSize,String sortBy);
}

