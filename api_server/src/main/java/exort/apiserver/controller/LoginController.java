package exort.apiserver.controller;

import exort.apiserver.entity.UserInfo;
import exort.apiserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;
    @RequestMapping("/loginsuccess")
    public UserInfo loginSuccess(){
        System.out.println(SecurityContextHolder.getContext());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userService.getUserByUsername(userDetails.getUsername());
    }
    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

}