package com.evoting.evote_backend.dto;

import java.util.UUID;

public class VoterTokenResponseDTO {
    private String voter;                 // Nom ou email du votant
    private UUID token;
}
