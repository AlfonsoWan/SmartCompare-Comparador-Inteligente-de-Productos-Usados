package com.smartcompare.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String authType; // GOOGLE, LOCAL, etc.

    @Column(nullable = false)
    private String role; // USER, ADMIN

    @Column(nullable = false)
    private String password;
}
