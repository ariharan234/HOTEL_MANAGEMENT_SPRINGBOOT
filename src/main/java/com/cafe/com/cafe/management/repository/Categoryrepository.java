package com.cafe.com.cafe.management.repository;

import com.cafe.com.cafe.management.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Categoryrepository extends JpaRepository<CategoryEntity,Integer> {
       Optional<CategoryEntity> findByCategoryName(String name);
       List<CategoryEntity> getallcategory();
}
