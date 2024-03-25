package com.cafe.com.cafe.management.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class billdto {
    private Integer id;
    private String uuid;
    private String name;
    private String email;
    private String contactnumber;
    private String paymentmehtod;
    private Integer total;
    private String productdetails;
    private String createdby;
}
