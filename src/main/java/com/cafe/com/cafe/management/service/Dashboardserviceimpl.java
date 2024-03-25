package com.cafe.com.cafe.management.service;

import com.cafe.com.cafe.management.repository.Billrepository;
import com.cafe.com.cafe.management.repository.Categoryrepository;
import com.cafe.com.cafe.management.repository.Productrepository;
import com.cafe.com.cafe.management.service.impl.Dashboardserviceinterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class Dashboardserviceimpl implements Dashboardserviceinterface {

    private Categoryrepository categoryrepository;
    private Productrepository productrepository;
    private Billrepository billrepository;


    public Dashboardserviceimpl(Categoryrepository categoryrepository, Productrepository productrepository, Billrepository billrepository) {
        this.categoryrepository = categoryrepository;
        this.productrepository = productrepository;
        this.billrepository = billrepository;
    }

    @Override
    public ResponseEntity<Map<String,Object>> getallcount() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("Category",categoryrepository.count());
        map.put("Products",productrepository.count());
        map.put("Bills",billrepository.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
