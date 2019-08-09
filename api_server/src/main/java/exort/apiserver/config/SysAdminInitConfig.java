package exort.apiserver.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.apiserver.service.AuthService;
import exort.apiserver.service.AuthService.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SysAdminInitConfig {

	private final String SYS_ADMIN_USER_USERNAME 	= "admin";
	private final String SYS_ADMIN_USER_PASSWORD 	= "123";
	private final String SYS_ADMIN_ROLE_NAME 		= "admin";
	private final String SYS_ADMIN_SCOPE_NAME 		= "sys";

	@Autowired
	private PermService permSvc;
	@Autowired
	private AuthService authSvc;

	@AllArgsConstructor
	public class SystemAdministratorInfo {
		public final Integer 	ID;
		public final String 	USERNAME;
		public final String 	PASSWORD;
		public final String 	ROLE_NAME;
		public final String 	SCOPE_NAME;
	}

	@Bean
	public SystemAdministratorInfo initSystemAdminAccount() throws ApiError {
		final AuthRequest req = new AuthRequest(SYS_ADMIN_USER_USERNAME,SYS_ADMIN_USER_PASSWORD);
		final String token = authSvc.login(req).getData();
		Integer id = null;
		
		if(token == null){
			log.info("Cannot found admin account, now registering...");

			final ApiResponse<Integer> res = authSvc.register(req);
			if(res.getData() == null){
				throw new ApiError(400,"adminAccountInitErr","Failed to create admin account{usr:"+SYS_ADMIN_USER_USERNAME+",pwd:"+SYS_ADMIN_USER_PASSWORD+"}");
			}
			id = res.getData();

		}
		else{
			id = authSvc.parseToken(token).getId();
		}

		if(permSvc.getRole(SYS_ADMIN_ROLE_NAME).getData() == null){
			log.info("Cannot found admin role, now creating...");

			if(permSvc.createRole(new Role(SYS_ADMIN_ROLE_NAME)).getData() == null){
				throw new ApiError(400,"adminPermissionInitErr","Failed to create admin role["+SYS_ADMIN_ROLE_NAME+"] during initiating");
			}

		}

		if(permSvc.hasRole(Long.valueOf(id),SYS_ADMIN_SCOPE_NAME,SYS_ADMIN_ROLE_NAME).getData() == null){
			log.info("User["+String.valueOf(id)+"] does not have admin role in sys scope, now granting...");

			if(permSvc.grantRoles(Long.valueOf(id),SYS_ADMIN_SCOPE_NAME,Arrays.asList(SYS_ADMIN_ROLE_NAME)).getData() == null){
				throw new ApiError(400,"adminPermissionInitErr","Failed to grant admin role["+SYS_ADMIN_ROLE_NAME+"] to admin scope["+SYS_ADMIN_SCOPE_NAME+"] to admin ID["+String.valueOf(id)+"] during initiating");
			}
		}

		return new SystemAdministratorInfo(
				id,
				SYS_ADMIN_USER_USERNAME,
				SYS_ADMIN_USER_PASSWORD,
				SYS_ADMIN_ROLE_NAME,
				SYS_ADMIN_SCOPE_NAME);
	}

}

