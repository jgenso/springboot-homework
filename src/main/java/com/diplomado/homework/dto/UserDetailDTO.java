package com.diplomado.homework.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private Long id;

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
    @Min(18)
    private Integer age;

    @NotNull
    private LocalDate birthDay;
}