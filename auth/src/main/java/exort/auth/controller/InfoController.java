package exort.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.auth.entity.RestResponse;
import exort.auth.entity.UserInfo;
import exort.auth.service.InfoService;

@RestController
@RequestMapping(path="/users")
public class InfoController {

	@Autowired
	private InfoService service;

	@GetMapping("/info")
	public RestResponse getUserInfo(int id){
		UserInfo info = service.getUserInfo(id);
		if(info != null){
			return new RestResponse<UserInfo>(info,"","");
		}else{
			return new RestResponse<Object>(null,"Get ERR","Error when getting user infomation with ID: "+String.valueOf(id));
		}
	}

	@PostMapping("/info")
	public RestResponse updateUserInfo(UserInfo info){
		if(service.updateUserInfo(info)){
			return new RestResponse<UserInfo>(service.getUserInfo(info.getId()),"","");
		}else{
			return new RestResponse<UserInfo>(null,"Update ERR","Error when updating user infomation with ID: "+String.valueOf(info.getId()));
		}
	}

	@PatchMapping("/info")
	public RestResponse disableUser(int id,boolean disabled){
		if(service.disableUser(id,disabled)){
			return new RestResponse<String>("{}","","");
		}else{
			return new RestResponse<Object>(null,"Disable ERR","Error when updating user infomation with ID: "+String.valueOf(id));
		}
	}

}
