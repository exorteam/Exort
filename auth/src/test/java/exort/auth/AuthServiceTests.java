package exort.auth;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exort.auth.entity.AuthResponse;
import exort.auth.entity.UserAccount;
import exort.auth.repository.AccountRepository;
import exort.auth.service.AuthService;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTests {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AuthService service;

	@Test
	public void testAuthService(){
		UserAccount account = new UserAccount();
		account.setUsername(UUID.randomUUID().toString());
		account.setPassword(UUID.randomUUID().toString());

		int registerResult = service.register(account.getUsername(),null);
		Assert.assertEquals(registerResult,-1);
		Assert.assertFalse(accountRepository.existsByUsername(account.getUsername()));
		
		registerResult = service.register(account.getUsername(),account.getPassword());
		Assert.assertTrue(registerResult > 0);
		Assert.assertTrue(accountRepository.existsByUsername(account.getUsername()));
		account.setId(registerResult);
		final int testUserId = registerResult;

		registerResult = service.register(account.getUsername(),account.getPassword());
		Assert.assertEquals(registerResult,-2);

		String token = service.login(account.getUsername(),account.getPassword());
		AuthResponse authRes = service.auth(token);
		Assert.assertEquals(account.getUsername(),authRes.getUsername());
		Assert.assertEquals(account.getId(),authRes.getId());

		accountRepository.deleteById(testUserId);

	}

}
