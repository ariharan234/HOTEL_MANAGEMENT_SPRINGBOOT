package com.cafe.com.cafe.management.service.impl;

import com.cafe.com.cafe.management.dto.LoginDetail;
import com.cafe.com.cafe.management.dto.Userdto;
import com.cafe.com.cafe.management.dto.Useremail;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

public interface serviceinterface {
    public void postuser(Userdto user);
    public String loginservice(LoginDetail logindetails);

    Userdto getuserbyemail(String email);



    List<Userdto> getalluser() throws AccessDeniedException;

    String updateuserstatusbyadmin(Map<String, String> map) throws Exception;

    ResponseEntity<String> Checktoken();

    ResponseEntity<String> changepassword(Map<String, String> map);

    ResponseEntity<String> forgotPassword(Useremail email) throws MessagingException;
}
