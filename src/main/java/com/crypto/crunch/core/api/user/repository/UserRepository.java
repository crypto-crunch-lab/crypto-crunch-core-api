package com.crypto.crunch.core.api.user.repository;

import com.crypto.crunch.core.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer integer);
    Optional<User> findByEmail(String email);
}
