package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);

    Optional<User> findByAccountStatusPasswordResetToken(String resetToken);
}
