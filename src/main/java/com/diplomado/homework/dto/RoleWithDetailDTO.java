package com.diplomado.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleWithDetailDTO {
    private Long id;
    private String name;
    private List<UserRoleDTO> users;
}