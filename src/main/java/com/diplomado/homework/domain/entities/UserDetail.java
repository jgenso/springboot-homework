package com.diplomado.homework.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "First name should have at least 3 characters")
    @Size(max = 32, message = "First name should have less than 33 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Last name should have at least 3 characters")
    @Size(max = 32, message = "Last name should have less than 33 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Min(18)
    private Integer age;

    @NotNull
    @Column(name = "birth_day", columnDefinition = "DATE")
    private LocalDate birthDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserDetail(String firstName, String lastName, Integer age, LocalDate birthDay, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.birthDay = birthDay;
        this.user = user;
    }

    public UserDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", birthDate='" + birthDay + '\'' +
                '}';
    }
}