package exort.apiserver.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import exort.apiserver.entity.UserInfo;
import exort.apiserver.repository.UserRepository;
import exort.apiserver.service.UserService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public UserInfo getUserById(int id){
		if(!userRepository.existsById(id)){
			return null;
		}
		return userRepository.findById(id).get();
	}

	public UserInfo getUserByUsername(String username){
		if(!userRepository.existsByUsername(username)){
			return null;
		}
		return userRepository.findByUsername(username);
	}

	public UserInfo getCurrentUser(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return getUserByUsername(userDetails.getUsername());
	}

	public List<UserInfo> getAllUsers(){
		return userRepository.findAll();
	}

	public int createUser(UserInfo user){
		if(userRepository.existsByUsername(user.getUsername())){
			return -1;
		}

		int id = 1;
		while(userRepository.existsById(id)){
			++id;
		}
		user.setId(id);

		userRepository.save(user);
		log.info("created user: "+user.getUsername()+":"+user.getPassword());

		return id;
	}

	public boolean updateUser(UserInfo info){
		if(!userRepository.existsById(info.getId())){
			return false;
		}

		userRepository.save(info);
		return true;
	}

	public boolean removeUserById(int id){
		userRepository.deleteById(id);
		return true;
	}

	//public int auth(String username,String password){
	//    if(!userRepository.existsByUsername(username)){
	//        return -1;
	//    }

	//    UserInfo user = userRepository.findByUsername(username);
	//    String pwd = user.getPassword();
	//    if(!pwd.equals(password)){
	//        return -1;
	//    }

	//    return user.getType();
	//}
}