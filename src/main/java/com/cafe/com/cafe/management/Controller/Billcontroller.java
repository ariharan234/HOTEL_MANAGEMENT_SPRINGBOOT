package com.cafe.com.cafe.management.Controller;

import com.cafe.com.cafe.management.dto.billdto;
import com.cafe.com.cafe.management.service.billserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class Billcontroller {

    @Autowired
    private billserviceimpl billserviceimpl;

    @PostMapping("/generatereport")
    public ResponseEntity<String> generatereport(@RequestBody Map<String,Object> map) throws Exception {
        return billserviceimpl.generatereport(map);
    }

    @GetMapping("/getallbill")
    public ResponseEntity<List<billdto>> getallbill(){
        return billserviceimpl.getallbill();
    }

    @PostMapping("/getpdf")
    public ResponseEntity<byte[]> getpdf(@RequestBody Map<String,Object> map) throws Exception {
        return billserviceimpl.getpdf(map);
    }

    @PostMapping("/Deletebyid/{id}")
    public ResponseEntity<String> deletebillbyid(@PathVariable Integer id){
        return billserviceimpl.deletebyid(id);
    }
}
