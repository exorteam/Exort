package exort.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exort.auth.entity.UserInfo;
import exort.auth.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class AdminConfig {
	@Autowired
	private UserRepository repository;

	private final int    ADMIN_ID       = 1;
	private final String ADMIN_USERNAME = "admin";
	private final String ADMIN_PASSWORD = "123";

	@Bean
	public void createDefaultAdmin(){
		if(repository.existsByUsername(ADMIN_USERNAME)){
			repository.delete(repository.findByUsername(ADMIN_USERNAME));
		}

		UserInfo admin = new UserInfo();
		admin.setId(ADMIN_ID);
		admin.setUsername(ADMIN_USERNAME);
		admin.setPassword(ADMIN_PASSWORD);
		repository.save(admin);

		log.info("Init admin with ["+ADMIN_ID+"|"+ADMIN_USERNAME+"|"+ADMIN_PASSWORD+"]");
	}
}
