package exort.apiserver;

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

}

