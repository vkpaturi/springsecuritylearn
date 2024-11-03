package com.project.spring.springsecuritylearn.repositories;

import com.project.spring.springsecuritylearn.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users save(Users user);
}
