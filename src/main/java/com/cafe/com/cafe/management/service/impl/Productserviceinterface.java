package com.cafe.com.cafe.management.service.impl;

import com.cafe.com.cafe.management.dto.Productdto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface Productserviceinterface {
    ResponseEntity<String> addproduct(Productdto productdto);

    ResponseEntity<List<Productdto>> getallproducts() throws Exception;

    ResponseEntity<String> updateproduct(Productdto productdto);

    ResponseEntity<String> deleteproductbyid(Integer id);

    ResponseEntity<String> updatestatus(Map<String, String> map);

    ResponseEntity<Productdto> getproductbyid(Integer id);

    ResponseEntity<List<Productdto>> getproductbycategoryid(Integer id);
}
