package com.diplomado.homework.services.mapper;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.domain.entities.UserRole;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.RoleWithDetailDTO;
import com.diplomado.homework.dto.UserRoleDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper implements CustomMapper<RoleDTO, Role> {
    @Override
    public RoleDTO toDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());

        return dto;
    }

    public UserRoleDTO toUserRoleDTO(UserRole userRole) {
        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(userRole.getUser().getId());
        dto.setUsername(userRole.getUser().getUsername());
        if (userRole.getUser().getUserDetail() != null) {
            dto.setFirstName(userRole.getUser().getUserDetail().getFirstName());
            dto.setLastName(userRole.getUser().getUserDetail().getLastName());
        }
        dto.setActive(userRole.getActive());

        return dto;
    }

    public RoleWithDetailDTO toRoleWithDetailDTO(Role role) {
        RoleWithDetailDTO dto = new RoleWithDetailDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setUsers(role.getUserRoles().stream().map(ur -> toUserRoleDTO(ur)).collect(Collectors.toList()));

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