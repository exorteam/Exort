package exort.apiserver;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exort.apiserver.entity.UserInfo;
import exort.apiserver.repository.UserRepository;
import exort.apiserver.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService service;
	@Autowired
	private UserRepository repository;

	@After
	public void cleanTestRepository(){
		repository.deleteAll();
	}

	@Test
	public void createTestUserThenGetAndRemove(){
		final String username = UUID.randomUUID().toString();
		final String password = UUID.randomUUID().toString();

		UserInfo info = new UserInfo();
		info.setType(0);
		info.setUsername(username);
		info.setPassword(password);
		
		int id = service.createUser(info);
		Assert.assertNotEquals(id,-1);

		UserInfo info2 = service.getUserById(id);
		Assert.assertEquals(info,info2);

		UserInfo info3 = service.getUserByUsername(info.getUsername());
		Assert.assertEquals(info,info3);

		Assert.assertTrue(service.removeUserById(id));
		UserInfo info4 = service.getUserById(id);
		Assert.assertNull(info4);
		UserInfo info5 = service.getUserByUsername(username);
		Assert.assertNull(info5);
	}

	@Test
	public void authTest(){
		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
		int[] userTypes = {0,0,0,1};

		// 1. Inexistent username
		// 2. Wrong password
		// 3. log in as normal user
		// 4. log in as admin
		for(int i=0;i<4;i++){
			UserInfo user = new UserInfo();
			user.setUsername(UUID.randomUUID().toString());
			user.setPassword(UUID.randomUUID().toString());
			user.setType(userTypes[i]);
			user.setId(service.createUser(user));
			users.add(user);
		}

		int res1 = service.auth(users.get(0).getUsername()+UUID.randomUUID().toString().substring(0,5),
				users.get(0).getPassword());
		Assert.assertEquals(res1,-1);

		int res2 = service.auth(users.get(1).getUsername(),
				users.get(1).getPassword()+UUID.randomUUID().toString().substring(0,5));
		Assert.assertEquals(res2,-1);

		int res3 = service.auth(users.get(2).getUsername(),users.get(2).getPassword());
		Assert.assertEquals(res3,0);

		int res4 = service.auth(users.get(3).getUsername(),users.get(3).getPassword());
		Assert.assertEquals(res4,1);
	}
}

