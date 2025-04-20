package com.masteryhub.e_commerce.repository;

import com.masteryhub.e_commerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
