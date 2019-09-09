package exort.auth.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.auth.entity.UserInfo;
import exort.auth.service.InfoService;

@RestController
@RequestMapping(path="/users/info")
public class InfoController {

	@Autowired
	private InfoService service;

	@GetMapping("/{id}")
	public ApiResponse<UserInfo> getUserInfo(@PathVariable("id") int id){
		return service.getUserInfo(id);
	}

	@PostMapping("/{id}")
	public ApiResponse<UserInfo> updateUserInfo(@PathVariable("id") int id,@RequestBody UserInfo info){
		if(id != info.getId()){
			throw new ApiError(400,"QueryErr","Id args differ when updating");
		}
		return service.updateUserInfo(info);
	}

	@PostMapping("/disable/{id}")
	public ApiResponse<Boolean> disableUser(@PathVariable("id") int id,@RequestBody Map<String,Boolean> args){
		Boolean disabled = args.get("disabled");
		if(disabled == null){
			throw new ApiError(400,"ArgErr","Missing arg \"disable\" when calling \"disableUser\"");
		}
		return service.disableUser(id,disabled);
	}

	@GetMapping("/page")
	public ApiResponse<List<UserInfo>> getUserInfoByPage(@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam String sortBy){
		return service.getUserInfoByPage(pageNum,pageSize,sortBy);
	}

	@GetMapping
	public ApiResponse<List<UserInfo>> getUserInfoInBatch(@RequestBody List<Integer> ids){
		return service.getUserInfoInBatch(ids);
	}

}
