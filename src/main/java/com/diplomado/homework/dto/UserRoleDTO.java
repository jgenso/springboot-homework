package com.diplomado.homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Firstname should have at least 3 characters")
    @Size(max = 32, message = "Firstname should have less than 33 characters")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Lastname should have at least 3 characters")
    @Size(max = 32, message = "Lastname should have less than 33 characters")
    private String lastName;

    @NotNull
    private Boolean active;
}