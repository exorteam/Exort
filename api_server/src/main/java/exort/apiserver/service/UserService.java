package exort.apiserver.service;

import exort.apiserver.entity.UserInfo;

public interface UserService {

	public UserInfo getUserById(int id);
	public UserInfo getUserByUsername(String username);
	public int createUser(UserInfo user);
	public boolean removeUserById(int id);

	//public int auth(String username,String password); // return code { -1:error, 0:user, 1:admin }
}
