package com.cafe.com.cafe.management.Entity;

import jakarta.persistence.*;
import lombok.Data;
@NamedQuery(
        name = "CategoryEntity.getallcategory",
        query = "SELECT c FROM CategoryEntity c WHERE c.id IN (SELECT p.categoryEntity.id FROM ProductEntity p WHERE p.status = 'true')"
)

@Data
@Entity
@Table(name="category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoryName;
}
