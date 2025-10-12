package com.vicsergeev.SpringHW.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    private String name;

    private int age;

    private String email;

    @Column(name = "created_at", nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    @CreationTimestamp
    private LocalDateTime createdAt;

}
