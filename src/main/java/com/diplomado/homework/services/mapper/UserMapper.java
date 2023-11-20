package com.diplomado.homework.services.mapper;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.domain.entities.User;
import com.diplomado.homework.domain.entities.UserRole;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;
import com.diplomado.homework.repositories.spring.data.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements CustomMapper<UserDTO, User> {

    @Autowired
    UserDetailMapper userDetailMapper;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public UserDTO toDtoDetailed(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());

        if (user.getUserDetail() != null) {
            dto.setUserDetail(userDetailMapper.toDto(user.getUserDetail()));
        }

        if (user.getUserRoles() != null) {
            dto.setRoles(user.getUserRoles().stream().map(ur -> ur.getRole().getId()).collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        if (dto.getUserDetail() != null) {
            user.setUserDetail(userDetailMapper.toEntity(dto.getUserDetail()));
            user.getUserDetail().setUser(user);
        }
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<UserRole> userRoles = dto.getRoles().stream().map(roleId -> {
                Role role = roleRepository.getReferenceById(roleId);
                return new UserRole(true, user, role);
            }).collect(Collectors.toSet());
            user.setUserRoles(userRoles);
        }

        return user;
    }
}