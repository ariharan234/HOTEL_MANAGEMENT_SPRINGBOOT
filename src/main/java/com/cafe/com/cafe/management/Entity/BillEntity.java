package com.cafe.com.cafe.management.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicUpdate
@DynamicInsert
@Entity(name="bill")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private String name;
    private String email;
    private String contactnumber;
    private String paymentmehtod;
    private Integer total;
    @Column(name="productdetails",columnDefinition = "json")
    private String productdetails;
    private String createdby;

}
