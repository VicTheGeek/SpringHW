package com.vicsergeev.SpringHW.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

/**
 * Created by Victor 09.10.2025
 */

@Setter
@Getter
@Entity
@Table(name = "users")
@Schema(description = "User model")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User ID, will be given automatically", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "username", example = "jack sparrow")
    private String name;

    @Schema(description = "user age", example = "35")
    private Integer age;

    @Column(nullable = false)
    @Schema(description = "user email that can be used for sign up", example = "aa@aa.aa")
    private String email;

    @Column(name = "created_at", nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    @CreationTimestamp
    @Schema(description = "date and time when user was created")
    private LocalDateTime createdAt;

    public User() {}
}
