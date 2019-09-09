package exort.apiserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.perm.service.PermService;
import exort.apiserver.config.SysAdminInitConfig.SystemAdministratorInfo;
import exort.apiserver.service.UserInfoService;
import exort.apiserver.service.UserInfoService.UserInfo;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(path="/users")
public class UserInfoController {

	@Autowired
	private UserInfoService infoSvc;
	@Autowired
	private PermService permSvc;
	@Autowired
	private SystemAdministratorInfo sysAdmin;

	@GetMapping("/{id}")
	public UserInfo getUserInfoById(@RequestAttribute("id") int operatorId,@PathVariable("id") int userId){
		log.info("Operator("+String.valueOf(operatorId)+") get user info of user("+String.valueOf(userId)+").");
		return infoSvc.getUserInfo(userId);
	}

	@GetMapping("/self")
	public UserInfo getCurrentUserInfo(@RequestAttribute("id") int operatorId){
		log.info("Operator("+String.valueOf(operatorId)+") get current user info.");
		return infoSvc.getUserInfo(operatorId);
	}

	@PostMapping("/{id}")
	public UserInfo updateUserInfoById(@RequestAttribute("id") int operatorId,@PathVariable("id") int userId,@RequestBody UserInfo info){
		log.info("Operator("+String.valueOf(operatorId)+") update user info of user("+String.valueOf(userId)+").");
		if(operatorId != userId){
			log.warn("Updating operation from another user should be rejected");
			return null;
		}
		return infoSvc.updateUserInfo(userId,info);
	}

	@PatchMapping("/{id}")
	public boolean disableUserById(@RequestAttribute("id") int operatorId,@PathVariable("id") int userId,@RequestParam boolean disabled){
		log.info("Operator("+String.valueOf(operatorId)+") toggle disability for user("+String.valueOf(userId)+").");
		if(permSvc.hasRole(Long.valueOf(operatorId),sysAdmin.SCOPE_NAME,sysAdmin.ROLE_NAME).getData() == null){
			log.warn("Disabling by non-admin user should be rejected");
			return false;
		}
		return infoSvc.disableUser(userId,disabled);
	}

	@PostMapping
	public List getUserInfoInBatch(@RequestBody List<Integer> ids){
		return infoSvc.getUserInfoInBatch(ids);
	}

	@GetMapping("/page")
	public List getUserInfoByPage(@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam String sortBy){
		return infoSvc.getUserInfoByPage(pageNum,pageSize,sortBy);
	}


}

