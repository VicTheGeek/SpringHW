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
}
