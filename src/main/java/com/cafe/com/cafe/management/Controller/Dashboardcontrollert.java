package com.cafe.com.cafe.management.Controller;

import com.cafe.com.cafe.management.service.Dashboardserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class Dashboardcontrollert {
    @Autowired
    private Dashboardserviceimpl dashboardservice;

    @GetMapping("/gatallcount")
    public ResponseEntity<Map<String,Object>> getallcount(){
       return dashboardservice.getallcount();
    }
}
