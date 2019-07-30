package exort.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.auth.entity.RestResponse;
import exort.auth.entity.UserInfo;
import exort.auth.service.InfoService;

@RestController
@RequestMapping(path="/users/info")
public class InfoController {

	@Autowired
	private InfoService service;

	@GetMapping("/{id}")
	public RestResponse getUserInfo(@PathVariable("id") int id){
		UserInfo info = service.getUserInfo(id);
		if(info != null){
			return new RestResponse<UserInfo>(info,"","");
		}else{
			return new RestResponse<Object>(null,"Get ERR","Error when getting user infomation with ID: "+String.valueOf(id));
		}
	}

	@PostMapping("/{id}")
	public RestResponse updateUserInfo(@PathVariable("id") int id,@RequestBody UserInfo info){
		if(id == info.getId() && service.updateUserInfo(info)){
			return new RestResponse<UserInfo>(service.getUserInfo(info.getId()),"","");
		}else{
			return new RestResponse<UserInfo>(null,"Update ERR","Error when updating user infomation with ID: "+String.valueOf(info.getId()));
		}
	}

	@PatchMapping("/{id}")
	public RestResponse disableUser(@PathVariable("id") int id,@RequestParam boolean disabled){
		if(service.disableUser(id,disabled)){
			return new RestResponse<String>("{}","","");
		}else{
			return new RestResponse<Object>(null,"Disable ERR","Error when updating user infomation with ID: "+String.valueOf(id));
		}
	}

	@GetMapping("/page")
	public RestResponse getUserInfoByPage(@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam String sortBy){
		List<UserInfo> res = service.getUserInfoByPage(pageNum,pageSize,sortBy).getContent();
		return new RestResponse<List>(res,"","");
	}

	@GetMapping
	public RestResponse getUserInfoInBatch(@RequestBody List<Integer> ids){
		return new RestResponse<List>(service.getUserInfoInBatch(ids),"","");
	}

}
