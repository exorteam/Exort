package exort.apiserver.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SysAdminInitConfig {

	public final static String SYS_ADMIN_ROLE_NAME 	= "admin";
	public final static String SYS_ADMIN_SCOPE_NAME = "sys";
	public final static int    SYS_ADMIN_USER_ID	= 1;

	@Autowired
	private PermService permSvc;

	@Bean
	public void initSystemPermission() throws ApiError{
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

