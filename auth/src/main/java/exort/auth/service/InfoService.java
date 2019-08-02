package exort.auth.service;

import java.util.List;

import exort.api.http.common.entity.ApiResponse;
import exort.auth.entity.UserInfo;

public interface InfoService {

	public ApiResponse<UserInfo> 		getUserInfo(int id);
	public ApiResponse<UserInfo> 		updateUserInfo(UserInfo info);
	public ApiResponse<Boolean> 		disableUser(int id, boolean disabled);
	public ApiResponse<List<UserInfo>> 	getUserInfoInBatch(Iterable<Integer> ids);
	public ApiResponse<List<UserInfo>> 	getUserInfoByPage(int pageNum,int pageSize,String sortBy);

}
