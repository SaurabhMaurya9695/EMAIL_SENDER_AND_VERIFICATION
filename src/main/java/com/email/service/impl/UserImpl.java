package com.email.service.impl;


import com.email.model.User;
import com.email.repo.UserRepo;
import com.email.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    private HashMap<String, Object> mp = new HashMap<>() ;
    @Override
    public String addUser(User user) {
        userRepo.save(user);
        return "USER_ADDED_SUCCESSFULLY";
    }

    @Override
    public String mailSend(String email , String From ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        message.setFrom(From);
        message.setSubject("OTP FOR LOGIN");
        int otp = (int) (Math.random() * 999999);
        System.out.println("otp is : " +  otp );
        String msg ="OTP FOR LOGIN IS : " + "<h2>" + otp +  "<h2>" ;
        message.setText(msg, true);
        message.setTo(email);
        javaMailSender.send(mimeMessage);
        mp.put("OTP", otp);
        mp.put("EMAIL", email);
        return "MAIL SEND SUCCESSFULLY";

    }

    @Override
    public String verifyOTP(String OTP) {

        String email = (String) mp.get("EMAIL") ;
        int savedOtp = (int) mp.get("OTP");
        User user = userRepo.findByEmail(email);
        if(user == null) {
            mp.clear();
            return "User Not Found With This Email";
        }
        if(savedOtp == Integer.parseInt(OTP)) { // if both password matched then
            mp.remove("OTP") ;
            return ("OTP MATCHED");
        }else {
            mp.clear();
            return "OTP NOT MATCHED TRY AGAIN";
        }


    }


}
