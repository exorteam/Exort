package exort.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.auth.entity.UserAccount;
import exort.auth.service.AuthService;


@RestController
public class AuthController {
	@Autowired
	private AuthService service;
    
	@PostMapping("/register")
	public ApiResponse<Integer> register(@RequestBody UserAccount acc){
		return service.register(acc.getUsername(),acc.getPassword());
	}

    @PostMapping("/validate")
    public ApiResponse<Integer> validateAccount(@RequestBody UserAccount acc){
        return service.validateAccount(acc.getUsername(),acc.getPassword());
    }

	//@PostMapping("/auth")
	//public ApiResponse auth(@RequestBody String token){
	//    return service.auth(token);
	//}
    
	//@PostMapping("/login")
	//public ApiResponse login(@RequestBody UserAccount account){
	//    return service.login(account.getUsername(),account.getPassword());
	//}

}
