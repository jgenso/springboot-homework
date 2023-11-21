package com.diplomado.homework.dto;

import jakarta.validation.constraints.Email;
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
public class UserDTO {
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Username should have at least 3 characters")
    @Size(max = 32, message = "Username should have less than 33 characters")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8, message = "User password should have at least 8 characters")
    @Size(max = 32, message = "User password should have less than 33 characters")
    private String password;

    @NotNull
    @NotBlank
    @Email(message = "User email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    private UserDetailDTO userDetail;

    private List<Long> roles;
}