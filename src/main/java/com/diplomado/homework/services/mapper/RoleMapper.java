package com.diplomado.homework.services.mapper;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.dto.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements CustomMapper<RoleDTO, Role> {
    @Override
    public RoleDTO toDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());

        return dto;
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());

        return role;
    }
}