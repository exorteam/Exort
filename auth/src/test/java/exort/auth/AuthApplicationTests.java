package exort.auth;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exort.auth.entity.AuthResponse;
import exort.auth.entity.UserInfo;
import exort.auth.repository.UserRepository;
import exort.auth.service.AuthService;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {

	@Autowired
	private UserRepository repository;
	@Autowired
	private AuthService service;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAuthService(){
		UserInfo info = new UserInfo();
		info.setUsername(UUID.randomUUID().toString());
		info.setPassword(UUID.randomUUID().toString());

		int registerResult = service.register(info.getUsername(),null);
		Assert.assertEquals(registerResult,-1);
		Assert.assertFalse(repository.existsByUsername(info.getUsername()));

		registerResult = service.register(info.getUsername(),info.getPassword());
		Assert.assertTrue(registerResult > 0);
		Assert.assertTrue(repository.existsByUsername(info.getUsername()));
		info.setId(registerResult);
		final int testUserId = registerResult;

		registerResult = service.register(info.getUsername(),info.getPassword());
		Assert.assertEquals(registerResult,-2);

		String token = service.login(info.getUsername(),info.getPassword());
		AuthResponse authRes = service.auth(token);
		Assert.assertEquals(info.getUsername(),authRes.getUsername());
		Assert.assertEquals(info.getId(),authRes.getId());

		repository.deleteById(testUserId);

	}

}
