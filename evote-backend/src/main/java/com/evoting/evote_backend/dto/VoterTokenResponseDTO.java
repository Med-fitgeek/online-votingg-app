package com.evoting.evote_backend.dto;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class VoterTokenResponseDTO {
    private String voter;                 // Nom ou email du votant
    private UUID token;
}
