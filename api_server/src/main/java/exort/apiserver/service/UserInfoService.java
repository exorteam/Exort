package exort.apiserver.service;

import java.util.Date;
import java.util.List;

import exort.api.http.common.entity.ApiResponse;
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

	ApiResponse<UserInfo> 		getUserInfo(int id);
	ApiResponse<UserInfo> 		updateUserInfo(int id,UserInfo info);
	ApiResponse<Boolean>  		disableUser(int id,boolean disabled);
	ApiResponse<List<UserInfo>> getUserInfoInBatch(List<Integer> ids);
	ApiResponse<List<UserInfo>>	getUserInfoByPage(int pageNum,int pageSize,String sortBy);
}
