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
public class UserRoleDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean active;
}