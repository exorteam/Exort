package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.perm.service.PermService;
import exort.api.http.common.entity.ApiResponse;

@RestController
@RequestMapping(path="/perm")
public class PermController {

	@Autowired
	private PermService service;

	@GetMapping("/admin")
	public ApiResponse checkIfIsAdministrator(@RequestAttribute("id") Long id){
		return service.hasRole(id,"System","admin");
	}
	
}
