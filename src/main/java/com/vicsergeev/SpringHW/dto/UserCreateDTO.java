package com.vicsergeev.SpringHW.dto;

import java.time.LocalDateTime;

/**
* Created by Victor 09.10.2025
*/

public record UserCreateDTO(String name, int age, String email, LocalDateTime createdAt) {
}
