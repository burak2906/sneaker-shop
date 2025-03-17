package com.nl2sql.authserver.repository;

import com.nl2sql.authserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByName(String name);
}
