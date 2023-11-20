package com.diplomado.homework.repositories.spring.data;

import com.diplomado.homework.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}