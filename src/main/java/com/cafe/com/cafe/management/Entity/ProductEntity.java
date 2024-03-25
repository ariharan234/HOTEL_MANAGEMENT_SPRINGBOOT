package com.cafe.com.cafe.management.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")
public class ProductEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @ManyToOne(targetEntity = CategoryEntity.class)
    @JoinColumn(name = "category_fk")
    private CategoryEntity categoryEntity;
    private String price;
    private String status;
}
