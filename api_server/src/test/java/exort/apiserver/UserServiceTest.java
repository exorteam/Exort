package exort.apiserver;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import exort.apiserver.component.SecurityUtil;
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
	@Autowired
	private SecurityUtil securityUtil;

	private final int USER_TYPE_TEST = -10;

	@After
	public void cleanTestRepository(){
		repository.deleteAll(repository.findByType(USER_TYPE_TEST));
	}

	@Test
	public void testCRUD(){
		final String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();

		UserInfo info = new UserInfo();
		info.setType(USER_TYPE_TEST);
		info.setUsername(username);
		info.setPassword(password);
		
		final int id = service.createUser(info);
		Assert.assertNotEquals(id,-1);
		UserInfo info2 = service.getUserById(id);
		Assert.assertEquals(info,info2);

		password = UUID.randomUUID().toString();
		info.setPassword(password);
		service.updateUser(info);
		UserInfo info3 = service.getUserByUsername(info.getUsername());
		Assert.assertEquals(info,info3);

		Assert.assertTrue(service.removeUserById(id));
		UserInfo info4 = service.getUserById(id);
		Assert.assertNull(info4);
		UserInfo info5 = service.getUserByUsername(username);
		Assert.assertNull(info5);
	}

	@Test
	public void testCurrentUser(){
		UserInfo info = new UserInfo();
		info.setUsername(UUID.randomUUID().toString());
		info.setPassword(UUID.randomUUID().toString());
		info.setType(USER_TYPE_TEST);
		info.setId(service.createUser(info));

		securityUtil.logInAs(info.getUsername());
		UserInfo info2 = service.getCurrentUser();
		Assert.assertEquals(info,info2);
	}
		
}

