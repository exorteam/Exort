package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.entity.UserInfo;
import exort.apiserver.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path="/user")
public class UserServiceController {

	@Autowired
	private UserService service;

	//@PostMapping("/auth")
	//public int auth(@RequestParam String usr, @RequestParam String pwd){
	//    return service.auth(usr,pwd);
	//}

	@PostMapping("/create")
	public int createUser(@RequestBody UserInfo info){
		return service.createUser(info);
	}

	@GetMapping("/info")
	public UserInfo getCurrentUser(){
		return service.getCurrentUser();
	}

	@PostMapping("/update")
	public boolean updateUser(@RequestBody UserInfo info){
		return service.updateUser(info);
	}

	@PostMapping("/delete")
	public boolean deleteUserById(@RequestParam int id){
		return service.removeUserById(id);
	}

	@GetMapping("/all")
	public List<UserInfo> getall(){
		return service.getall();
	}
}
