package com.diplomado.homework.repositories.spring.data;

import com.diplomado.homework.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}