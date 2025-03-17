package com.nl2sql.authserver.repository;

import com.nl2sql.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
