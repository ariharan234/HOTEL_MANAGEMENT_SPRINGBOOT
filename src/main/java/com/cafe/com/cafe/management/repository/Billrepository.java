package com.cafe.com.cafe.management.repository;

import com.cafe.com.cafe.management.Entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Billrepository extends JpaRepository<BillEntity,Integer> {
    List<BillEntity> findAllByOrderByIdDesc();
    List<BillEntity> findByCreatedbyOrderByIdDesc(String useremail);

    BillEntity findByUuid(String uuid);
}
