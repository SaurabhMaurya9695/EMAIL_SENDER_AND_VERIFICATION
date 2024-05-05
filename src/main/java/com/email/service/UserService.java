package com.email.service;


import com.email.model.User;
import jakarta.mail.MessagingException;

public interface UserService {
    String addUser(User user);

    String mailSend(String email , String from) throws MessagingException;

    String verifyOTP(String otp);
}
