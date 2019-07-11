package exort.apiserver.service;

import exort.apiserver.entity.UserInfo;

import java.util.List;

public interface UserService {

	public UserInfo getUserById(int id);
	public UserInfo getUserByUsername(String username);
	public UserInfo getCurrentUser();
	public int createUser(UserInfo info);
	public boolean updateUser(UserInfo info);
	public boolean removeUserById(int id);
	public List<UserInfo> getall();

	//public int auth(String username,String password); // return code { -1:error, 0:user, 1:admin }
}
