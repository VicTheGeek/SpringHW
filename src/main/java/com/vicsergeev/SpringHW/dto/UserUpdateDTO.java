package com.vicsergeev.SpringHW.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
* Created by Victor 09.10.2025
*/

@Getter
@Setter
public class UserUpdateDTO {
    private String name;
    private Integer age;
    private String email;
    private LocalDateTime createdAt;
}
