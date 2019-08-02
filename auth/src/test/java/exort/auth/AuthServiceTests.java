package exort.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTests {

	@Test
	public void loadContext(){}

	//@Autowired
	//private AccountRepository accountRepository;
	//@Autowired
	//private InfoRepository infoRepository;
	//@Autowired
	//private AuthService service;

	//@Test
	//public void testAuthService(){
	//    UserAccount account = new UserAccount();
	//    account.setUsername(UUID.randomUUID().toString());
	//    account.setPassword(UUID.randomUUID().toString());

	//    int registerResult = service.register(account.getUsername(),null);
	//    Assert.assertEquals(registerResult,-1);
	//    Assert.assertFalse(accountRepository.existsByUsername(account.getUsername()));
		
	//    registerResult = service.register(account.getUsername(),account.getPassword());
	//    Assert.assertTrue(registerResult > 0);
	//    Assert.assertTrue(accountRepository.existsByUsername(account.getUsername()));
	//    account.setId(registerResult);
	//    final int testUserId = registerResult;

	//    registerResult = service.register(account.getUsername(),account.getPassword());
	//    Assert.assertEquals(registerResult,-2);

	//    Map<String,String> response = service.login(account.getUsername(),account.getPassword()+UUID.randomUUID().toString().substring(0,3));
	//    String token = response.get("token");
	//    Assert.assertNull(token);

	//    response = service.login(account.getUsername(),account.getPassword());
	//    token = response.get("token");
	//    Assert.assertNotNull(token);

	//    AuthResponse authRes = service.auth(token);
	//    Assert.assertEquals(account.getUsername(),authRes.getUsername());
	//    Assert.assertEquals(account.getId(),authRes.getId());

	//    accountRepository.deleteById(testUserId);
	//    infoRepository.deleteById(testUserId);

	//}

}
