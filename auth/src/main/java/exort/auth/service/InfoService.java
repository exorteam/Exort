package exort.auth.service;

import java.util.List;
import exort.auth.entity.UserInfo;

public interface InfoService {

	public UserInfo getUserInfo(int id);
	public boolean  updateUserInfo(UserInfo info);
	public boolean 	disableUser(int id, boolean disabled);
	public List<UserInfo> getUserInfoInBatch(Iterable<Integer> ids);

}
