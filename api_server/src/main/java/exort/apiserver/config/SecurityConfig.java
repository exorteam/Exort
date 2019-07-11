package exort.apiserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import exort.apiserver.entity.UserInfo;
import exort.apiserver.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and()
			.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/user/create").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/loginsuccess").permitAll();
	 	http.csrf().disable();
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

	@Bean
	void createAdminAccount(){
		if(userService.getUserByUsername("admin") == null){
			UserInfo admin = new UserInfo();
			admin.setUsername("admin");
			admin.setPassword("123");
			admin.setType(1);
			userService.createUser(admin);
		}

	}

}
