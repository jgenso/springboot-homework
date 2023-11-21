package com.diplomado.homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 3, message = "Role name should have at least 3 characters")
    @Size(max = 32, message = "Role name should have less than 33 characters")
    private String name;
}