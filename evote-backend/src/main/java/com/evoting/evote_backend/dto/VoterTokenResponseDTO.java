package com.evoting.evote_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoterTokenResponseDTO {
    private String voter;                 // Nom ou email du votant
    private UUID token;
}
