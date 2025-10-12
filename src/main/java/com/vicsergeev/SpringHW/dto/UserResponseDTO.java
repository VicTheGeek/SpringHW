package com.vicsergeev.SpringHW.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Victor 09.10.2025
 */

// DTO for response to GET request
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private LocalDateTime createdAt;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String name, Integer age, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.createdAt = createdAt;
    }

}
