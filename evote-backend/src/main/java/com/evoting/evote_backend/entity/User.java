package com.evoting.evote_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // hashed

    private String role; // e.g., "ADMIN"

    @OneToMany(mappedBy = "createdBy")
    private List<Election> elections;
}
