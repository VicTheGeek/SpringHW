package com.vicsergeev.SpringHW.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Created by Victor 16.10.2025
 */

public class UserDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotNull
    private Integer age;

    public UserDTO() {}

    public UserDTO(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}