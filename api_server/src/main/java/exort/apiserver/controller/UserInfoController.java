package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.service.UserInfoService;
import exort.apiserver.service.UserInfoService.UserInfo;

@RestController
@RequestMapping(path="/users")
public class UserInfoController {

	@Autowired
	private UserInfoService service;

	@GetMapping("/info/{id}")
	public UserInfo getUserInfoById(@PathVariable("id") int id){
		return service.getUserInfo(id);
	}

	@PostMapping("/info/{id}")
	public UserInfo updateUserInfoById(@PathVariable("id") int id,@RequestBody UserInfo info){
		return service.updateUserInfo(id,info);
	}

	@PatchMapping("/info/{id}")
	public boolean disableUserById(@PathVariable("id") int id,@RequestParam boolean disabled){
		return service.disableUser(id,disabled);
	}
}
