package com.evoting.evote_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VoterToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID token;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option selectedOption; // Null tant que le vote n'est pas fait
}
