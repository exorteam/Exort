package exort.apiserver.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.apiserver.entity.AuthRequest;
import exort.apiserver.entity.SystemAdminConstants;
import exort.apiserver.service.AuthService;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SysAdminInitConfig {

	private String SYS_ADMIN_USER_USERNAME 	= SystemAdminConstants.USERNAME;
	private String SYS_ADMIN_USER_PASSWORD 	= SystemAdminConstants.PASSWORD;
	private String SYS_ADMIN_ROLE_NAME 		= SystemAdminConstants.ROLE_NAME;
	private String SYS_ADMIN_SCOPE_NAME 	= SystemAdminConstants.SCOPE_NAME;
	private int    SYS_ADMIN_USER_ID		= SystemAdminConstants.ID;

	@Autowired
	private PermService permSvc;
	@Autowired
	private AuthService authSvc;

	@Bean
	public void initSystemAdminAccount() throws ApiError {
		AuthRequest req = new AuthRequest(SYS_ADMIN_USER_USERNAME,SYS_ADMIN_USER_PASSWORD);
		if(authSvc.login(req).get("token") != null)return;
		log.info("Cannot found admin account, now registering...");

		if(!authSvc.register(req).equals("Register success")){
			throw new ApiError(400,"adminAccountInitErr","Failed to create admin account{usr:"+SYS_ADMIN_USER_USERNAME+",pwd:"+SYS_ADMIN_USER_PASSWORD+"}");
		}
	}

	@Bean
	public void initSystemAdminPermission() throws ApiError {
		if(permSvc.getRole(SYS_ADMIN_ROLE_NAME).getData() == null){
			log.info("Cannot found admin role, now creating...");

			if(permSvc.createRole(new Role(SYS_ADMIN_ROLE_NAME)).getData() == null){
				throw new ApiError(400,"adminPermissionInitErr","Failed to create admin role["+SYS_ADMIN_ROLE_NAME+"] during initiating");
			}

		}

		if(!(permSvc.hasRole(Long.valueOf(SYS_ADMIN_USER_ID),SYS_ADMIN_SCOPE_NAME,SYS_ADMIN_ROLE_NAME).getData() == null)){
			log.info("User["+String.valueOf(SYS_ADMIN_USER_ID)+"] does not have admin role in sys scope, now granting...");

			if(permSvc.grantRoles(Long.valueOf(SYS_ADMIN_USER_ID),SYS_ADMIN_SCOPE_NAME,Arrays.asList(SYS_ADMIN_ROLE_NAME)).getData() == null){
				throw new ApiError(400,"adminPermissionInitErr","Failed to grant admin role["+SYS_ADMIN_ROLE_NAME+"] to admin scope["+SYS_ADMIN_SCOPE_NAME+"] to admin ID["+String.valueOf(SYS_ADMIN_USER_ID)+"] during initiating");
			}
		}
	}

}

