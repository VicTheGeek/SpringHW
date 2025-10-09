package com.vicsergeev.SpringHW.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
