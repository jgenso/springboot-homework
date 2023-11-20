package com.diplomado.homework.services.mapper;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.domain.entities.User;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper implements CustomMapper<UserDTO, User> {

    @Autowired
    UserDetailMapper userDetailMapper;

    @Override
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        if (user.getUserDetail() != null) {
            dto.setUserDetail(userDetailMapper.toDto(user.getUserDetail()));
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
        }

        if (dto.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        return user;
    }
}