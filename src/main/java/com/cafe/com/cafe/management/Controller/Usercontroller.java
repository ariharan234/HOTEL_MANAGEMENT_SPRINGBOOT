package com.cafe.com.cafe.management.Controller;

import com.cafe.com.cafe.management.dto.LoginDetail;
import com.cafe.com.cafe.management.dto.Userdto;
import com.cafe.com.cafe.management.dto.Useremail;
import com.cafe.com.cafe.management.service.Serviceimpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private Serviceimpl service;


    @PostMapping("/signup")
    public void postuser(@RequestBody Userdto user){
        service.postuser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody(required = true) LoginDetail logindetails){
        return service.loginservice(logindetails);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Userdto>> getallusers(){

        List<Userdto> userdtos=service.getalluser();
        return new ResponseEntity<>(userdtos, HttpStatus.OK);
        
    }
    @PostMapping("/updatestatus")
    public ResponseEntity<String> updateuserstatus(@RequestBody  Map<String,String> map) throws Exception {

        return ResponseEntity.ok(service.updateuserstatusbyadmin(map));
    }
    @GetMapping("/Checktoken")
    public ResponseEntity<String> checktoken(){
        return service.Checktoken();
    }

    @PostMapping("/ChangePassword")
    public ResponseEntity<String> changepassword(@RequestBody Map<String,String> map){
        return service.changepassword(map);
    }
    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotpassword(@RequestBody Useremail useremail) throws MessagingException {
        return service.forgotPassword(useremail);
    }



}
