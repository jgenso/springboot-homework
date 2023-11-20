package com.diplomado.homework.services.mapper;

import com.diplomado.homework.domain.entities.Role;
import com.diplomado.homework.domain.entities.UserDetail;
import com.diplomado.homework.dto.RoleDTO;
import com.diplomado.homework.dto.UserDetailDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDetailMapper implements CustomMapper<UserDetailDTO, UserDetail> {
    @Override
    public UserDetailDTO toDto(UserDetail userDetail) {
        UserDetailDTO dto = new UserDetailDTO();
        dto.setId(userDetail.getId());
        dto.setFirstName(userDetail.getFirstName());
        dto.setLastName(userDetail.getLastName());
        dto.setAge(userDetail.getAge());
        dto.setBirthDay(userDetail.getBirthDay());

        return dto;
    }

    @Override
    public UserDetail toEntity(UserDetailDTO dto) {
        UserDetail userDetail = new UserDetail();
        userDetail.setId(dto.getId());
        userDetail.setFirstName(dto.getFirstName());
        userDetail.setLastName(dto.getLastName());
        userDetail.setAge(dto.getAge());
        userDetail.setBirthDay(dto.getBirthDay());

        return userDetail;
    }
}