package com.cafe.com.cafe.management.dto;

import com.cafe.com.cafe.management.Entity.CategoryEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class Productdto {
    private Integer id;
    private String name;
    private String description;
    private CategoryEntity categoryEntity;
    private String price;
    private String status;
}
