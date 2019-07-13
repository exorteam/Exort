package exort.apiserver.serviceimpl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import exort.apiserver.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MyUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
    public UserDetails loadUserByUsername(String usr) throws UsernameNotFoundException{
		String pwd = repository.findByUsername(usr).getPassword();

        if (pwd == null) {
            throw new UsernameNotFoundException(usr);
        }

		log.info("loaded user by username: "+usr);
        return new User(usr, passwordEncoder().encode(pwd), Collections.singletonList(new SimpleGrantedAuthority("User")));
    }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
