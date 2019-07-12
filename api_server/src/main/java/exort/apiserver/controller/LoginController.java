package exort.apiserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.entity.UserInfo;
import exort.apiserver.service.UserService;

@RestController
public class LoginController {

    @Autowired
    private UserService service;

    @GetMapping("/loginsuccess")
    public UserInfo loginSuccess(){
        return service.getCurrentUser();
    }

    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

}
