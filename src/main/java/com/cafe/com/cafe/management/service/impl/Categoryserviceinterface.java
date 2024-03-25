package com.cafe.com.cafe.management.service.impl;

import com.cafe.com.cafe.management.dto.Categorydto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Categoryserviceinterface {
    public ResponseEntity<String> postcategory(Categorydto categorydto);

    ResponseEntity<List<Categorydto>> getallcategories(String filtervalue);

    ResponseEntity<String> updatecategory(Categorydto categorydto);
}
