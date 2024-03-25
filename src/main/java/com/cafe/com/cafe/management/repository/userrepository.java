package com.cafe.com.cafe.management.repository;

import com.cafe.com.cafe.management.Entity.Role;
import com.cafe.com.cafe.management.Entity.UserEntity;
import com.cafe.com.cafe.management.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface userrepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(Role role);
}
