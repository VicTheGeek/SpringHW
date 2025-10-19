package com.vicsergeev.SpringHW.model;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    @Column(nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    @CreationTimestamp
    private LocalDateTime createdAt;

    public User() {}
}
