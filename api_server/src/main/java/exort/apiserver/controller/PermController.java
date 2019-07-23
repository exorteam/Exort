package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.perm.service.PermService;

@RestController
public class PermController {

	@Autowired
	private PermService service;


}
