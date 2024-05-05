package com.email.controller;

import com.email.model.User;
import com.email.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String FROM ;

    @PostMapping("/register")
    public void createUser(@RequestBody User user){
        System.out.println(user.getEmail());
        userService.addUser(user);
    }

    @PostMapping("send/{mail}")
    public void sendMail(@PathVariable("mail") String mail) throws MessagingException {
        System.out.println("Mail is : "+ mail);
        userService.mailSend(mail , this.FROM);
    }

    @PostMapping("verify")
    public String verify(@RequestParam("otp") String otp){
        return userService.verifyOTP(otp);
    }
}
