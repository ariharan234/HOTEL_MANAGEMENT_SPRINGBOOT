package com.cafe.com.cafe.management.repository;

import com.cafe.com.cafe.management.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Productrepository extends JpaRepository<ProductEntity,Integer> {

}
