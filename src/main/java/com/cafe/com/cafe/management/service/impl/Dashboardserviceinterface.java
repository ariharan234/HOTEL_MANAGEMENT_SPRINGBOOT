package com.cafe.com.cafe.management.service.impl;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface Dashboardserviceinterface {

    ResponseEntity<Map<String,Object>> getallcount();
}
