package com.cafe.com.cafe.management.service.impl;

import com.cafe.com.cafe.management.dto.billdto;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Billserviceinterface {
    ResponseEntity<String> generatereport(Map<String, Object> map) throws Exception;

    ResponseEntity<List<billdto>> getallbill();

    ResponseEntity<byte[]> getpdf(Map<String, Object> map) throws Exception;

    ResponseEntity<String> deletebyid(Integer id);
}
